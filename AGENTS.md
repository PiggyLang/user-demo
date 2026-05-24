# AGENTS.md

## Project Goal

Build a Spring Boot 2.7.x user management system for learning the Codex workflow.

## Technology Constraints

- Java 8
- Maven
- Spring Boot 2.7.x
- MyBatis-Plus
- MySQL
- Lombok
- No JPA
- No Hibernate

## Functional Requirements

- User create, read, update, delete
- User paginated query
- Fuzzy search by `username`
- Exact filter by `status`

## Coding Standards

- Use constructor injection.
- Do not use field injection.
- Controllers must not contain business logic.
- All API responses must use `Result<T>`.

## Workflow

1. Explain the step before making changes.
2. Modify files.
3. Show the diff after changes.
4. Run `mvn compile` at the end.
5. If compilation fails, fix automatically and rerun.
