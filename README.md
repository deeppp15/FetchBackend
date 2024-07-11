# Receipt Processor Challenge

This repository contains a backend microservice developed in Spring Boot to extract relevant information from JSON receipt structures.

### Features
- Built following industry conventions for REST APIs.
- Utilizes an H2 in-memory database with a one-to-many relationship between Receipt and Item entities.

### Testing Frameworks-
  Used Junit4 and Mockito for writing testcases and stubbing database calls.
  
### Test Coverage (Service Layer 100%)
All test cases have been successfully passed for below scenarios
- Invalid Recipet
- Valid Recipet
- Id not present for viewing getPoints
- Id present for viewing getPoints

### Running the Service with Docker
To run the service using Docker, execute the following commands:

### Required Softwares and Dependencies
- Java 17
- Maven 3.9.8
  verify Maven install by typing

  ```sh
  mvn -version
- Now go to project directory and run
  
   ```sh
   mvn clean install
- This generates a target folder in the project directory
- Now finally run the below docker commands to run this microservice in a container
  
  ```sh
  docker build -t receipt-processor .
  docker run -p 8080:8080 receipt-processor
- Go to http://localhost:8080/swagger-ui/index.html#/fetch-controller where you can test the two endpoints.
