# 🤖 Human-in-the-Loop Review Platform
CURRENTLY IN DEVELOPMENT
_A full-stack playground for experimenting with LLM-extracted knowledge, manual curation, and knowledge-graph persistence._

<div align="center">
  <!-- Badges – replace the URLs after you push to GitHub -->
  <a href="https://github.com/your-org/hitl-review/actions"><img alt="CI" src="https://img.shields.io/github/actions/workflow/status/your-org/hitl-review/ci.yml?branch=main"></a>
  <a href="https://github.com/your-org/hitl-review/blob/main/LICENSE"><img alt="License" src="https://img.shields.io/github/license/your-org/hitl-review"></a>
</div>

---

## ✨ What’s inside

| Layer | Tech | Purpose |
|-------|------|---------|
| **UI** | Angular 18 | Curate & fix `Person` and `Idea` records (more entities coming). |
| **API** | Spring Boot 3, Spring Data JPA, Spring Data Neo4j | Portable service layer that can persist to **MySQL**, **PostgreSQL**, or **Neo4j**. |
| **(Optional)** | GraphQL (SPQR / Spring for GraphQL) | In case you prefer a single graph endpoint over REST. |
| **LLM Worker** | Python (FastAPI) | Long-term home for async extraction tasks. |
| **Tests** | Jest (Angular), JUnit 5 + Testcontainers (Java), PyTest (Python) | Because learning happens by breaking stuff. |
| **Infra** | Docker Compose | One command spins up DBs (MySQL, Postgres+pgAdmin, Neo4j) and local services. |

---

## 🚀 Quick start

```bash
# 1. Clone
git clone https://github.com/LKS-BY/Selfish-HIL.git
cd hitl-review

For Development
# 2. Boot the stack
docker compose up -d  (mysql / neo4j / )       # ⇒ MySQL @ 3306, Postgres @ 5432, pgAdmin @ 5050, Neo4j @ 7474

# 3. Run the Spring API
./mvnw spring-boot:run       # uses MySQL by default (see `SPRING_PROFILES_ACTIVE`)

# 4. Start the Angular UI
cd ui
npm install
ng serve                      # http://localhost:4200

#end bash 

```

# 🗂️ Repo layout

.
├── api/                # Java Spring Boot source
│   ├── src/main/java
│   └── src/test/java
├── ui/                 # Angular 17 app
│   ├── src/app
│   └── src/environments
├── worker/             # Python FastAPI (LLM extraction micro-service)
│   └── tests/
├── docker-compose.yml
└── docs/               # architecture diagrams, ADRs, etc.


# ⚙️ Configuration
File	What it does
docker-compose.yml	Defines DBs & admin UIs (MySQL, pgAdmin+Postgres, Neo4j Browser).
api/src/main/resources/application-*.yml	Separate Spring profiles (mysql, postgres, neo4j).
ui/src/environments/*.ts	Point the Angular proxy at the correct API port.
🛣 Roadmap

Role-based auth (Keycloak or Spring Security + OAuth2).

WebSockets – push live LLM updates into the reviewer’s screen.

GraphQL gateway, federating SQL + Neo4j transparently.

Vector-search playground (PGVector, Neo4j Vector Index, etc.).

CI/CD GitHub Actions deploying preview environments with Docker Compose-CI.

Documentation site (Docusaurus) fed from /docs.