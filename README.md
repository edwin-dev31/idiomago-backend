<p align="center">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 17">
  <img src="https://img.shields.io/badge/Spring_Boot-3.4.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot 3.4.4">
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL">
  <img src="https://img.shields.io/badge/OAuth2-Google%20|%20GitHub%20|%20Facebook-EB5424?style=for-the-badge&logo=auth0&logoColor=white" alt="OAuth2">
  <img src="https://img.shields.io/badge/OpenAI-Integration-412991?style=for-the-badge&logo=openai&logoColor=white" alt="OpenAI">
  <img src="https://img.shields.io/badge/MIT-License-green?style=for-the-badge" alt="MIT License">
</p>

# IdiomaGo Backend

Backend for a language learning platform built with **Spring Boot 3** and **Java 17**. Provides secure authentication (JWT + OAuth2), word translation management, AI-powered vocabulary generation, and multi-language support.

## Features

| Feature | Description |
|---------|-------------|
| **Authentication** | Register, login, email verification + OAuth2 (Google, GitHub, Facebook) |
| **Word Management** | CRUD for translations across multiple languages |
| **AI Vocabulary** | OpenAI-powered intelligent word suggestions |
| **Multi-Language** | Built-in support for multiple languages |
| **Email Service** | Verification emails on registration |
| **PostgreSQL** | Persistent relational data via JPA/Hibernate + Flyway migrations |

## Tech Stack

| Technology | Purpose |
|------------|---------|
| **Java 17** | Language |
| **Spring Boot 3.4.4** | Core framework |
| **Spring Security** | JWT + OAuth2 authentication |
| **Spring Data JPA / Hibernate** | ORM & persistence |
| **PostgreSQL** | Relational database |
| **Flyway** | Database migrations |
| **MapStruct** | Object mapping |
| **JJWT** | JSON Web Token handling |
| **Jakarta Mail** | Email verification |
| **OpenAI API** | AI vocabulary generation |
| **Docker** | Containerization |

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `BACKEND_BASE_URL` | `http://localhost:1731/idiomago` | Backend API base URL |
| `FRONTEND_BASE_URL` | `http://localhost:5173` | Frontend app URL for CORS and redirects |

Set these in your environment or via `application-prod.properties`.

## Prerequisites

- JDK 17+
- Maven
- PostgreSQL (or Docker)
- OpenAI API key (for AI features)
- OAuth credentials (Google, GitHub, Facebook) — optional

## Getting Started

### 1. Clone

```bash
git clone https://github.com/edwin-dev31/idiomago-backend.git
cd idiomago-backend
```

### 2. Start the database

```bash
docker compose up -d
```

### 3. Build

```bash
./mvnw clean package
```

### 4. Run

```bash
java -jar target/idiomago-0.0.1-SNAPSHOT.jar
```

Server starts at **`http://localhost:1731/idiomago`**.

## Frontend

This backend powers the [**IdiomaGo Frontend**](https://github.com/edwin-dev31/idiomago-frontend) — the React + Vite UI for the language learning platform.

## License

Distributed under the **MIT License**. See [LICENSE](./LICENSE) for more information.

---

<p align="center">
  <a href="https://github.com/edwin-dev31/idiomago-backend/issues">Report a bug</a> ·
  <a href="https://github.com/edwin-dev31/idiomago-backend/pulls">Request a feature</a>
</p>
