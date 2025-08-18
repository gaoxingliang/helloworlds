#!/bin/bash

# Performance Test Script for User Service
# This script creates 100 users and then calls get user 1-100 times

BASE_URL="http://localhost:8080"
API_ENDPOINT="/api/users"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== User Service Performance Test ===${NC}"
echo -e "${YELLOW}Base URL: ${BASE_URL}${NC}"
echo ""

# Function to create a user
create_user() {
    local id=$1
    local name="User${id}"
    local email="user${id}@example.com"
    local phone="555-${id}-${id}${id}${id}"
    
    curl -s -X POST "${BASE_URL}${API_ENDPOINT}" \
        -H "Content-Type: application/json" \
        -d "{\"name\":\"${name}\",\"email\":\"${email}\",\"phone\":\"${phone}\"}" \
        > /dev/null
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓ Created user ${id}${NC}"
    else
        echo -e "${RED}✗ Failed to create user ${id}${NC}"
    fi
}

# Function to get a user
get_user() {
    local id=$1
    
    curl -s -X GET "${BASE_URL}${API_ENDPOINT}/${id}" \
        -H "Content-Type: application/json" \
        > /dev/null
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓ Retrieved user ${id}${NC}"
    else
        echo -e "${RED}✗ Failed to retrieve user ${id}${NC}"
    fi
}

# Function to get all users
get_all_users() {
    echo -e "${YELLOW}Getting all users...${NC}"
    curl -s -X GET "${BASE_URL}${API_ENDPOINT}" \
        -H "Content-Type: application/json" \
        > /dev/null
    
    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓ Retrieved all users${NC}"
    else
        echo -e "${RED}✗ Failed to retrieve all users${NC}"
    fi
}

# Check if service is running
echo -e "${YELLOW}Checking if service is running...${NC}"
if curl -s -f "${BASE_URL}${API_ENDPOINT}" > /dev/null 2>&1; then
    echo -e "${GREEN}✓ Service is running${NC}"
else
    echo -e "${RED}✗ Service is not running on ${BASE_URL}${NC}"
    echo -e "${YELLOW}Please start one of the frameworks first:${NC}"
    echo "  - Raw (Spring Boot): cd raw && mvn spring-boot:run"
    echo "  - Helidon: cd helidon && mvn clean compile exec:java -Dexec.mainClass=\"com.example.UserServiceApplication\""
    echo "  - Micronaut: cd micronaut && ./gradlew run"
    echo "  - Quarkus: cd quarkus && ./mvnw quarkus:dev"
    echo "  - Spring Native: cd spring-native && mvn spring-boot:run"
    exit 1
fi

echo ""

# Phase 1: Create 100 users
echo -e "${BLUE}=== Phase 1: Creating 100 Users ===${NC}"
start_time=$(date +%s.%N)

for i in {1..100}; do
    create_user $i
done

end_time=$(date +%s.%N)
create_duration=$(echo "$end_time - $start_time" | bc)
echo -e "${YELLOW}Created 100 users in ${create_duration} seconds${NC}"
echo ""

# Phase 2: Get all users once
echo -e "${BLUE}=== Phase 2: Getting All Users ===${NC}"
start_time=$(date +%s.%N)
get_all_users
end_time=$(date +%s.%N)
get_all_duration=$(echo "$end_time - $start_time" | bc)
echo -e "${YELLOW}Retrieved all users in ${get_all_duration} seconds${NC}"
echo ""

# Phase 3: Get individual users 1-100
echo -e "${BLUE}=== Phase 3: Getting Individual Users 1-100 ===${NC}"
start_time=$(date +%s.%N)

for i in {1..100}; do
    get_user $i
done

end_time=$(date +%s.%N)
get_individual_duration=$(echo "$end_time - $start_time" | bc)
echo -e "${YELLOW}Retrieved 100 individual users in ${get_individual_duration} seconds${NC}"
echo ""

# Summary
echo -e "${BLUE}=== Performance Summary ===${NC}"
echo -e "${YELLOW}Create 100 users: ${create_duration} seconds${NC}"
echo -e "${YELLOW}Get all users: ${get_all_duration} seconds${NC}"
echo -e "${YELLOW}Get 100 individual users: ${get_individual_duration} seconds${NC}"
echo -e "${YELLOW}Total time: $(echo "$create_duration + $get_all_duration + $get_individual_duration" | bc) seconds${NC}"
echo ""

# Optional: Clean up by deleting all users
read -p "Do you want to delete all users? (y/n): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo -e "${BLUE}=== Cleaning Up: Deleting All Users ===${NC}"
    start_time=$(date +%s.%N)
    
    for i in {1..100}; do
        curl -s -X DELETE "${BASE_URL}${API_ENDPOINT}/${i}" > /dev/null
        if [ $? -eq 0 ]; then
            echo -e "${GREEN}✓ Deleted user ${i}${NC}"
        else
            echo -e "${RED}✗ Failed to delete user ${i}${NC}"
        fi
    done
    
    end_time=$(date +%s.%N)
    delete_duration=$(echo "$end_time - $start_time" | bc)
    echo -e "${YELLOW}Deleted 100 users in ${delete_duration} seconds${NC}"
fi

echo -e "${GREEN}Performance test completed!${NC}"
