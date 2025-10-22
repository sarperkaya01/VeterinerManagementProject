# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Veteriner Management Project is a Spring Boot REST API for managing a veterinary clinic. The application handles customers, animals (pets), doctors, appointments, vaccines, and available appointment dates.

**Tech Stack:**
- Java 21
- Spring Boot 3.5.6
- Spring Data JPA
- Spring Security 6 with JWT (JJWT 0.12.3)
- PostgreSQL (hosted on CockroachDB)
- Lombok
- ModelMapper (for DTO mapping)
- Maven

## Common Development Commands

### Build & Run
```bash
# Build the project
./mvnw clean package

# Run the application
./mvnw spring-boot:run

# Run with debug logging
./mvnw spring-boot:run -Dspring-boot.run.arguments="--debug"
```

### Testing
```bash
# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=VetManagementApplicationTests

# Run tests with coverage
./mvnw test jacoco:report
```

### Dependency Management
```bash
# Check for dependency updates
./mvnw versions:display-dependency-updates

# Update all dependencies
./mvnw versions:use-latest-releases
```

## Project Architecture

### Directory Structure
- `src/main/java/com/veto/vetManagement/`
  - `Controller/` - REST endpoints (6 controllers for each entity + AuthController)
  - `Services/` - Business logic layer (AuthService + entity services)
  - `Security/` - Security configuration and JWT utilities (SecurityConfig, JwtTokenProvider, JwtAuthenticationFilter)
  - `DAO/` - Repository interfaces (Spring Data JPA, includes UserRepository)
  - `Entities/` - JPA entity classes (Customer, Animal, Doctor, Appointment, AvailableDate, Vaccine, User)
  - `DTO/` - Data Transfer Objects organized by entity (each has SaveRequest, UpdateRequest, Response + Auth DTOs)
  - `Util/` - Utilities including exception handling, ModelMapper configuration, and custom exceptions

### Layered Architecture Pattern

The application follows a traditional three-tier architecture:

1. **Controller Layer** (`Controller/`)
   - Handles HTTP requests/responses
   - Performs DTO-to-Entity mapping using ModelMapper
   - Returns ResponseEntity with appropriate HTTP status codes
   - Each controller corresponds to one entity (CustomerController, AnimalController, etc.)

2. **Service Layer** (`Services/`)
   - Contains business logic
   - Handles entity relationships and validation
   - Throws custom exceptions (NotFoundException, AlreadyExistsException, AppointmentConflictException)
   - Each service class manages one entity type

3. **Data Access Layer** (`DAO/`)
   - Spring Data JPA repositories
   - Provides custom query methods (e.g., `findByNameContainingIgnoreCase`, `findByCustomerId`)
   - Handles database operations

### Key Design Patterns

**DTO Pattern**: All controllers use separate Request DTOs for create/update operations and Response DTOs for output. This decouples the API contract from internal entity structure.

**Exception Handling**: Custom exceptions are defined in `Util/` and handled globally by `GlobalExceptionHandler.java` using `@RestControllerAdvice`, mapping to appropriate HTTP status codes:
- `NotFoundException` → 404
- `AlreadyExistsException` → 409
- `AppointmentConflictException` → 409

**Dependency Injection**: All classes use constructor injection (no field injection). Services and repositories are autowired through constructors.

### Entity Relationships

- **Customer** - owns multiple Animals and has many Appointments (as owner)
- **Animal** - belongs to one Customer
- **Doctor** - can have multiple Appointments and AvailableDates
- **Appointment** - connects Customer, Animal, and Doctor with a specific date/time
- **AvailableDate** - represents time slots available for a Doctor
- **Vaccine** - appears to be for recording vaccinations (specific relationships to be verified)

### Database Configuration

- PostgreSQL via CockroachDB cloud
- Connection properties in `application.properties`
- Hibernate DDL strategy: `update` (safe for development, updates schema on startup)
- Connection pooling with HikariCP (max 10 connections)
- SQL logging enabled for development (`spring.jpa.show-sql=true`)

## Important Patterns & Conventions

### Adding a New Entity Endpoint

When adding a new REST endpoint for an entity:

1. Create Entity class in `Entities/` with JPA annotations
2. Create Repository in `DAO/` extending `JpaRepository`
3. Create Service in `Services/` with business logic methods
4. Create DTOs in `DTO/{EntityName}/` (SaveRequest, UpdateRequest, Response)
5. Create Controller in `Controller/` with standard CRUD endpoints
6. Use ModelMapper in controller to convert between DTOs and entities
7. Throw appropriate custom exceptions in service layer

### Service Method Patterns

Services follow consistent patterns:
- `getById(id)` - retrieves single entity, throws NotFoundException if not found
- `findAll()` - retrieves all entities
- `findBy*()` - custom search methods based on entity properties
- `save()` - persists new entity, may validate relationships
- `update()` - updates existing entity, validates entity exists first
- `delete()` - removes entity from database

### Controller Method Patterns

Controllers consistently:
- Use appropriate HTTP methods (GET, POST, PUT, DELETE)
- Use ModelMapper to convert entities to/from DTOs
- Return ResponseEntity with proper HTTP status codes (200 OK, 201 CREATED, 204 NO_CONTENT, etc.)
- Use @RequestParam for query parameters and @PathVariable for path parameters
- Collect stream results into List (traditional approach, not reactive)

## Important Configuration

- **Application Name**: `vetManagement`
- **Base API Path**: `/api/` (all endpoints start with this)
- **Java Version**: 21
- **Lombok Enabled**: Reduces boilerplate; make sure annotation processor is configured

## Authentication & Authorization

### JWT Implementation

The application uses Spring Security 6 with vanilla JWT (JJWT 0.12.3) for stateless authentication.

**Key Components:**
- `SecurityConfig.java` - Configures Spring Security with stateless session management, CORS, and JWT filter
- `JwtTokenProvider.java` - Handles JWT token generation, validation, and claim extraction using HMAC-SHA256
- `JwtAuthenticationFilter.java` - Filters incoming requests, validates JWT tokens, and sets authentication context
- `CustomUserDetailsService.java` - Loads user details from database for authentication
- `AuthController.java` - Exposes `/api/auth/login` and `/api/auth/register` endpoints

**Authentication Flow:**
1. Client sends credentials to `/api/auth/login` (LoginRequest: username, password)
2. AuthService authenticates using AuthenticationManager
3. JwtTokenProvider generates JWT token with username, role, and expiration claims
4. Client receives AuthResponse with Bearer token
5. Client includes token in subsequent requests: `Authorization: Bearer <token>`
6. JwtAuthenticationFilter validates token and sets authentication in SecurityContext
7. SecurityConfig authorizes requests based on user roles and URL patterns

**JWT Token Structure:**
- Algorithm: HMAC-SHA256
- Secret Key: Configurable via `jwt.secret` property (default in application.properties)
- Expiration: Configurable via `jwt.expiration-hours` property (default: 24 hours)
- Claims: username (subject), role, issued-at, expiration, issuer

**User Roles:**
- `ROLE_ADMIN` - Full access including write operations on customers
- `ROLE_VETERINARIAN` - Access to veterinary operations
- `ROLE_CUSTOMER` - Standard user access (default role for new registrations)

**Authorization Rules:**
- `/api/auth/**` - Publicly accessible (login, register)
- `GET /api/customers/**` - Authenticated users
- `POST/PUT/DELETE /api/customers/**` - ADMIN role required
- Other endpoints - Authenticated users required

**Security Configuration:**
- CSRF protection disabled (stateless JWT tokens are immune to CSRF)
- CORS enabled for localhost:3000 and localhost:8080
- Stateless session management (SessionCreationPolicy.STATELESS)
- BCryptPasswordEncoder for password hashing

### Adding Authentication to New Endpoints

When securing new endpoints:
1. Endpoint is automatically protected by SecurityConfig if it requires authentication
2. Use `@PreAuthorize` annotation for method-level security (e.g., `@PreAuthorize("hasRole('ADMIN')")`)
3. Access authenticated user via `SecurityContextHolder.getContext().getAuthentication()`
4. Extract username from JWT: `jwtTokenProvider.getUsernameFromToken(token)`

### Configuration

Add to `application.properties`:
```properties
jwt.secret=your-secret-key-min-256-bits-for-HS256
jwt.expiration-hours=24
```

## Current Limitations & Notes

- No pagination implemented for list endpoints (all results returned)
- Database credentials are in properties file (not recommended for production)
- JWT secret key should be stored in environment variables or secret management in production
- Appointment conflict detection is handled but specifics should be reviewed in AppointmentService
- Reactive/async patterns not used (traditional blocking operations)
- Token refresh mechanism not yet implemented
