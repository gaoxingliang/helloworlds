# User Service - Helidon

A simple user information service built with Helidon and GraalVM native image.

## Features

- RESTful API for user management
- MySQL database integration
- Helidon DB Client for data access
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

2. Create the users table:
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(50)
);
```

3. Update `application.yaml` with your MySQL credentials.

## Running the Application

### JVM Mode
```bash
mvn clean compile exec:java -Dexec.mainClass="com.example.UserServiceApplication"
```

### Native Image Mode
```bash
mvn clean package -Pnative
./target/user-service-helidon
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
