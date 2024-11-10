**Gym Management App**

A Spring Boot backend application designed to efficiently manage gym classes and memberships.
Features:
Authentication
•  JWT-based authentication system
•  Role-based access control (Admin and Member roles)
•  Secure password encryption using BCrypt
Member Management
•  Create, update, and delete member profiles
•  Search members by name
•  Pagination and sorting capabilities for member lists
•  Gym class enrollment status
•  Authentication credentials
Gym Classes
•  Create and manage gym classes
•  Upload class images
•  Class enrollment system
•  Image management for class representation
API Documentation: Swagger without authentication

Getting Started
1. Clone the repository
2. Configure application.properties with your database settings
3. Run the Spring Boot application
4. Access the API endpoints with Postman through http://localhost:8080 
"/public" are accessed from both member and admin role,  "/admin" are accessed only by admin role
4. Access swagger through http://localhost:8080/swagger-ui/index.html
Default username:user2 (Admin) or user1(Member)
	      password:123

Prerequisites
•  Java 17 or higher
•  Maven
•  Spring boot
•  MySQL database
