# Match Service - Swagger API Documentation

## Overview

This project now includes comprehensive Swagger/OpenAPI documentation for all REST endpoints.

## Accessing the API Documentation

### Swagger UI
Once the application is running, you can access the interactive Swagger UI at:
- **URL**: http://localhost:8080/swagger-ui.html
- **Description**: Interactive API documentation where you can test endpoints directly

### OpenAPI Specification
You can also access the raw OpenAPI specification at:
- **URL**: http://localhost:8080/api-docs
- **Format**: JSON format OpenAPI 3.0 specification

## Features Added

### 1. OpenAPI Configuration
- Custom API information with title, version, and description
- Contact information and license details
- Multiple server configurations (dev and production)

### 2. Controller Documentation
- **Match Management**: CRUD operations for sports matches
- **Match Odds Management**: CRUD operations for match odds
- Comprehensive operation descriptions and examples
- Detailed response codes and error descriptions
- **RFC 9457 ProblemDetail**: Standardized error responses with examples

### 3. DTO Schema Documentation
- Detailed field descriptions with examples
- Validation constraints documentation
- Proper data type specifications
- Format patterns for dates and times

### 4. Error Response Documentation
- **ProblemDetail Integration**: Uses Spring's `org.springframework.http.ProblemDetail`
- **Standardized Error Format**: RFC 9457 compliant error responses
- **Detailed Examples**: Real-world error response examples in Swagger UI
- **Validation Errors**: Comprehensive constraint violation details
- **Global Exception Handling**: Centralized error handling with proper documentation

### 4. API Endpoints Documented

#### Match Endpoints
- `GET /api/v1/matches/{matchId}` - Get match by ID
- `POST /api/v1/matches` - Create new match
- `PUT /api/v1/matches/{matchId}` - Update existing match
- `DELETE /api/v1/matches/{matchId}` - Delete match

#### Match Odds Endpoints
- `GET /api/v1/matches/{matchId}/odds` - Get paginated odds for a match
- `GET /api/v1/matches/{matchId}/odds/{matchOddId}` - Get specific match odd
- `POST /api/v1/matches/{matchId}/odds` - Create new match odd
- `PUT /api/v1/matches/{matchId}/odds/{matchOddId}` - Update existing match odd
- `DELETE /api/v1/matches/{matchId}/odds/{matchOddId}` - Delete match odd

## Configuration

The Swagger configuration is customizable through `application.properties`:

```properties
# OpenAPI/Swagger Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
springdoc.show-actuator=false
```

## Testing the API

1. Start the application: `./mvnw spring-boot:run`
2. Open browser to: http://localhost:8080/swagger-ui.html
3. Use the "Try it out" buttons to test endpoints interactively
4. Review the schema definitions for request/response formats

## Dependencies

The project uses SpringDoc OpenAPI 3 with UI support:
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.8.9</version>
</dependency>
```

This provides modern OpenAPI 3.0 support and includes the Swagger UI. The error responses use Spring Framework's built-in `ProblemDetail` class which implements RFC 9457 standard for HTTP problem details.

## Error Response Format

All error responses follow the RFC 9457 standard using Spring's `ProblemDetail`:

```json
{
    "type": "about:blank",
    "title": "Bad Request",
    "status": 400,
    "detail": "Validation failed for one or more arguments",
    "instance": "/api/v1/matchDtos",
    "constrainViolations": {
        "teamA": "must not be blank",
        "teamB": "must not be blank"
    }
}
```

### Error Types:
- **400 Bad Request**: Validation errors, invalid input
- **404 Not Found**: Resource not found
- **409 Conflict**: Data integrity violations (e.g., duplicate matches)
- **500 Internal Server Error**: Unexpected server errors
