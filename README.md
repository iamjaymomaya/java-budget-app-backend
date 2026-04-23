# Budget App Backend (Spring Boot)

A REST API backend for a simple personal budgeting app. It supports user authentication (JWT) and managing budget data such as accounts, categories, and transactions, along with reporting endpoints.

## What This App Is About

This project is a Spring Boot backend for tracking spending/income.

- **Auth**: Register/login and receive a JWT for protected routes
- **Accounts**: Create/update/list/delete accounts (e.g., cash, bank, credit)
- **Categories**: Create/update/list/delete categories (e.g., groceries, rent)
- **Transactions**: Record income/expense transactions tied to accounts/categories
- **Reports**: Summary/report-style endpoints (see `ReportController`)

## Requirements

- **Java**: 21 (configured via `pom.xml`)
- **Maven**:
  - Recommended: use the included **Maven Wrapper** (no separate Maven install needed)
  - Wrapper Maven distribution: **3.9.12** (see `.mvn/wrapper/maven-wrapper.properties`)

## Configuration

Default settings are in `src/main/resources/application.properties`:

- **Server port**: `9090`
- **Database**: PostgreSQL
  - URL: `jdbc:postgresql://localhost:5432/budget-app`
  - Username: `postgres`
  - Password: `password`
- **JWT**:
  - `JWT_SECRET` (optional env var; defaults to a development value)
  - `JWT_EXP_MINUTES` (optional; defaults to `120`)

If your local Postgres credentials/db differ, update `application.properties` accordingly.

## How To Run

From the repository root:

1) Start PostgreSQL and create a database named `budget-app`.

2) Run the API:

```bash
./mvnw spring-boot:run
```

The server will start on:

- `http://localhost:9090`

### Run Tests

```bash
./mvnw test
```

## Postman Collection

A Postman collection is included at:

- `Budget App.postman_collection.json`

### Import into Postman

1) Open Postman
2) Click **Import**
3) Select **File**
4) Choose `Budget App.postman_collection.json`

After importing:

- Set the base URL to `http://localhost:9090`
- For protected endpoints, login/register first and use the returned JWT as the `Authorization: Bearer <token>` header (or configure it in Postman as a collection/environment variable).
