# Expense Tracker

A simple REST API for tracking expenses and monthly budgets.

## What it does

- Register and log in users
- Add, view, update, and delete expenses
- View expense reports by month, category, and payment method
- Create and manage budgets

## Run it

Requirements: Java 21.

Set `JWT_SECRET` before starting the app. For example, in PowerShell:

```powershell
$env:JWT_SECRET = "replace-this-with-a-long-secret"
.\mvnw.cmd spring-boot:run
```

On macOS or Linux:

```bash
export JWT_SECRET="replace-this-with-a-long-secret"
./mvnw spring-boot:run
```

The API starts at `http://localhost:8080`.

## Main endpoints

| Endpoint | Purpose |
| --- | --- |
| `/api/auth` | Register and log in |
| `/api/expenses` | Manage expenses and view reports |
| `/api/budget` | Manage budgets and view summaries |
| `/api/users` | Manage users |

Expense and budget endpoints use a `userId` query parameter to select the user. Most create and update requests expect JSON.

## Data

The app uses an H2 file database stored under `./data`. The H2 console is enabled at `http://localhost:8080/h2-console`.

## Tests

```powershell
.\mvnw.cmd test
```