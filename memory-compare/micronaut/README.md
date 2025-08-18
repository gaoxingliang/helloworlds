# User Service - Micronaut

A simple user information service built with Micronaut and GraalVM native image.

## Features

- RESTful API for user management
- MySQL database integration
- JPA/Hibernate for data persistence
- GraalVM native image support

## Prerequisites

- Java 21
- Gradle 8.0+
- MySQL 8.0+
- GraalVM 21+

## Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE userdb;
```

2. Update `application.yml` with your MySQL credentials.

## Running the Application

### JVM Mode
```bash
./gradlew run
```

### Native Image Mode
```bash
./gradlew nativeCompile
./build/native/nativeCompile/micronaut
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
