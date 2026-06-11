# Reference Data Aggregation Service

Spring Boot 3 and Java 17 service that exposes country reference data through one routed endpoint.

## Run locally

The default profile requires a signed JWT. Use the `dev` profile for local development:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Run verification:

```bash
./mvnw test
./mvnw verify
```

Swagger UI is available at `http://localhost:8080/swagger-ui.html`.

## Request examples

Search countries:

```bash
curl -X POST http://localhost:8080/api/v1/reference-data \
  -H 'Content-Type: application/json' \
  -d '{
    "service": "reference.country.search",
    "data": {
      "continentCode": "AF",
      "page": 0,
      "size": 20,
      "sortBy": "countryName",
      "sortDirection": "ASC"
    }
  }'
```

Get country details:

```bash
curl -X POST http://localhost:8080/api/v1/reference-data \
  -H 'Content-Type: application/json' \
  -d '{"service":"reference.country.details","data":{"countryCode":"KE"}}'
```

Refresh all reference data:

```bash
curl -X POST http://localhost:8080/api/v1/reference-data \
  -H 'Content-Type: application/json' \
  -d '{"service":"reference.data.refresh","data":{"refreshType":"FULL"}}'
```

## Security

The default profile validates HMAC SHA-256 JWTs using `JWT_SECRET`. Read operations require the
`reference-data:read` authority. Refresh requires `reference-data:refresh`.

## Redis

The default profile uses an in-memory cache. Start Redis and enable the `redis` profile to use Redis:

```bash
docker run --name rdas-redis -p 6379:6379 -d redis:7
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev,redis
```

Configure Redis with `REDIS_HOST`, `REDIS_PORT`, and `RDAS_CACHE_TTL`.

## SOAP integration

The adapter follows the CountryInfoService WSDL at:

`http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso`

It currently returns static data while preserving operation-aligned methods for
`FullCountryInfoAllCountries`, `FullCountryInfo`, `CountriesUsingCurrency`,
`ListOfContinentsByName`, `ListOfCurrenciesByName`, and `ListOfLanguagesByName`.
