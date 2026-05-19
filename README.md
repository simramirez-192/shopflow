# 🛒 ShopFlow — Plataforma de Marketplace Online

## Arquitectura de Microservicios | DSY1103 Desarrollo FullStack 1

---

## 👥 Integrantes del Equipo

| Nombre | Rol | Microservicios a cargo |
|--------|-----|------------------------|
| Simón Ramírez | Backend Developer | user-service, auth-service, product-service, inventory-service, cart-service |
| Jorge Delgado | Backend Developer | order-service, payment-service, shipping-service, notification-service, review-service |

---

## 📋 Descripción del Proyecto

**ShopFlow** es una plataforma de compras online desarrollada bajo una arquitectura de microservicios independientes. Cada microservicio gestiona su propio dominio, base de datos y lógica de negocio, comunicándose entre sí a través de REST APIs usando WebClient.

El sistema permite a los usuarios registrarse, autenticarse, explorar productos, gestionar su carrito, realizar pedidos, procesar pagos, hacer seguimiento de envíos, recibir notificaciones y dejar reseñas de productos.

---

## 🧩 Microservicios Implementados (10)

| # | Microservicio | Puerto | Base de Datos | Descripción |
|---|--------------|--------|---------------|-------------|
| 1 | **user-service** | 8081 | shopflow_users | Gestión de usuarios — CRUD completo con validaciones |
| 2 | **auth-service** | 8082 | shopflow_auth | Autenticación y tokens de sesión, se comunica con user-service |
| 3 | **product-service** | 8083 | shopflow_products | Catálogo de productos con búsqueda y filtros |
| 4 | **inventory-service** | 8084 | shopflow_inventory | Control de stock por producto |
| 5 | **cart-service** | 8085 | shopflow_cart | Carrito de compras por usuario |
| 6 | **order-service** | 8086 | shopflow_orders | Pedidos con ítems y cálculo de totales |
| 7 | **payment-service** | 8087 | shopflow_payment | Procesamiento de pagos por pedido |
| 8 | **shipping-service** | 8088 | shopflow_shipping | Envíos y tracking de despachos |
| 9 | **notification-service** | 8089 | shopflow_notification | Notificaciones por usuario |
| 10 | **review-service** | 8090 | shopflow_review | Reseñas y calificaciones de productos |

---

## 🏗️ Arquitectura y Patrón de Diseño

Cada microservicio sigue estrictamente el patrón **Controller → Service → Repository (CSR)**:

```
[nombre]-service/
└── src/main/java/com/shopflow/[nombre]service/
    ├── controller/     → Recibe requests REST, retorna ResponseEntity
    ├── service/        → Lógica de negocio, validaciones, reglas del dominio
    ├── repository/     → Acceso a BD mediante JpaRepository
    ├── model/          → Entidades JPA (@Entity, relaciones)
    ├── dto/            → CreateDTO + ResponseDTO
    └── exception/      → Excepciones propias + @ControllerAdvice global
```

---

## ⚙️ Tecnologías Utilizadas

| Tecnología | Versión | Uso |
|-----------|---------|-----|
| Java | 17 | Lenguaje principal |
| Spring Boot | 3.x | Framework base |
| Spring Data JPA | 3.x | Persistencia con Hibernate |
| MySQL | 8.x | Base de datos (Laragon) |
| WebClient | 3.x | Comunicación entre microservicios |
| Bean Validation | JSR 380 | Validaciones en DTOs |
| SLF4J | 2.x | Logs estructurados |
| Maven | 3.x | Gestión de dependencias |

---

## ✅ Funcionalidades Implementadas

- **Persistencia real** con JPA + Hibernate: entidades `@Entity`, `@Id`, `@GeneratedValue`, `@OneToMany`, `@ManyToOne`
- **CRUD completo** en los 10 microservicios con endpoints REST funcionales
- **Validaciones** con Bean Validation (`@NotNull`, `@NotBlank`, `@Size`, `@Email`, etc.) en todos los DTOs
- **Manejo de excepciones** centralizado con `@ControllerAdvice` y `@ExceptionHandler`
- **Respuestas controladas** con `ResponseEntity` y códigos HTTP adecuados (200, 201, 400, 404, 500)
- **Logs estructurados** con SLF4J en puntos clave del flujo (creación, actualización, errores)
- **Comunicación entre microservicios** usando WebClient (auth-service → user-service)
- **Base de datos separada** por cada microservicio, garantizando independencia total
- **DTOs separados** de las entidades para entrada y salida de datos

---

## 🚀 Pasos para Ejecutar el Proyecto

### Requisitos previos
- Java 17 instalado
- Maven instalado
- Laragon con MySQL activo
- IntelliJ IDEA o VS Code

### Paso 1 — Crear las bases de datos

Abre HeidiSQL en Laragon y ejecuta el archivo `setup-databases.sql` incluido en el proyecto:

```bash
mysql -u root < setup-databases.sql
```

Esto crea las 10 bases de datos necesarias:
```
shopflow_users, shopflow_auth, shopflow_products, shopflow_inventory,
shopflow_cart, shopflow_orders, shopflow_payment, shopflow_shipping,
shopflow_notification, shopflow_review
```

### Paso 2 — Iniciar los microservicios

**Opción A — Script automático (Windows):**
```
Doble clic en start-all.bat
```

**Opción B — Manual (uno por uno):**
```bash
cd user-service && mvnw spring-boot:run
cd auth-service && mvnw spring-boot:run
cd product-service && mvnw spring-boot:run
cd inventory-service && mvnw spring-boot:run
cd cart-service && mvnw spring-boot:run
cd order-service && mvnw spring-boot:run
cd payment-service && mvnw spring-boot:run
cd shipping-service && mvnw spring-boot:run
cd notification-service && mvnw spring-boot:run
cd review-service && mvnw spring-boot:run
```

> Spring Boot creará las tablas automáticamente gracias a `spring.jpa.hibernate.ddl-auto=update`

### Paso 3 — Verificar que están corriendo

| Servicio | URL |
|---------|-----|
| user-service | http://localhost:8081/api/users |
| auth-service | http://localhost:8082/api/auth |
| product-service | http://localhost:8083/api/products |
| inventory-service | http://localhost:8084/api/inventory |
| cart-service | http://localhost:8085/api/cart |
| order-service | http://localhost:8086/api/orders |
| payment-service | http://localhost:8087/api/payments |
| shipping-service | http://localhost:8088/api/shipments |
| notification-service | http://localhost:8089/api/notifications |
| review-service | http://localhost:8090/api/reviews |

---

## 🗄️ Configuración de Base de Datos

Cada `application.properties` usa variables de entorno con fallback automático a Laragon:

```properties
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/shopflow_users?useSSL=false&serverTimezone=UTC}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:}
```

No se requiere configuración adicional si se usa Laragon con valores por defecto (usuario `root`, sin contraseña).

---

## 📁 Estructura del Repositorio

```
shopflow/
├── user-service/
├── auth-service/
├── product-service/
├── inventory-service/
├── cart-service/
├── order-service/
├── payment-service/
├── shipping-service/
├── notification-service/
├── review-service/
├── setup-databases.sql     ← Script para crear las BDs en Laragon
├── start-all.bat           ← Script para iniciar todos los servicios
├── .gitignore
└── README.md
```

---

## 🔗 Repositorio GitHub

[https://github.com/simramirez-192/shopflow](https://github.com/simramirez-192/shopflow)
