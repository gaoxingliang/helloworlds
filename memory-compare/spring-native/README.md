# User Service - Spring Native

A simple user information service built with Spring Native and GraalVM native image.

## Features

- RESTful API for user management
- MySQL database integration
- JPA/Hibernate for data persistence
- GraalVM native image support with Spring AOT

## Prerequisites

### For JVM Mode
- Java 21
- Maven 3.8+
- MySQL 8.0+

### For Native Image Mode
- **Option 1: Docker-based build (Recommended for beginners)**
  - Docker Desktop
  - Java 21
  - Maven 3.8+
  - MySQL 8.0+

- **Option 2: Local native build (Advanced)**
  - GraalVM 21+ with native-image tool
  - Native build tools for your platform
  - Java 21
  - Maven 3.8+
  - MySQL 8.0+

### Installing GraalVM Native Image (for local builds)

#### macOS
```bash
# Using Homebrew
brew install --cask graalvm/tap/graalvm-jdk-21

# Or download from Oracle
# https://www.graalvm.org/downloads/

# Install native-image tool
gu install native-image
```

#### Linux
```bash
# Download GraalVM from Oracle
# https://www.graalvm.org/downloads/

# Install native-image tool
gu install native-image

# Install build dependencies
sudo apt-get install build-essential libz-dev zlib1g-dev
```

#### Windows
```bash
# Download GraalVM from Oracle
# https://www.graalvm.org/downloads/

# Install native-image tool
gu install native-image

# Install Visual Studio Build Tools
# https://visualstudio.microsoft.com/downloads/#build-tools-for-visual-studio-2022
```

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

#### Option 1: Docker-based Build (Recommended)
This approach uses Docker to build the native image, which is easier and doesn't require local GraalVM installation:

```bash
# Build native image using Docker
mvn spring-boot:build-image

# Run the native image
docker run -p 8080:8080 user-service-spring-native:1.0.0
```

#### Option 2: Local Native Build
This approach builds the native image locally using your installed GraalVM:

```bash
# Build native image locally
mvn -Pnative native:build

# Run the native executable
./target/user-service-spring-native
```

#### Option 3: Compile and Run in One Step
```bash
# Compile and run native image
mvn -Pnative native:compile spring-boot:run
```

## Build Commands Reference

| Command | Description |
|---------|-------------|
| `mvn clean compile` | Compile Java classes |
| `mvn spring-boot:run` | Run in JVM mode |
| `mvn spring-boot:build-image` | Build Docker native image |
| `mvn -Pnative native:build` | Build local native executable |
| `mvn -Pnative native:compile spring-boot:run` | Compile and run native |
| `mvn clean package` | Build JAR file |
| `mvn -Pnative native:compile` | Compile native classes |

## Performance Comparison

Native images typically offer:
- **Faster startup time** (seconds vs minutes)
- **Lower memory footprint** (MB vs GB)
- **Better resource utilization**
- **Improved cold start performance**

## Troubleshooting

### Common Issues

1. **Native build fails with reflection errors**
   - Ensure all classes used via reflection are properly configured
   - Check Spring AOT configuration

2. **Memory issues during native build**
   - Increase heap size: `export MAVEN_OPTS="-Xmx4g"`
   - Use Docker-based build for limited resources

3. **GraalVM not found**
   - Verify GraalVM installation: `java -version`
   - Check PATH environment variable
   - Install native-image: `gu install native-image`

4. **Build tools missing (Linux)**
   ```bash
   sudo apt-get install build-essential libz-dev zlib1g-dev
   ```

### Environment Variables

For local native builds, you may need to set:
```bash
export GRAALVM_HOME=/path/to/graalvm
export PATH=$GRAALVM_HOME/bin:$PATH
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

## Performance Testing

Compare startup times:
```bash
# JVM mode
time mvn spring-boot:run

# Native mode (Docker)
time docker run -p 8080:8080 user-service-spring-native:1.0.0

# Native mode (Local)
time ./target/user-service-spring-native
```
