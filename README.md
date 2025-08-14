# Match Service

## Overview

A Spring Boot REST API service for managing sports matches and betting odds with comprehensive Swagger/OpenAPI documentation.

## Table of Contents

- [Running the Project](#running-the-project)
- [Accessing the API Documentation](#accessing-the-api-documentation)
- [API Documentation Features](#api-documentation-features)
- [API Endpoints Documented](#api-endpoints-documented)
- [Project Structure](#project-structure)
- [Development](#development)
- [Error Response Format](#error-response-format)

## Running the Project

### Prerequisites
- Docker and Docker Compose installed on your system
- No Java installation required on your local machine

### Quick Start with Docker
The easiest way to run the project is using Docker Compose, which will handle all dependencies and setup automatically:

```bash
# Clone the repository (if not already done)
git clone https://github.com/geo-gkez/match-service.git
cd match-service

# Start the application using Docker Compose
docker-compose up

# To run in detached mode (background)
docker-compose up -d

# To stop the application
docker-compose down
```

### What Docker Compose Includes
- **Application Container**: Builds and runs the Match Service application
- **Database**: Sets up the required database with proper configuration
- **Network**: Creates isolated network for service communication
- **Port Mapping**: Exposes the application on port 8080

The application will be available at `http://localhost:8080` once all containers are up and running.

### Alternative: Docker Build Only
If you prefer to use Docker without Compose:

```bash
# Build the Docker image
docker build -t match-service .

# Run the container
docker run -p 8080:8080 match-service
```

**Note**: When using Docker build only, you'll need to configure the database connection manually.

## Accessing the API Documentation

### Swagger UI
Once the application is running, you can access the interactive Swagger UI at:
- **URL**: http://localhost:8080/swagger-ui.html
- **Description**: Interactive API documentation where you can test endpoints directly

### OpenAPI Specification
You can also access the raw OpenAPI specification at:
- **URL**: http://localhost:8080/api-docs
- **Format**: JSON format OpenAPI 3.0 specification

## API Documentation Features

### 1. OpenAPI Configuration
- Custom API information with title, version, and description
- Contact information
- Multiple server configurations 

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

## API Endpoints Documented

#### Match Endpoints
- `GET /api/v1/matches` - Get paginated matches
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

## Project Structure

This is a Spring Boot application following standard Maven project structure with clean architecture principles:

```
match-service/
├── docker-compose.yaml          # Docker Compose configuration for easy deployment
├── Dockerfile                   # Docker image build configuration
├── pom.xml                     # Maven project configuration and dependencies
├── README.md                   # Project documentation
├── HELP.md                     # Additional help documentation
├── mvnw                        # Maven wrapper script (Unix)
├── mvnw.cmd                    # Maven wrapper script (Windows)
├── http-examples/              # HTTP request examples for testing
│   ├── matchExamples.http      # Match API examples
│   └── matchOddExample.http    # Match Odds API examples
└── src/
    ├── main/
    │   ├── java/com/github/geo_gkez/match_service/
    │   │   ├── MatchServiceApplication.java    # Spring Boot main application class
    │   │   ├── config/                         # Configuration classes
    │   │   │   └── OpenApiConfig.java          # Swagger/OpenAPI configuration
    │   │   ├── constant/                       # Application constants
    │   │   │   ├── DateFormaterConstants.java
    │   │   │   ├── MatchServiceConstants.java
    │   │   │   └── UrlPathConstants.java
    │   │   ├── controller/                     # REST API controllers
    │   │   │   ├── MatchController.java        # Match management endpoints
    │   │   │   └── MatchOddsController.java    # Match odds management endpoints
    │   │   ├── converter/                      # Custom converters
    │   │   │   └── SportEnumConverter.java     # Enum conversion utilities
    │   │   ├── dto/                           # Data Transfer Objects
    │   │   │   ├── MatchCreateOrUpdateRequest.java
    │   │   │   ├── MatchDto.java
    │   │   │   ├── MatchOddCreateOrUpdateRequest.java
    │   │   │   ├── MatchOddDto.java
    │   │   │   ├── PageMatchOddResponse.java
    │   │   │   └── PageMatchResponse.java
    │   │   ├── entity/                        # JPA entities
    │   │   │   ├── Match.java                 # Match entity
    │   │   │   ├── MatchOdd.java              # Match odds entity
    │   │   │   └── enums/                     # Entity-related enums
    │   │   ├── exception/                     # Custom exceptions and handlers
    │   │   ├── mapper/                        # MapStruct mappers for entity-DTO conversion
    │   │   ├── repository/                    # JPA repositories for data access
    │   │   ├── service/                       # Business logic services
    │   │   └── validation/                    # Custom validation logic
    │   └── resources/
    │       ├── application.properties         # Default application configuration
    │       ├── application-dev.properties     # Development environment config
    │       └── application-preprod.properties # Pre-production environment config
    └── test/
        └── java/com/github/geo_gkez/match_service/
            ├── controller/                    # Controller unit tests
            ├── integration_test/              # Integration tests
            ├── mapper/                        # Mapper unit tests
            └── service/                       # Service unit tests
```

### Key Technologies Used

#### Core Framework
- **Java 21**: Latest LTS version of Java
- **Spring Boot 3.5.4**: Application framework
- **Spring Data JPA**: Data persistence and repository layer
- **Spring Web**: RESTful web services
- **Spring Validation**: Input validation

#### Database
- **PostgreSQL**: Production database
- **Hibernate**: ORM framework (via Spring Data JPA)

#### Documentation & Mapping
- **MapStruct 1.6.3**: Entity-DTO mapping
- **SpringDoc OpenAPI 2.8.9**: API documentation (Swagger)

#### Testing
- **Spring Boot Test**: Testing framework with JUnit 5
- **Testcontainers**: Integration testing with real database
- **Mockito**: Mocking framework (included with Spring Boot Test)

#### Build & Deployment
- **Maven**: Build tool and dependency management
- **Docker**: Containerization
- **Docker Compose**: Multi-container orchestration

### Architecture Highlights

- **Layered Architecture**: Clear separation of concerns across controller, service, repository layers
- **DTO Pattern**: Separate data transfer objects for clean API contracts
- **Entity-DTO Mapping**: Automated mapping using MapStruct
- **Validation**: Comprehensive input validation at controller level
- **Exception Handling**: Centralized error handling with RFC 9457 compliance
- **Documentation**: Auto-generated API documentation with Swagger
- **Testing**: Comprehensive test coverage across all layers

## Development

### Running Locally with Maven
If you have Java 21:

```bash
# Clone the repository
git clone https://github.com/geo-gkez/match-service.git
cd match-service

# Run with Maven (requires PostgreSQL running)
./mvnw spring-boot:run

# Or build and run JAR
./mvnw clean package
java -jar target/match-service-0.0.1-SNAPSHOT.jar
```

### Testing
```bash
# Run all tests
./mvnw test
```

### API Testing
The project includes HTTP request examples in the `http-examples/` directory:
- `matchExamples.http` - Match management API examples
- `matchOddExample.http` - Match odds API examples

These can be executed directly in IntelliJ IDEA or VS Code with the REST Client extension.

## Error Response Format

All error responses follow the RFC 9457 standard using Spring's `ProblemDetail`:

```json
{
    "type": "about:blank",
    "title": "Bad Request",
    "status": 400,
    "detail": "Validation failed for one or more arguments",
    "instance": "/api/v1/matches",
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
