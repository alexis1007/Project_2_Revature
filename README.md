# 🏦 Loan Management System

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-19.0.0-blue.svg)](https://reactjs.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Latest-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📑 Table of Contents

- [Project Overview](#Project-Overview)
- [Features](#Key-Features)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Endpoints](#endpoints)
- [Setup Instructions](#setup-instructions)
- [Testing](#testing)
- [Logging](#logging)
- [Deliverables](#deliverables)
- [Future Enhancements](#future-enhancements)

## Project Overview

This **Loan Management System** is a comprehensive financial application developed as part of **Iteration 2 (Project 1)**. It represents a significant evolution from our previous system, transforming a Javalin-JDBC implementation into a modern, scalable full-stack application powered by **Spring Boot** and **React**.

The system facilitates seamless loan processing workflows between borrowers and managers through an intuitive interface with robust backend services. It features comprehensive authentication, profile management, and loan application processing with role-based access control.

---

## Key Features

### **1. User Registration & Authentication**
- **Secure Authentication Flow:**
  - `POST /api/auth/register`: Create new accounts with encrypted password storage
  - `POST /api/auth/login`: JWT-based authentication with configurable token expiration
  - `POST /api/auth/logout`: Session termination with token invalidation

- **Enhanced Security:**
  - Password hashing using BCrypt with salting for secure credential storage
  - Protection against common vulnerabilities:
    - CSRF: Using JWT tokens instead of cookies for authentication
    - XSS: Input validation and sanitization in both front-end and back-end
  - Token-based authorization with proper signature verification for all protected endpoints

- **Role-Based Access Control:**
  - Dynamic permission system based on database-stored roles
  - Fine-grained access control (`ROLE_regular` and `ROLE_manager`)
  - Authorization guards on sensitive operations

### **2. User Profile Management**
- **Comprehensive Profile Features:**
  - Complete user information management
  - Data validation and sanitization

- **Account Management:**
  - Self-service profile updates
  - Secure account deletion with data integrity protection
  - Privacy controls for personal information

### **3. Loan Application Processing**
- **User-Friendly Application Flow:**
  - Intuitive loan creation interface
  - Real-time status tracking
  - Application history with detailed records
  
- **Role-Specific Capabilities:**
  - **For Borrowers:**
    - Multiple concurrent loan applications
    - Application editing before approval
    - Personal loan history dashboard
  
  - **For Managers:**
    - Comprehensive application review interface
    - Individual loan approval/rejection workflow
    - Basic filtering by loan status (pending, approved, rejected)

### **4. Role Management**
- Managers can manage user roles (`UserType`):
  - View all roles.
  - Create, update, or delete roles.

---

## **Technology Stack**

### **Back-End:**
- **Java 17**
- **Spring Boot**
- **Spring MVC** (RESTful APIs)
- **Spring Data JPA** (Data persistence)
- **PostgreSQL** (Database)
- **JWT** (Authentication)
- **Logback** (Logging)

### **Front-End:**
- **React** (with TypeScript)
- **HTML/CSS/JavaScript**

### **Testing:**
- **JUnit 5** (Unit testing)
- **Mockito** (Mocking dependencies)

---

## **Architecture**

### **Back-End:**
- **Controllers:** Handle HTTP requests and responses.
- **Services:** Contain business logic.
- **Repositories:** Use Spring Data JPA for database operations.
- **Entities:** Represent database tables (e.g., `User`, `LoanApplication`, `UserType`).

### **Front-End:**
- **Components:** Modular React components for UI.
- **Services:** Handle API calls to the back-end.
- **Routing:** React Router for navigation.

---

## **Endpoints**

### **Authentication:**
- `POST /auth/register`: Register a new user.
- `POST /auth/login`: Authenticate and receive a JWT token.

### **User Profiles:**
- `GET /user-profiles/{id}`: View a user profile.
- `PUT /user-profiles/{id}`: Update a user profile.
- `DELETE /user-profiles/{id}`: Delete a user profile.

### **Loan Applications:**
- `POST /loans`: Create a new loan application.
- `GET /loans`: View all loans (manager only).
- `GET /loans/user/{id}`: View loans for a specific user.
- `PUT /loans/{id}`: Update a loan application.
- `PUT /loans/{id}/approve`: Approve a loan (manager only).
- `PUT /loans/{id}/reject`: Reject a loan (manager only).

### **User Types:**
- `GET /api/user_types`: View all user types.
- `POST /api/user_types`: Create a new user type (manager only).
- `PUT /api/user_types/{id}`: Update a user type (manager only).
- `DELETE /api/user_types/{id}`: Delete a user type (manager only).

---

## **Setup Instructions**

### **1. Back-End Setup**
1. Install **Java 17** and **Maven**.
2. Configure the database in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/project1
   spring.datasource.username=postgres
   spring.datasource.password=password
   ```
3. Run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
4. The back-end will be available at http://localhost:7070.

### **2. Front-End Setup**
1. Install Node.js (version 16 or higher).
2. Navigate to the React app directory:
```bash
cd react-app/loan-management-app
```
3. Install dependencies:
    ```bash
    npm install
    ```
4. Start the development server:
    ```bash
    npm run dev
    ```
5. The front-end will be available at http://localhost:5173.

---

## **Testing**

### **Unit Tests:**
- Run back-end tests using Maven:
  ```bash
  mvn test
  ```

### **Example Test (JUnit + Mockito):**
```java
@Test
public void testValidateUser() {
    User user = new User();
    user.setUsername("testuser");
    user.setPasswordHash(BCrypt.hashpw("password", BCrypt.gensalt(4)));
    when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

    Optional<User> validatedUser = userService.validateUser("testuser", "password");

    assertTrue(validatedUser.isPresent());
    assertEquals("testuser", validatedUser.get().getUsername());
}
```

---

## **Logging**

### **Logback Configuration:**
- Logs are configured in `src/main/resources/application.properties`:
  ```properties
  logging.level.root=INFO
  logging.level.org.example=DEBUG
  logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{36} - %msg%n
  ```

---

## **Deliverables**

### **Source Code:**
- Back-end: Spring Boot application.
- Front-end: React application.

### **Database Scripts:**
- `data.sql` for initializing the database.

### **Documentation:**
- This README file with setup instructions and API details.

---

## **Future Enhancements**
- Add integration tests for RESTful endpoints.
- Improve UI/UX with better styling and validation.
- Implement email notifications for loan status updates.