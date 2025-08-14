# Match Service - Swagger API Documentation

## Overview

This project now includes comprehensive Swagger/OpenAPI documentation for all REST endpoints.

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

### 4. API Endpoints Documented

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
