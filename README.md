# Receipt Processor Challenge

This repository contains a backend microservice developed in Spring Boot to extract relevant information from JSON receipt structures.

### Features
- Built following industry conventions for REST APIs.
- Utilizes an H2 in-memory database with a one-to-many relationship between Receipt and Item entities.

### Test Coverage
All test cases have been successfully passed for various scenarios.

### Running the Service with Docker
To run the service using Docker, execute the following commands:

Required Softwares and Dependencies
- Java 17

```sh
docker build -t receipt-processor .
docker run -p 8080:8080 receipt-processor
