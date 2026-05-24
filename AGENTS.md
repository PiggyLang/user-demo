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

## Java File Header Rules

- Every Java type must have a class-level Javadoc before annotations.
- The Javadoc must include a short responsibility description and `@author liulang`.
- Do not add `@date` or `@since` unless the user explicitly asks.

## Data Safety Rules

- User deletion must use logical delete by default.
- Do not use physical delete for business entities unless the user explicitly asks for it.
- For MyBatis-Plus entities, use `@TableLogic` and a `deleted` column.
- Before implementing any delete behavior, state whether it is logical delete or physical delete.
- Integration tests must verify that delete APIs hide records from normal queries while preserving rows in the database.

## Workflow

1. Explain the step before making changes.
2. Modify files.
3. Show the diff after changes.
4. Run `mvn compile` at the end.
5. If compilation fails, fix automatically and rerun.
