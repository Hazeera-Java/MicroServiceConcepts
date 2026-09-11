# MicroServiceConcepts

A complete hands-on Spring Boot Microservices project designed to learn and implement microservices concepts end-to-end.

The goal of this project is not just to create APIs, but to understand **why each microservices component is required, how it works, and how it is implemented in real applications**.

---

# 1. Project Overview

## Project Name

**ParcelFlow – Shipment & Tracking Microservices Platform**

ParcelFlow is a logistics/shipment management application.

Customers can create shipments, track shipments, update shipment status, and receive notifications when important shipment events occur.

The application will be developed directly as a **proper microservices architecture**.

We will NOT create a monolithic application first and then split it into microservices.

---

# 2. Architecture

```text
                         Client
                           |
                           v
                    +-------------+
                    | API Gateway |
                    +------+------+
                           |
                    Authentication
                           |
             +-------------+-------------+
             |             |             |
             v             v             v
        User Service  Shipment Service  Tracking Service
             |             |             |
             v             v             v
           H2 DB         H2 DB          H2 DB
                           |
                           v
                         Kafka
                           |
             +-------------+-------------+
             |             |             |
             v             v             v
       Notification     Audit       Other Consumers
          Service       Service
```

---

# 3. Microservices

## 3.1 User Service

Responsible for user-related operations.

### Responsibilities

* Create user
* Get user
* Update user
* Delete user
* User profile
* User-related validation

### Database

H2 initially.

Later:

```text
H2 → MySQL → Docker → Cloud
```

---

# 3.2 Shipment Service

Responsible for shipment lifecycle management.

### APIs / Responsibilities

```text
Create shipment
Get shipment
Update shipment
Cancel shipment
Change shipment status
```

### Example Shipment Status

```text
CREATED
PICKED_UP
IN_TRANSIT
AT_HUB
OUT_FOR_DELIVERY
DELIVERED
CANCELLED
```

### Database

Shipment Service owns its own database.

```text
Shipment Service → Shipment DB
```

It must not directly access another microservice's database.

---

# 3.3 Tracking Service

Responsible for shipment tracking.

### Responsibilities

```text
Track shipment
Store tracking events
Get shipment history
```

Example:

```text
Tracking Number: PF10001

CREATED
    ↓
PICKED_UP
    ↓
IN_TRANSIT
    ↓
AT_HUB
    ↓
OUT_FOR_DELIVERY
    ↓
DELIVERED
```

Tracking events will be received through asynchronous events where appropriate.

---

# 3.4 Notification Service

Responsible for sending notifications.

### Responsibilities

```text
Consume ShipmentStatusChangedEvent
Send notification
```

Example:

```text
Shipment Delivered
        ↓
Kafka
        ↓
Notification Service
        ↓
Email / SMS notification
```

The notification service will not need to be directly called synchronously for every shipment status change.

---

# 3.5 Audit Service

Responsible for maintaining an audit trail.

### Responsibilities

```text
Consume important events
Store audit history
```

Example:

```text
ShipmentCreated
ShipmentStatusChanged
ShipmentCancelled
UserCreated
```

These events can be consumed from Kafka.

---

# 4. Topics / Technologies

The project will cover the following topics.

## Microservices Fundamentals

* What are microservices?
* Monolith vs Microservices
* Advantages
* Disadvantages
* Service boundaries
* Bounded context
* Database per service
* Independent deployment
* Scalability
* Fault isolation
* Distributed systems concepts

---

## Spring Boot

* Spring Boot project structure
* Maven
* Dependency Injection
* IoC
* Configuration
* Profiles
* `@Component`
* `@Service`
* `@Repository`
* `@RestController`
* Configuration classes
* Application properties
* Application YAML

---

## REST APIs

* REST principles
* HTTP methods
* GET
* POST
* PUT
* PATCH
* DELETE
* HTTP status codes
* Request parameters
* Path variables
* Request body
* Response body
* Headers
* DTOs
* API versioning
* Pagination
* Sorting

---

## Database

Initially:

```text
H2 Database
```

Later:

```text
H2
 ↓
MySQL
 ↓
Docker
 ↓
Cloud Database
```

Topics:

* JPA
* Hibernate
* Entity
* Repository
* Relationships
* JPQL
* Native SQL
* Transactions
* Lazy loading
* Eager loading
* N+1 problem
* Optimistic locking
* Indexing
* Connection pooling
* HikariCP

---

# 5. Service Registry

We will implement service discovery.

Example:

```text
                  Eureka Server
                       |
        +--------------+--------------+
        |              |              |
        v              v              v
   User Service   Shipment Service  Tracking Service
```

Topics:

* Service registration
* Service discovery
* Eureka
* Logical service names
* Load balancing
* Why service discovery is required

---

# 6. API Gateway

Architecture:

```text
Client
  |
  v
API Gateway
  |
  +---- /users/** ------> User Service
  |
  +---- /shipments/** --> Shipment Service
  |
  +---- /tracking/** ---> Tracking Service
```

Topics:

* Spring Cloud Gateway
* Routing
* Gateway filters
* Authentication
* Authorization
* CORS
* Request logging
* Rate limiting
* Error handling

---

# 7. Configuration Server

We will externalize configuration.

Topics:

* Spring Cloud Config
* Config Server
* Config Client
* Environment-specific configuration
* Profiles
* External configuration
* Environment variables
* Secrets

Example:

```text
application.yml
        ↓
Config Server
        ↓
Microservices
```

---

# 8. Authentication & Security

We will implement application security.

Architecture:

```text
Client
  |
  | username/password
  v
Authentication
  |
  v
JWT
  |
  v
Client
  |
  | Bearer Token
  v
API Gateway
  |
  v
Microservices
```

Topics:

* Spring Security
* Authentication
* Authorization
* JWT
* Access token
* Refresh token
* Password hashing
* Roles
* Authorities
* Security filters
* Method security
* OAuth2 concepts
* Keycloak
* Service-to-service security

---

# 9. Synchronous Communication

Microservices will communicate with each other where synchronous communication is appropriate.

Example:

```text
Shipment Service
       |
       | REST
       v
User Service
```

Topics:

* RestClient
* OpenFeign
* Request/response communication
* Timeouts
* Error handling
* Service discovery integration
* Service-to-service authentication

---

# 10. Apache Kafka

Kafka will be a major part of this project.

Example:

```text
Shipment Service
       |
       | ShipmentStatusChangedEvent
       v
     Kafka
       |
       +------------+-------------+
       |            |             |
       v            v             v
 Tracking      Notification      Audit
 Service         Service        Service
```

Topics:

* Kafka fundamentals
* Producer
* Consumer
* Topic
* Partition
* Offset
* Consumer group
* Kafka key
* Serialization
* JSON events
* Event schema
* Event-driven architecture
* Asynchronous communication
* Retry
* Dead Letter Topic
* Idempotency
* Ordering
* Consumer scaling
* Error handling
* Kafka monitoring

---

# 11. Event-Driven Architecture

We will learn the difference between:

```text
Synchronous
     ↓
Request → Response
```

and:

```text
Asynchronous
     ↓
Event → Kafka → Consumers
```

We will understand:

* Events
* Commands
* Event producers
* Event consumers
* Eventual consistency
* Loose coupling
* Event contracts
* Event versioning

---

# 12. Distributed Transactions

We will understand why a normal:

```java
@Transactional
```

does not automatically solve distributed transactions.

Topics:

* Local transactions
* Distributed transactions
* Eventual consistency
* Saga pattern
* Saga choreography
* Saga orchestration
* Compensation
* Failure recovery

Example:

```text
Shipment Created
       ↓
Payment
       ↓
Confirmation
```

If something fails:

```text
Operation Failed
       ↓
Compensation
       ↓
Previous operation reversed
```

---

# 13. Resilience4j

We will intentionally simulate service failures.

Topics:

* Timeout
* Retry
* Circuit Breaker
* Fallback
* Rate Limiter
* Bulkhead
* Failure handling

Example:

```text
Shipment Service
       |
       v
Tracking Service
       X
     DOWN

       ↓

Circuit Breaker
       ↓
Fallback / Error Response
```

---

# 14. Redis

Redis will be introduced where it provides real value.

Topics:

* Caching
* Cache TTL
* Cache invalidation
* Spring Cache
* Redis
* Distributed cache
* Rate limiting
* Idempotency use cases

---

# 15. Validation

Topics:

* Bean Validation
* `@NotNull`
* `@NotBlank`
* `@Size`
* `@Pattern`
* Custom validation
* Validation error responses

---

# 16. Exception Handling

We will implement centralized exception handling.

Example:

```json
{
  "timestamp": "2026-09-11T20:00:00",
  "status": 404,
  "code": "SHIPMENT_NOT_FOUND",
  "message": "Shipment not found",
  "path": "/shipments/100"
}
```

Topics:

* Custom exceptions
* `@RestControllerAdvice`
* `@ExceptionHandler`
* Standard error response
* Validation errors
* HTTP error handling

---

# 17. Logging

Topics:

* SLF4J
* Logback
* Log levels
* Structured logging
* Request logging
* Correlation ID
* Trace ID
* Error logging

---

# 18. Testing

We will write tests for every important service.

### Unit Testing

```text
JUnit 5
Mockito
```

### Integration Testing

```text
Spring Boot Test
Testcontainers
```

### API Testing

```text
Postman
```

### Kafka Testing

Producer and consumer integration testing.

Topics:

* Unit tests
* Integration tests
* Repository tests
* Controller tests
* Service tests
* Testcontainers
* Kafka testing
* Test strategy

---

# 19. API Documentation

We will implement:

```text
Swagger / OpenAPI
```

Topics:

* API documentation
* Request models
* Response models
* API testing
* API contracts

---

# 20. Spring Boot Actuator

Topics:

* Actuator
* Health endpoint
* Metrics
* Application information
* Liveness
* Readiness
* Custom health indicators

Example:

```text
/actuator/health
/actuator/metrics
```

---

# 21. Monitoring

Architecture:

```text
Microservices
      |
      v
 Prometheus
      |
      v
   Grafana
```

Topics:

* Metrics
* Prometheus
* Grafana
* JVM metrics
* HTTP metrics
* Custom metrics
* Dashboards
* Alerts

---

# 22. Distributed Tracing

We will trace requests across multiple services.

Example:

```text
Client
  ↓
Gateway
  ↓
Shipment Service
  ↓
Tracking Service
  ↓
Kafka
  ↓
Notification Service
```

Topics:

* Distributed tracing
* Trace ID
* Span
* Correlation
* OpenTelemetry
* Troubleshooting distributed requests

---

# 23. Centralized Logging

Architecture:

```text
Microservices
      |
      v
Centralized Log System
      |
      v
Grafana / Kibana
```

Topics:

* Centralized logging
* Log aggregation
* Searching logs
* Correlation ID
* Error analysis

Possible technologies:

```text
Loki + Grafana
```

or:

```text
ELK
```

---

# 24. Docker

Every microservice will be containerized.

```text
Docker
 ├── API Gateway
 ├── Service Registry
 ├── Config Server
 ├── User Service
 ├── Shipment Service
 ├── Tracking Service
 ├── Notification Service
 ├── Audit Service
 ├── Kafka
 ├── Redis
 └── Databases
```

Topics:

* Dockerfile
* Image
* Container
* Docker network
* Volumes
* Environment variables
* Health checks
* Multi-stage builds

---

# 25. Docker Compose

We will run the complete local environment using Docker Compose.

Topics:

* `docker-compose.yml`
* Services
* Networks
* Volumes
* Dependencies
* Environment variables
* Health checks
* Service communication

---

# 26. Kubernetes

After Docker, we will deploy the application to Kubernetes.

Topics:

* Kubernetes
* Cluster
* Node
* Pod
* Deployment
* Service
* ConfigMap
* Secret
* Namespace
* Ingress
* Replica
* Scaling
* Load balancing
* Liveness probe
* Readiness probe
* Rolling deployment

---

# 27. Kubernetes Scaling

Example:

```text
Shipment Service

        |
        +---- Pod 1
        |
        +---- Pod 2
        |
        +---- Pod 3
```

Topics:

* Horizontal scaling
* Replicas
* Horizontal Pod Autoscaler
* Resource requests
* Resource limits
* Load balancing

---

# 28. CI/CD

Pipeline:

```text
Developer
    |
    v
Git Push
    |
    v
Build
    |
    v
Unit Tests
    |
    v
Integration Tests
    |
    v
Code Quality
    |
    v
Docker Build
    |
    v
Docker Registry
    |
    v
Kubernetes Deployment
```

Topics:

* CI/CD
* Git
* GitHub Actions / Jenkins
* Build pipeline
* Automated testing
* Docker image creation
* Image registry
* Deployment
* Rollback

---

# 29. AWS Deployment

Finally, we will deploy the application to AWS.

Topics:

* AWS basics
* EC2
* RDS
* EKS
* Load Balancer
* IAM
* VPC basics
* Security Groups
* CloudWatch
* Secrets
* Container deployment
* Production architecture

---

# 30. Production-Ready Concepts

We will also cover concepts frequently required in real projects and interviews.

* Idempotency
* API versioning
* Backward compatibility
* Graceful shutdown
* Database migration
* Flyway / Liquibase
* Zero-downtime deployment
* Rolling deployment
* Blue/Green deployment
* Canary deployment
* Rate limiting
* Fault tolerance
* High availability
* Scalability
* Disaster recovery concepts
* Secret management
* Security best practices

---

# 31. Development Approach

We will follow this pattern for every topic:

```text
1. Understand the concept
        ↓
2. Understand why we need it
        ↓
3. Understand the architecture
        ↓
4. Write the code
        ↓
5. Run the application
        ↓
6. Test it
        ↓
7. Introduce a failure/problem
        ↓
8. Fix the problem
        ↓
9. Understand production considerations
        ↓
10. Interview questions
```

---

# 32. Learning Order

The project will be implemented in the following order:

```text
01. Project Setup
02. User Service
03. Shipment Service
04. Tracking Service
05. H2 + JPA
06. REST APIs
07. DTOs
08. Validation
09. Exception Handling
10. Logging
11. Service-to-Service Communication
12. Service Registry
13. API Gateway
14. Config Server
15. Spring Security
16. JWT
17. Kafka Producer
18. Kafka Consumer
19. Event-Driven Architecture
20. Retry + DLT
21. Idempotency
22. Saga / Distributed Transactions
23. Resilience4j
24. Redis
25. Testing
26. Testcontainers
27. Swagger / OpenAPI
28. Actuator
29. Prometheus
30. Grafana
31. Distributed Tracing
32. Centralized Logging
33. Docker
34. Docker Compose
35. Kubernetes
36. Kubernetes Scaling
37. CI/CD
38. AWS
39. Production Architecture
40. Final End-to-End Testing
```

---

# 33. Initial Technology Stack

| Area                | Technology               |
| ------------------- | ------------------------ |
| Language            | Java                     |
| Framework           | Spring Boot              |
| Build               | Maven                    |
| Database            | H2 initially             |
| ORM                 | Hibernate / JPA          |
| API                 | REST                     |
| Service Discovery   | Eureka                   |
| Gateway             | Spring Cloud Gateway     |
| Security            | Spring Security + JWT    |
| Messaging           | Apache Kafka             |
| Cache               | Redis                    |
| Resilience          | Resilience4j             |
| Testing             | JUnit 5 + Mockito        |
| Integration Testing | Testcontainers           |
| API Documentation   | OpenAPI / Swagger        |
| Health              | Spring Boot Actuator     |
| Metrics             | Prometheus               |
| Dashboard           | Grafana                  |
| Tracing             | OpenTelemetry            |
| Logging             | Loki / Grafana or ELK    |
| Containerization    | Docker                   |
| Local orchestration | Docker Compose           |
| Orchestration       | Kubernetes               |
| CI/CD               | GitHub Actions / Jenkins |
| Cloud               | AWS                      |

---

# 34. Initial Project Structure

```text
MicroServiceConcepts/
│
├── api-gateway/
│
├── service-registry/
│
├── config-server/
│
├── auth-service/
│
├── user-service/
│
├── shipment-service/
│
├── tracking-service/
│
├── notification-service/
│
├── audit-service/
│
├── docker/
│
├── kubernetes/
│
├── .github/
│   └── workflows/
│
├── docs/
│
└── README.md
```

---

# 35. Important Rule

This project will be built as **real microservices from the beginning**.

We will NOT:

```text
Build Monolith
      ↓
Split Monolith
```

Instead:

```text
Define Service Boundaries
        ↓
Create Independent Services
        ↓
Give Each Service Its Own Responsibility
        ↓
Give Each Service Its Own Database
        ↓
Implement Communication
        ↓
Add Infrastructure
```

---

# 36. Final Goal

At the end of the project, the complete system should look approximately like:

```text
                              CLIENT
                                |
                                v
                         +--------------+
                         | API GATEWAY  |
                         +------+-------+
                                |
                         Security / JWT
                                |
          +---------------------+----------------------+
          |                     |                      |
          v                     v                      v
    USER SERVICE        SHIPMENT SERVICE        TRACKING SERVICE
          |                     |                      |
        H2 DB                 H2 DB                  H2 DB
                                |
                                v
                              KAFKA
                                |
                +---------------+----------------+
                |               |                |
                v               v                v
          NOTIFICATION       AUDIT          OTHER SERVICES
             SERVICE         SERVICE
                |
                v
             Email/SMS


Infrastructure:

     Config Server
     Service Registry
     Redis
     Prometheus
     Grafana
     OpenTelemetry
     Centralized Logging


Deployment:

     Docker
        ↓
     Docker Compose
        ↓
     Kubernetes
        ↓
     CI/CD
        ↓
     AWS
```

---

# 37. Definition of Done

The project will be considered complete when we can:

* Create a user
* Authenticate a user
* Generate JWT
* Create a shipment
* Update shipment status
* Track shipment
* Publish shipment events to Kafka
* Consume events
* Send notifications
* Store audit events
* Handle service failures
* Retry failed operations
* Use circuit breakers
* Handle duplicate events
* Monitor services
* View health status
* View metrics
* Trace requests across services
* Search centralized logs
* Run everything with Docker
* Run everything with Docker Compose
* Deploy to Kubernetes
* Scale services
* Run CI/CD
* Deploy to AWS
* Test the complete system end-to-end

---

# 38. Learning Philosophy

The objective is not to memorize annotations or configuration.

For every technology we should be able to answer:

```text
What is it?
Why do we need it?
What problem does it solve?
How does it work?
How do we implement it?
What happens when it fails?
When should we NOT use it?
How is it used in production?
What questions can be asked about it in an interview?
```

This project is intended to provide **hands-on end-to-end microservices experience using Java and Spring Boot**.
