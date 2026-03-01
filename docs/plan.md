# Investment Tracker — Plan

## Phase 1: Project skeleton ✅

- [x] deps.edn with core dependencies
- [x] Integrant system wiring (config → handler → server)
- [x] Aero config with `#profile` tags
- [x] Health-check endpoint (`GET /health`)
- [x] eftest test runner + first test
- [x] Dev REPL tooling (integrant.repl)
- [x] Dockerfile

## Phase 2: Persistence layer — SQLite + next.jdbc ✅

- [x] Add next.jdbc, HikariCP, SQLite JDBC driver, migratus
- [x] Integrant component for HikariCP connection pool
- [x] Migratus migration setup (Integrant component)
- [x] Initial migration: transactions table
- [x] Repository namespace with CRUD functions
- [x] Tests for persistence layer

## Phase 3: Domain model & transaction API ✅

- [x] Transaction API namespace with handlers
- [x] `POST /transactions` — record buy/sell events
- [x] `GET  /transactions` — list transactions with optional `?symbol=` filter
- [x] `GET  /transactions/:id` — get single transaction
- [x] `DELETE /transactions/:id` — delete a transaction
- [x] API-level tests (create, list, filter, get, delete, 404)

## Phase 4: Price data integration

- [ ] Research best free market-data API (FT HTML scrape vs alternatives)
- [ ] HTTP client namespace for fetching prices
- [ ] Parse & normalise price response
- [ ] `GET /prices/:symbol?from=&to=` — historical prices
- [ ] Scheduled / on-demand price refresh

## Phase 5: Portfolio valuation

- [ ] Calculate portfolio value over time (holdings × prices)
- [ ] `GET /portfolios/:id/value?from=&to=` — time-series response
- [ ] `GET /portfolios/:id/value?date=` — portfolio value at a specific date
- [ ] Gains / losses / return calculations

## Phase 6: Containerisation & polish

- [ ] Optimise Dockerfile (uberjar build)
- [ ] docker-compose for app + database
- [ ] CI pipeline (GitHub Actions: lint, test, build image)
- [ ] Structured logging configuration
- [ ] README with running / testing instructions

---

## Persistence options (Phase 2)

> **Decision: SQLite + next.jdbc** — zero-infrastructure, file-based SQL.
> Same `next.jdbc` interface makes switching to PostgreSQL straightforward later.
