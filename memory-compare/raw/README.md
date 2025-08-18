# User Service - Raw Spring Boot

A simple user information service built with Spring Boot and GraalVM native image.

## Features

- RESTful API for user management
- MySQL database integration
- JPA/Hibernate for data persistence
- GraalVM native image support

## Prerequisites

- Java 21
- Maven 3.8+
- MySQL 8.0+
- GraalVM 21+

## Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE userdb;
```

2. Update `application.properties` with your MySQL credentials.

## Running the Application

### JVM Mode
```bash
mvn spring-boot:run
```

### Native Image Mode
```bash
mvn spring-boot:build-image
docker run -p 8080:8080 user-service-raw:1.0.0
```

## API Endpoints

- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email
- `POST /api/users` - Create a new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

## Example Usage

```bash
# Create a user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","phone":"123-456-7890"}'

# Get user by ID
curl http://localhost:8080/api/users/1
```
