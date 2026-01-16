# 🛡️ Spring Boot RBAC Security & Testing System

A professional Spring Boot application demonstrating **Role-Based Access Control (RBAC)**, comprehensive **JUnit 5 Integration Testing**, and automated **CI/CD via GitHub Actions**.

---

## 🚀 Key Features

* **Secure Authentication**: Custom login system with distinct roles (`ADMIN`, `USER`).
* **Role-Based Authorization**: 
    * `ADMIN`: Can perform all CRUD operations (Add, Update, Delete, View).
    * `USER`: Can only View student data.
* **JUnit Integration Tests**: 100% test coverage for security endpoints ensuring no unauthorized access.
* **CI/CD Pipeline**: Automated builds and tests using GitHub Actions on every push.
* **H2 Database**: Lightweight in-memory database for fast testing cycles.

---

## 🛠️ Tech Stack

* **Backend**: Java 17+, Spring Boot 3.x
* **Security**: Spring Security (Basic Auth + JSON Login)
* **Testing**: JUnit 5, MockMvc, AssertJ
* **DevOps**: GitHub Actions (Maven Workflow)
* **DB**: H2 In-Memory Database

---

## 🧪 Testing Overview

The project includes a robust testing suite `SecurityIntegrationTest.java` that validates:
1. **Login Success/Failure**: Checks for valid credentials and correct role mapping.
2. **Access Control**: Verifies that a `USER` cannot delete or update data (Expected: `403 Forbidden`).
3. **Unauthenticated Access**: Ensures public users cannot access protected APIs (Expected: `401 Unauthorized`).

---

## 📦 CI/CD & Artifacts

Every time code is pushed to the `main` branch:
1. **GitHub Actions** triggers a full Maven build.
2. All **JUnit tests** are executed in the cloud.
3. A deployment-ready **JAR Artifact** is generated (Size: ~12.1 MB).

---

## 📖 How to Run

1. Clone the repo: `git clone https://github.com/Bablupuri1/springboot-rbac-security-tests.git`
2. Run tests: `mvn test`
3. Start application: `mvn spring-boot:run`
