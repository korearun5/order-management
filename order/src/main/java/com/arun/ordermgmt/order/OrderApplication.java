package com.arun.ordermgmt.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

}



// Project: Order Management System (Event-Driven, DDD, Orchestrated)
// Tech Stack: Spring Boot, Spring Cloud Gateway, Kafka, MySQL, Zeebe (Camunda 8), Docker, Prometheus, Grafana, OpenTelemetry, Apache Spark

// ------------------- USE CASE -------------------
// Simple use case: Customer places an order
// Flow:
// 1. Client sends HTTP POST to /orders
// 2. API Gateway routes to Order Service
// 3. Order Service creates order (MySQL, Outbox)
// 4. Outbox Publisher publishes event to Kafka
// 5. Inventory/Billing/Shipping services consume the event
// 6. Zeebe orchestrates workflow (can include compensation)
// 7. Apache Spark processes the stream for analytics
// 8. Metrics and traces are pushed to observability tools