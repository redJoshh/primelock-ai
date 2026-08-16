# PrimeLock AI 🧠⏱️

> Eliminating developer context-switching tax by optimizing deep work around Biological Prime Time (BPT).

PrimeLock AI is a polyglot microservice application that combines developer workspace state management with behavioral productivity frameworks. It automatically captures Git states and terminal histories upon session pauses, utilizing LLMs and vector search (`pgvector`) to summarize and restore developer context instantly.

## 🏗️ Core Architecture & Tech Stack

* **Core Engine (Java 21 / Spring Boot 3.2):** Handles JWT authentication, user tenancy, daily "Rule of 3" tracking, and Biological Prime Time (BPT) domain logic.
* **AI Worker (Python 3.12 / FastAPI):** *(In Development)* Asynchronous worker handling Git CLI diff parsing, LLM summarization, and text embedding generation.
* **Data Layer:** PostgreSQL 16 (utilizing HNSW indexing via the `pgvector` extension) and Redis 7 Alpine.
* **Infrastructure:** Fully containerized isolated local development environment via Docker Compose.

primelock-ai/
├── core-engine/       # Spring Boot REST API & Domain Models
├── ai-worker/         # (WIP) Python FastAPI AI Microservice
└── docker-compose.yml # Shared Infrastructure Configuration

## 🚀 Getting Started (Local Development)

### Prerequisites
* Docker Desktop (with WSL2 backend)
* Java 21 JDK (Amazon Corretto or Eclipse Temurin)
* Maven 

### 1. Boot the Infrastructure
The database and caching layers are fully containerized. To spin up PostgreSQL and Redis, run:
```bash
docker compose up -d
cd core-engine
./mvnw spring-boot:run
