# Order Management System

## Description

The Order Management System is a microservices-based application that allows users to manage orders efficiently. It consists of two main services: the Order Service and the Member Service, which work together to provide functionalities such as creating, updating, deleting, and retrieving orders. The system also implements JWT-based authentication and authorization for secure access.

## Technologies Used

- **Java 17**
- **Spring Boot**: Framework for developing Java applications.
- **Spring Security**: For authentication and authorization.
- **JWT**: authentication for secure access.
- **JPA/Hibernate**: For database management.
- **REST API**: For communication between client and server.
- **Postman**: For testing API endpoints.

## Main Endpoins

## Authentication

- **POST /login/**:
- **POST /register/**:


## ORDERS

- **GET /orders/**:
- **GET /orders/order/{id}**:
- **GET /orders/order/member/{id}**:
- **POST /orders/create**:
- **PUT /orders/update/{id}**:
- **DELETE /orders/delete/{id}**:


## Getting Started

### Prerequisites
- Java 17 or higher
- Maven
- MYSQL (or your chosen database)
- IDE (e.g., IntelliJ, Eclipse, VScode)

## Security and Authentication

The application uses Spring Security for securing the REST API. Users must authenticate to access certain endpoints. 

### Token-Based Authentication

Upon successful login, a JWT token is returned, which should be included in the `Authorization` header for subsequent requests to protected endpoints.

### Roles

- **ADMIN**: Full access to manage orders and members.
- **USER**: Access to search and view orders.