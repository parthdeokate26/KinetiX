# Inventory Service

A minimal Spring Boot microservice that acts as the destination service for the
KinetiX Worker Service. This service exists only for testing purposes — it
simply receives a POST request, logs the payload, and returns a success
response.

## Technology

- Java 21
- Spring Boot 3.5.x
- Maven
- Spring Web
- Lombok

## Run

```
mvn spring-boot:run
```

The service starts on port `9090`.

## Test

Send a POST request to:

```
http://localhost:9090/api/inventory/reserve
```

Sample JSON body:

```json
{
    "eventId": "evt-100",
    "customer": "Parth",
    "amount": 500
}
```

Expected response:

```
Inventory Reserved Successfully
```

Example with curl:

```
curl -X POST http://localhost:9090/api/inventory/reserve \
  -H "Content-Type: application/json" \
  -d '{"eventId":"evt-100","customer":"Parth","amount":500}'
```
