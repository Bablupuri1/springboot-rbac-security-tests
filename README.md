# 🛡️ Spring Boot RBAC Security & Testing System

![JUnit Tests](https://github.com/Bablupuri1/springboot-rbac-security-tests/actions/workflows/test.yml/badge.svg)

A professional Spring Boot application demonstrating **Role-Based Access Control (RBAC)**, comprehensive **JUnit 5 Integration Testing**, and automated **CI/CD via GitHub Actions**.

---

## 🚀 Key Features

- 🔐 **Secure Authentication**: Custom login system with distinct roles (`ADMIN`, `USER`).
- 🛂 **Role-Based Authorization**:
  - `ADMIN`: Full CRUD operations (Add, Update, Delete, View).
  - `USER`: Restricted to viewing student data.
- 🧪 **JUnit Integration Tests**: Validates authentication and authorization flows with MockMvc.
- ⚙️ **CI/CD Pipeline**: Automated builds and tests using GitHub Actions on every push/pull request.
- 🗄️ **H2 Database**: Lightweight in-memory DB for fast testing cycles.

---

## 🛠️ Tech Stack

- **Backend**: Java 21, Spring Boot 3.x
- **Security**: Spring Security (JWT + Role-Based Access)
- **Testing**: JUnit 5, MockMvc, AssertJ
- **DevOps**: GitHub Actions (Maven Workflow)
- **Database**: H2 In-Memory Database

---

## 🧪 Testing Overview

The project includes a robust testing suite `SecurityIntegrationTest.java` that validates:

1. **Login Success/Failure**  
   Ensures valid credentials map to correct roles.
2. **Access Control**  
   Verifies that a `USER` cannot perform restricted actions (Expected: `403 Forbidden`).
3. **Unauthenticated Access**  
   Blocks public users from protected APIs (Expected: `401 Unauthorized`).

---

## 📦 CI/CD & Artifacts

Every time code is pushed to the `main` branch:

1. **GitHub Actions** triggers a full Maven build.
2. All **JUnit tests** are executed in the cloud.
3. Recruiters can verify test status via the **badge above** or the **Actions tab**.
4. A deployment-ready **JAR Artifact** is generated (Size: ~12.1 MB).

---

## 📖 How to Run

### 🔹 Local Run
1. Clone the repo:  
   ```bash
   git clone https://github.com/Bablupuri1/springboot-rbac-security-tests.git
   cd springboot-rbac-security-tests
