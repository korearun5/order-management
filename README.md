# Order Management System
Microservices architecture implementing Domain-Driven Design

## Architecture
![System Architecture](docs/architecture.png)

## Services
1. API Gateway - Spring Cloud Gateway
2. Order Service - Core domain service
3. Inventory Service - Stock management
4. Auth Service - OAuth2/JWT authentication
5. Config Server - Centralized configuration
6. Eureka Server - Service discovery

## Getting Started
```bash
mvn clean install