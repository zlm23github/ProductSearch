# MiniProductSearch

A high-performance **monolithic** product search application built with **Spring Boot**, **MySQL**, and **Elasticsearch**.  
Supports flexible multi-parameter search, full-text search, and efficient data synchronization between MySQL and Elasticsearch.

---

## Features

- **Full-text search** on product title and description (Elasticsearch)
- **Multi-parameter filtering**: keyword, category, price range
- **Pagination** and sorting support
- **Batch data synchronization** from MySQL to Elasticsearch
- **RESTful API** with Swagger documentation
- **Performance tested** with JMeter

---

## Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Data JPA (MySQL)
- Spring Data Elasticsearch
- MySQL 8.x
- Elasticsearch 8.x
- Swagger (springdoc-openapi)
- JMeter (for performance testing)
- Docker (for local MySQL/ES setup)

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/your-username/mini-product-search.git
cd mini-product-search
```

### 2. Start MySQL & Elasticsearch (recommended: Docker)

```bash
# Start MySQL
docker run --name mysql8 -e MYSQL_ROOT_PASSWORD=yourpassword -p 3307:3306 -d mysql:8.0

# Start Elasticsearch
docker run -d --name elasticsearch -p 9200:9200 -e "discovery.type=single-node" elasticsearch:8.11.1
```

### 3. Configure application properties

Edit `src/main/resources/application.yml` (or `.properties`) to match your MySQL and Elasticsearch settings.

### 4. Run the application

```bash
./mvnw spring-boot:run
```

### 5. Import mock data

- Use the provided Python script or CSV to generate/import product data into MySQL.
- Use the `/api/products/sync` endpoint to batch sync data from MySQL to Elasticsearch.

### 6. API Documentation

Visit [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) for interactive API docs.

---

## Example API Endpoints

- `GET /api/products/search-es`  
  Search products in Elasticsearch with parameters: `keyword`, `category`, `minPrice`, `maxPrice`, `page`, `size`.

- `POST /api/products/sync`  
  Trigger full data sync from MySQL to Elasticsearch.

---


