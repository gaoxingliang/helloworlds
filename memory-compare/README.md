# Memory Compare - Java Framework Comparison

This project contains implementations of a simple user information service using 5 different Java frameworks, all configured to build native images with GraalVM. The goal is to compare memory usage, startup time, and performance across different frameworks.

## Frameworks Included

1. **Raw (Spring Boot)** - Traditional Spring Boot with native image support
2. **Helidon** - Oracle's lightweight microservices framework
3. **Micronaut** - Modern, JVM-based framework for building microservices
4. **Quarkus** - Supersonic Subatomic Java framework
5. **Spring Native** - Spring Boot with native image optimization

## Common Features

All implementations provide the same REST API:
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email
- `POST /api/users` - Create a new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

## Prerequisites

- Java 21
- Maven 3.8+ (for Spring Boot, Helidon, Quarkus, Spring Native)
- Gradle 8.0+ (for Micronaut)
- MySQL 8.0+
- GraalVM 21+
- Docker (for native image builds)

## Database Setup

1. Start MySQL server
2. Create the database:
```sql
CREATE DATABASE userdb;
```

3. Update database credentials in each framework's configuration files if needed:
   - `raw/src/main/resources/application.properties` (default: root/root, port 33307)
   - `helidon/src/main/resources/application.yaml` (default: root/root, port 33307)
   - `micronaut/src/main/resources/application.yml` (default: root/root, port 33307)
   - `quarkus/src/main/resources/application.properties` (default: root/root, port 33307)
   - `spring-native/src/main/resources/application.properties` (default: root/root, port 33307)

## Building and Running

### Raw (Spring Boot)
```bash
cd raw
mvn clean package
mvn spring-boot:run
```

### Helidon
```bash
cd helidon
mvn clean compile exec:java -Dexec.mainClass="com.example.UserServiceApplication"
```

### Micronaut
```bash
cd micronaut
./gradlew run
```

### Quarkus
```bash
cd quarkus
./mvnw quarkus:dev
```

### Spring Native
```bash
cd spring-native
mvn spring-boot:run
```

## Building Native Images

### Raw (Spring Boot)
```bash
cd raw
mvn spring-boot:build-image
docker run -p 8080:8080 user-service-raw:1.0.0
```

### Helidon
```bash
cd helidon
mvn clean package -Pnative
./target/user-service-helidon
```

### Micronaut
```bash
cd micronaut
./gradlew nativeCompile
./build/native/nativeCompile/user-service-micronaut
```

### Quarkus
```bash
cd quarkus
./mvnw clean package -Pnative
./target/user-service-quarkus-1.0.0-runner
```

### Spring Native
```bash
cd spring-native
mvn spring-boot:build-image
docker run -p 8080:8080 user-service-spring-native:1.0.0
```

## Testing the API

Once any service is running, you can test it with:

```bash
# Create a user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","phone":"123-456-7890"}'

# Get all users
curl http://localhost:8080/api/users

# Get user by ID
curl http://localhost:8080/api/users/1

# Get user by email
curl http://localhost:8080/api/users/email/john@example.com

# Update user
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"John Smith","email":"john@example.com","phone":"987-654-3210"}'

# Delete user
curl -X DELETE http://localhost:8080/api/users/1
```

## Performance Comparison

To compare performance across frameworks:

1. Build native images for all frameworks
2. Measure startup time: `time ./application`
3. Measure memory usage: `ps aux | grep application`
4. Run load tests with tools like Apache Bench or wrk
5. Monitor resource usage during operation

## Framework-Specific Notes

### Raw (Spring Boot)
- Uses traditional Spring Boot with native image support
- JPA/Hibernate for data persistence
- Maven-based build

### Helidon
- Oracle's lightweight framework
- Uses Helidon DB Client for database access
- Manual SQL queries with parameterized statements
- Maven-based build

### Micronaut
- Modern, compile-time framework
- JPA/Hibernate with Micronaut Data
- Gradle-based build
- Excellent native image support

### Quarkus
- Supersonic Subatomic Java
- Hibernate ORM with Panache
- Maven-based build
- Excellent native image support

### Spring Native
- Spring Boot with native image optimization
- Uses Spring AOT (Ahead-of-Time) compilation
- JPA/Hibernate for data persistence
- Maven-based build

## Contributing

Feel free to contribute improvements, additional frameworks, or performance benchmarks to this comparison project.
