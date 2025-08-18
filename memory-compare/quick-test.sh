#!/bin/bash

# Quick Performance Test Script
# Creates 100 users and gets them individually

BASE_URL="http://localhost:8080"
API_ENDPOINT="/api/users"

echo "=== Quick Performance Test ==="
echo "Base URL: $BASE_URL"
echo ""

# Check if service is running
echo "Checking if service is running..."
if curl -s -f "$BASE_URL$API_ENDPOINT" > /dev/null 2>&1; then
    echo "✓ Service is running"
else
    echo "✗ Service is not running on $BASE_URL"
    echo "Please start one of the frameworks first"
    exit 1
fi

echo ""

# Create 100 users
echo "=== Creating 100 Users ==="
start_time=$(date +%s.%N)

for i in {1..100}; do
    curl -s -X POST "$BASE_URL$API_ENDPOINT" \
        -H "Content-Type: application/json" \
        -d "{\"name\":\"User$i\",\"email\":\"user$i@example.com\",\"phone\":\"555-$i-$i$i$i\"}" \
        > /dev/null
    
    if [ $((i % 10)) -eq 0 ]; then
        echo "Created $i users..."
    fi
done

end_time=$(date +%s.%N)
create_duration=$(echo "$end_time - $start_time" | bc)
echo "Created 100 users in ${create_duration} seconds"
echo ""

# Get all users once
echo "=== Getting All Users ==="
start_time=$(date +%s.%N)
curl -s -X GET "$BASE_URL$API_ENDPOINT" > /dev/null
end_time=$(date +%s.%N)
get_all_duration=$(echo "$end_time - $start_time" | bc)
echo "Retrieved all users in ${get_all_duration} seconds"
echo ""

# Get individual users 1-100
echo "=== Getting Individual Users 1-100 ==="
start_time=$(date +%s.%N)

for i in {1..100}; do
    curl -s -X GET "$BASE_URL$API_ENDPOINT/$i" > /dev/null
    
    if [ $((i % 10)) -eq 0 ]; then
        echo "Retrieved $i users..."
    fi
done

end_time=$(date +%s.%N)
get_individual_duration=$(echo "$end_time - $start_time" | bc)
echo "Retrieved 100 individual users in ${get_individual_duration} seconds"
echo ""

# Summary
echo "=== Performance Summary ==="
echo "Create 100 users: ${create_duration} seconds"
echo "Get all users: ${get_all_duration} seconds"
echo "Get 100 individual users: ${get_individual_duration} seconds"
total_time=$(echo "$create_duration + $get_all_duration + $get_individual_duration" | bc)
echo "Total time: ${total_time} seconds"
echo ""

echo "Performance test completed!"
