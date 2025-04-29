# PMS (Product Management System)
A simple Spring Boot-based Product Management System (PMS) that supports user authentication and basic product operations.


## Features
* User Authentication (Login/Signup)
* Product Management (CRUD operations)
* Spring Security integration
* Exception Handling


## Tech Stack
* Java 17
* Spring Boot 3.3.11
* Spring Security
* Maven
* Lombok
* Postman (for API testing)


## Prerequisites
* Java 17 or higher
* Maven 3.8+
* MongoDB


## Installation
```
1. Clone the repository
git clone https://github.com/vats3650/pms.git

2. Navigate into the project directory
cd pms

3. Build the project
mvn clean install

4. Run the project
mvn spring-boot:run
```

The server will start at http://localhost:8080.


## API Endpoints
|Method | Endpoint | Description |
|-------|----------|-------------|
|POST | /auth/signup | Register a new user|
|POST | /auth/login | Login and receive a token|
|GET | /products | List all products|
|POST | /products | Create a new product|
|PUT | /products/{id} | Update an existing product|
|DELETE | /products/{id} | Delete a product|

More details you can find on Swagger UI: http://localhost:8080/swagger-ui/index.html

##### NOTE: /products/* endpoints require authentication (JWT Token). so, 1st register a user then get the token and hit /products/* endpoints with Authorization header.
