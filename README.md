# Queue Management System (QMS)

A simple backend system for managing queues — similar to a token/ticket system used at banks or clinics.

**Status:** 🚧 Work in progress

## Overview

Admins can create a queue, and clients can join it to get a token. Admins can view live queue counts, mark tokens as done, and close a queue when no more entries should be accepted.

## Tech Stack

- Java 17
- Spring Boot
- MySQL
- Redis (planned — for caching estimated wait time)
- Docker (planned)

## Modules

- **Admin** — admin creation and lookup
- **Queue** — create, close, delete, and fetch queues (done)
- **Token** — client join, mark done, list/count tokens (in progress)

## Running Locally

```bash
./mvnw spring-boot:run
```

Configure your MySQL connection in `application.properties` before running.

## Notes

This project is being built incrementally as a learning exercise, with a focus on backend fundamentals — entity relationships, service/repo layering, and eventually authentication (JWT) and API versioning.
