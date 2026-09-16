# ParaBank QA Automation Project

A full QA engineering project built against [ParaBank](https://parabank.parasoft.com) (Parasoft's public demo banking application), covering the complete testing lifecycle: manual test design and execution, defect management, UI automation, API testing, database validation, and CI/CD.

## Tech Stack

| Area | Tools |
|---|---|
| UI Automation | Selenium WebDriver, Java 17, TestNG, Maven (Page Object Model) |
| API Testing | Postman |
| Database Testing | H2 (in-memory), JDBC, SQL |
| Defect Management | Jira |
| CI/CD | GitHub Actions, headless Chrome |
| Version Control | Git / GitHub |

## Project Scope

- **47 manual test cases** designed across 9 ParaBank modules (Login, Registration, Account Overview, Transfer Funds, Bill Pay, Find Transactions, Update Contact Info, Request Loan, Logout)
- **21 core test cases** manually executed, with results documented
- **4 real defects** found, logged in Jira with full lifecycle simulation, and severity/priority grading
- **21 test cases automated** with Selenium + TestNG, using a Page Object Model framework with explicit waits and centralized configuration
- **6 REST API tests** built in Postman, covering positive and negative scenarios, confirming one defect exists at the API layer as well as the UI layer
- **3 database validation tests** using JDBC against an H2 in-memory database, including balance validation, JOIN-based transaction verification, and aggregate queries
- **CI/CD pipeline** via GitHub Actions, running the Maven/TestNG suite automatically on every push, with headless Chrome support

## Key Defects Found

| ID | Module | Severity | Summary |
|---|---|---|---|
| SCRUM-7 | Transfer Funds | Critical | No balance validation — transfers exceeding available balance are accepted |
| SCRUM-8 | Transfer Funds | Critical | Negative transfer amounts are accepted and silently reverse transaction direction |
| SCRUM-9 | Bill Pay | Critical | Same root cause as SCRUM-7 — no balance validation on bill payments |
| SCRUM-10 | Logout | Medium | Unhandled internal error when using the browser Back button after logout (no data exposed) |

SCRUM-7 was independently confirmed to exist at the API level as well as the UI level, tracing the defect to the backend rather than a UI display issue.

## Project Structure

```
src/test/java/com/parabank/
├── pages/       Page Object classes (one per ParaBank page)
├── tests/       TestNG test classes
└── utils/       BaseTest, ConfigReader, DatabaseConnection

config.properties       Centralized test credentials and settings
schema.sql              H2 database schema and seed data
testng.xml              TestNG suite definition
.github/workflows/      GitHub Actions CI pipeline
```

## Running the Tests

**Locally (Eclipse or command line):**
```bash
mvn test
```
This reads `testng.xml` and runs the full suite, including UI, and database tests.

**Configuration:** update `config.properties` with valid ParaBank credentials before running — ParaBank's demo server periodically resets test data.

## Known Environment Limitations

ParaBank is Parasoft's shared public demo server, not a dedicated test environment. Over the course of this project, several environment-specific behaviors were observed and documented rather than treated as defects:

- Test account data is periodically reset by the server, requiring credential updates in `config.properties`
- Running the full suite sequentially can cause balance-dependent tests (e.g., Loan approval) to be affected by earlier tests that intentionally exploit the balance-validation defects
- Live UI test execution from GitHub Actions' cloud IP addresses is unreliable, likely due to server-side restrictions on Parasoft's end; the CI pipeline itself (build, dependency resolution, headless Chrome, test execution trigger) runs correctly

## Author

John Unyime — QA Automation Engineer