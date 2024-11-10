# Gym Management App

A Spring Boot backend application designed to efficiently manage gym classes and memberships.

## Features

### Authentication
- JWT-based authentication system
- Role-based access control (Admin and Member roles)
- Secure password encryption using BCrypt

### Member Management
- Create, update, and delete member profiles
- Search members by name
- Pagination and sorting capabilities for member lists
- Gym class enrollment status
- Authentication credentials

### Gym Classes
- Create and manage gym classes
- Upload class images
- Class enrollment system
- Image management for class representation

### API Documentation
- Swagger (without authentication)

## Getting Started

1. Clone the repository
2. Configure `application.properties` with your database settings
3. Run the Spring Boot application
4. Access the API endpoints with Postman:
   - `/public`: Accessible by both Member and Admin roles
   - `/admin`: Accessible only by Admin role
5. Access Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

**Default Credentials:**
- Username: `user2` (Admin) or `user1` (Member)
- Password: `123`

## Prerequisites
- Java 17 or higher
- Maven
- Spring Boot
- MySQL database
