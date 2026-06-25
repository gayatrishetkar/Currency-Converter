# 💱 Currency Converter API

<p align="center">
  <img src="https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.2-green?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/RestClient-New_in_3.2-blue?style=for-the-badge&logo=spring&logoColor=white" />
  <img src="https://img.shields.io/badge/FreeCurrencyAPI-Live_Rates-yellow?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Swagger-UI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" />
  <img src="https://img.shields.io/badge/Actuator-Enabled-critical?style=for-the-badge&logo=spring&logoColor=white" />
</p>

> A lightweight **Spring Boot 3.2** REST API for real-time currency conversion. Consumes live exchange rates from [FreeCurrencyAPI](https://freecurrencyapi.com) using Spring's modern **RestClient** and exposes a clean conversion endpoint with full **Swagger UI** and **Actuator** support.

---

## ✨ Features

- 🔄 **Real-time currency conversion** using live exchange rates
- 🌐 **Spring RestClient** — modern, fluent HTTP client introduced in Spring Boot 3.2
- 📊 **Spring Boot Actuator** — monitor app health and metrics
- 📝 **Swagger UI** — interactive API documentation
- ⚡ **Zero database** — stateless, lightweight, and fast
- 🔒 **Externalized config** — API key stored in `application.properties`
- 🛡️ **Global Exception Handling** — meaningful error messages for invalid currencies and units
- ✅ **Input Validation** — validates currency codes and units before calling the API
- 🔁 **Auto-deserialization** — FreeCurrencyAPI response mapped directly to POJO via Jackson
- 🧹 **Clean Architecture** — controller, service, model and exception layers separated

---

## 🏗️ Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 17+ | Programming language |
| Spring Boot | 3.2 | Application framework |
| Spring RestClient | 3.2 | HTTP client to call FreeCurrencyAPI |
| Spring Boot Actuator | 3.2 | App health & monitoring |
| SpringDoc OpenAPI | Latest | Swagger UI & API docs |
| FreeCurrencyAPI | v1 | Live exchange rate data source |

---

## 📁 Project Structure

```
src/main/java/com/example/currencyconverter/
├── CurrencyConverterApplication.java   # Main entry point
├── controller/
│   └── CurrencyController.java         # REST endpoint
├── client/
│   └── CurrencyConverter.java -> CurrencyConverterImpl           # Business logic + RestClient
└── request and response dto/
    ├── ApiResponse.java                 # Maps FreeCurrencyAPI response
    └── ConversionResponse.java          # Shapes API output to client
```

---

## 🌐 Why RestClient?

This project uses **Spring's new `RestClient`** (introduced in Spring Boot 3.2) instead of the older `RestTemplate`.

```java
// ❌ Old way — RestTemplate (verbose, non-fluent)
RestTemplate restTemplate = new RestTemplate();
Map response = restTemplate.getForObject(url, Map.class);

// ✅ New way — RestClient (fluent, modern, clean)
ApiResponse response = restClient.get()
        .uri(uriBuilder -> uriBuilder
                .queryParam("apikey", apiKey)
                .queryParam("base_currency", fromCurrency)
                .queryParam("currencies", toCurrency)
                .build())
        .retrieve()
        .body(ApiResponse.class);  // Auto-deserialized via Jackson
```

### RestClient advantages
| Feature | RestTemplate | RestClient |
|---|---|---|
| API Style | Imperative | Fluent builder chain |
| `@Bean` required | ✅ Yes | ❌ No — `RestClient.Builder` auto-injected |
| Readability | ❌ Verbose | ✅ Clean & concise |
| Response deserialization | Manual casting | Direct to POJO via `.body(Class)` |
| Spring Boot version | Any | 3.2+ |

> `RestClient.Builder` is **automatically configured** by Spring Boot 3.2 — just inject it in your constructor, no `@Bean` needed.

---

## ⚙️ Configuration

```properties
# application.properties
server.port=9090

# FreeCurrencyAPI
freecurrency.api.key=YOUR_API_KEY_HERE
freecurrency.api.url=https://api.freecurrencyapi.com/v1/latest

# Actuator
management.endpoints.web.exposure.include=*

# Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui/index.html
```

> 🔑 Get your free API key at [app.freecurrencyapi.com](https://app.freecurrencyapi.com/dashboard)

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- FreeCurrencyAPI key (free at [freecurrencyapi.com](https://freecurrencyapi.com))

### Run the app

```bash
# Clone the repo
git clone https://github.com/gayatrishetkar/Currency-Converter.git
cd Currency-Converter

# Add your API key in src/main/resources/application.properties
freecurrency.api.key=YOUR_API_KEY_HERE

# Run
mvn spring-boot:run
```

---

## 📡 API Usage

### Convert Currency

```
GET /convertCurrency?fromCurrency={from}&toCurrency={to}&units={amount}
```

| Parameter | Type | Required | Description |
|---|---|---|---|
| `fromCurrency` | String | ✅ | Source currency code (e.g. `INR`) |
| `toCurrency` | String | ✅ | Target currency code (e.g. `USD`) |
| `units` | double | ✅ | Amount to convert |

### Example Request

```bash
curl "http://localhost:9090/convertCurrency?fromCurrency=INR&toCurrency=USD&units=5000"
```

### Example Response

```json
{
    "fromCurrency": "INR",
    "toCurrency": "USD",
    "units": 5000.0,
    "exchangeRate": 0.0105886306,
    "convertedAmount": 52.9432
}
```

### More examples

```bash
# USD to EUR
curl "http://localhost:9090/convertCurrency?fromCurrency=USD&toCurrency=EUR&units=100"

# GBP to JPY
curl "http://localhost:9090/convertCurrency?fromCurrency=GBP&toCurrency=JPY&units=250"

# EUR to INR
curl "http://localhost:9090/convertCurrency?fromCurrency=EUR&toCurrency=INR&units=1000"
```

---

## 📊 How It Works

```
Client Request
      │
      ▼
CurrencyController
      │  @GetMapping("/convertCurrency")
      ▼
CurrencyConverter -> CurrencyConverterImpl
      │  RestClient calls FreeCurrencyAPI
      │  base_currency=INR&currencies=USD
      ▼
FreeCurrencyAPI Response
      │  { "data": { "USD": 0.0105886306 } }
      ▼
ConversionResponse
      │  units × exchangeRate = convertedAmount
      ▼
JSON Response to Client
```

---

## 🩺 Actuator — App Monitoring

Spring Boot Actuator is enabled and exposes health, metrics, and more.

| Endpoint | URL | Description |
|---|---|---|
| All actuator endpoints | `http://localhost:9090/actuator` | Lists all available endpoints |
| Health check | `http://localhost:9090/actuator/health` | App health status |
| App info | `http://localhost:9090/actuator/info` | Application info |
| Metrics | `http://localhost:9090/actuator/metrics` | JVM & app metrics |

```json
// GET http://localhost:9090/actuator/health
{
    "status": "UP"
}
```

---

## 📝 Swagger UI & API Docs

Interactive API documentation is available via SpringDoc OpenAPI.

| Resource | URL |
|---|---|
| 🖥️ Swagger UI | [http://localhost:9090/swagger-ui/index.html](http://localhost:9090/swagger-ui/index.html) |
| 📄 OpenAPI JSON | [http://localhost:9090/api-docs](http://localhost:9090/api-docs) |

> Use Swagger UI to test the `/convertCurrency` endpoint directly from your browser — no Postman needed!

---

## 🔗 FreeCurrencyAPI Response

The API returns a clean, flat structure:

```json
{
    "data": {
        "USD": 0.0105886306
    }
}
```

This maps directly to the `ApiResponse` POJO:

```java
public class ApiResponse {
    private Map<String, Double> data;  // "USD" -> 0.0105886306
}
```

No nested objects, no extra parsing — just a `Map<String, Double>`.

---

## 📜 License

This project is open source and available under the [MIT License](LICENSE).

---

<p align="center">Built with ☕ Java & 🍃 Spring Boot by <a href="https://github.com/gayatrishetkar">Gayatri Shetkar</a></p>
