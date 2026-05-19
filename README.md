# 🛒 ShopFlow - Sistema de Pedidos Online

## Arquitectura de Microservicios | DSY1103 Desarrollo FullStack 1

---

## 👥 Integrantes del Equipo

| Nombre | Rol |
|--------|-----|
| [Nombre 1] | [Rol] |
| [Nombre 2] | [Rol] |
| [Nombre 3] | [Rol] |

---

## 📋 Descripción del Proyecto

**ShopFlow** es una plataforma de compras online desarrollada bajo una arquitectura de microservicios independientes. Cada microservicio gestiona su propio dominio, base de datos y lógica de negocio, comunicándose entre sí a través de REST APIs usando WebClient.

El sistema permite a los usuarios registrarse, autenticarse, explorar productos, gestionar su carrito, realizar pedidos, procesar pagos, hacer seguimiento de envíos, recibir notificaciones y dejar reseñas.

---

## 🧩 Microservicios Implementados (10)

| # | Microservicio | Puerto | Base de Datos | Descripción |
|---|--------------|--------|---------------|-------------|
| 1 | **user-service** | 8081 | shopflow_users | Gestión de usuarios (CRUD completo) |
| 2 | **auth-service** | 8082 | shopflow_auth | Autenticación y tokens de sesión |
| 3 | **product-service** | 8083 | shopflow_products | Catálogo de productos |
| 4 | **inventory-service** | 8084 | shopflow_inventory | Control de stock por producto |
| 5 | **cart-service** | 8085 | shopflow_cart | Carrito de compras por usuario |
| 6 | **order-service** | 8086 | shopflow_orders | Pedidos con ítems y cálculo de totales |
| 7 | **payment-service** | 8087 | shopflow_payment | Procesamiento de pagos |
| 8 | **shipping-service** | 8088 | shopflow_shipping | Envíos y tracking |
| 9 | **notification-service** | 8089 | shopflow_notification | Notificaciones por usuario |
| 10 | **review-service** | 8090 | shopflow_review | Reseñas y calificaciones de productos |

---

## 🏗️ Estructura de cada Microservicio (Patrón CSR)

Cada microservicio sigue estrictamente el patrón **Controller → Service → Repository**, con separación real de responsabilidades:

```
[nombre]-service/
└── src/main/java/com/shopflow/[nombre]service/
    ├── controller/       → Recibe requests REST, retorna ResponseEntity
    ├── service/          → Lógica de negocio, validaciones, reglas del dominio
    ├── repository/       → Acceso a BD mediante JpaRepository
    ├── model/            → Entidades JPA (@Entity, relaciones, etc.)
    ├── dto/              → CreateDTO + ResponseDTO (separación entidad/entrada)
    └── exception/        → Excepciones propias + @ControllerAdvice global
```

---

## ⚙️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA + Hibernate** — persistencia real con MySQL
- **Bean Validation (JSR 380)** — validaciones con anotaciones
- **WebClient (Spring WebFlux)** — comunicación entre microservicios
- **SLF4J** — logs estructurados en capas
- **MySQL** — base de datos relacional (una por microservicio)
- **Maven** — gestión de dependencias

---

## 🚀 Pasos para Ejecutar

### Prerrequisitos

- Java 17 instalado
- MySQL corriendo en `localhost:3306`
- IntelliJ IDEA o VS Code con extensión Java
- Maven 3.8+

### 1. Crear bases de datos en MySQL

```sql
CREATE DATABASE shopflow_users;
CREATE DATABASE shopflow_auth;
CREATE DATABASE shopflow_products;
CREATE DATABASE shopflow_inventory;
CREATE DATABASE shopflow_cart;
CREATE DATABASE shopflow_orders;
CREATE DATABASE shopflow_payment;
CREATE DATABASE shopflow_shipping;
CREATE DATABASE shopflow_notification;
CREATE DATABASE shopflow_review;
```

### 2. Configurar credenciales (si es necesario)

En cada `application.properties` ajusta usuario/contraseña:

```properties
spring.datasource.username=root
spring.datasource.password=root
```

### 3. Levantar cada microservicio

Desde la carpeta de cada servicio, ejecutar:

```bash
cd user-service
mvn spring-boot:run
```

Repetir para cada microservicio. Se recomienda este orden:

```
1. user-service      (puerto 8081)
2. auth-service      (puerto 8082)
3. product-service   (puerto 8083)
4. inventory-service (puerto 8084)
5. cart-service      (puerto 8085)
6. order-service     (puerto 8086)
7. payment-service   (puerto 8087)
8. shipping-service  (puerto 8088)
9. notification-service (puerto 8089)
10. review-service   (puerto 8090)
```

---

## 🔌 Endpoints principales por microservicio

### User Service (`localhost:8081`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/users` | Crear usuario |
| GET | `/api/users` | Listar usuarios |
| GET | `/api/users/{id}` | Obtener usuario |
| PUT | `/api/users/{id}` | Actualizar usuario |
| DELETE | `/api/users/{id}` | Eliminar usuario |

### Auth Service (`localhost:8082`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/auth/login` | Iniciar sesión |
| POST | `/api/auth/logout` | Cerrar sesión |
| GET | `/api/auth/validate` | Validar token |

### Product Service (`localhost:8083`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/products` | Crear producto |
| GET | `/api/products` | Listar activos |
| GET | `/api/products/{id}` | Obtener producto |
| GET | `/api/products/category/{cat}` | Por categoría |
| PUT | `/api/products/{id}` | Actualizar |
| DELETE | `/api/products/{id}` | Desactivar |

### Inventory Service (`localhost:8084`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/inventory` | Crear inventario |
| GET | `/api/inventory/product/{id}` | Ver stock |
| PATCH | `/api/inventory/product/{id}/reduce` | Reducir stock |
| PATCH | `/api/inventory/product/{id}/add` | Agregar stock |

### Cart Service (`localhost:8085`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/cart` | Agregar al carrito |
| GET | `/api/cart/user/{userId}` | Ver carrito |
| GET | `/api/cart/user/{userId}/total` | Total del carrito |
| DELETE | `/api/cart/item/{itemId}` | Eliminar ítem |
| DELETE | `/api/cart/user/{userId}` | Vaciar carrito |

### Order Service (`localhost:8086`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/orders` | Crear pedido |
| GET | `/api/orders/{id}` | Ver pedido |
| GET | `/api/orders/user/{userId}` | Pedidos del usuario |
| PATCH | `/api/orders/{id}/status` | Cambiar estado |
| DELETE | `/api/orders/{id}` | Cancelar pedido |

### Payment Service (`localhost:8087`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/payments` | Procesar pago |
| GET | `/api/payments/order/{orderId}` | Pago de pedido |
| GET | `/api/payments/user/{userId}` | Pagos del usuario |

### Shipping Service (`localhost:8088`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/shipping` | Crear envío |
| GET | `/api/shipping/track/{code}` | Tracking |
| PATCH | `/api/shipping/{id}/status` | Actualizar estado |

### Notification Service (`localhost:8089`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/notifications` | Enviar notificación |
| GET | `/api/notifications/user/{userId}` | Ver todas |
| GET | `/api/notifications/user/{userId}/unread` | Sin leer |
| PATCH | `/api/notifications/{id}/read` | Marcar leída |

### Review Service (`localhost:8090`)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/reviews` | Crear reseña |
| GET | `/api/reviews/product/{productId}` | Reseñas de producto |
| GET | `/api/reviews/product/{productId}/stats` | Estadísticas |
| GET | `/api/reviews/user/{userId}` | Reseñas del usuario |

---

## 🔗 Comunicación entre Microservicios

- **auth-service** → consulta **user-service** vía `WebClient` para validar credenciales en el login
- Los servicios se comunican mediante HTTP REST con manejo de errores y timeouts

---

## ✅ Funcionalidades Implementadas

- [x] Patrón CSR completo en los 10 microservicios
- [x] Persistencia real con JPA + Hibernate
- [x] Validaciones con Bean Validation (JSR 380) en todos los DTOs
- [x] Manejo centralizado de excepciones con `@ControllerAdvice`
- [x] Respuestas con `ResponseEntity` y códigos HTTP correctos
- [x] Logs con SLF4J en controller, service y excepciones
- [x] Separación DTO / Entidad en todos los microservicios
- [x] Comunicación entre microservicios (auth → user) vía WebClient
- [x] Reglas de negocio por dominio (stock, unicidad de reseñas, estados de pedido, etc.)
- [x] Relaciones JPA (`@OneToMany`, `@ManyToOne`) en Order/OrderItem

---

## 📁 Estructura del Repositorio

```
shopflow/
├── README.md
├── user-service/
├── auth-service/
├── product-service/
├── inventory-service/
├── cart-service/
├── order-service/
├── payment-service/
├── shipping-service/
├── notification-service/
└── review-service/
```

---

## 📝 Notas técnicas

- Cada microservicio tiene su propia base de datos MySQL (aislamiento real)
- `spring.jpa.hibernate.ddl-auto=update` crea las tablas automáticamente al iniciar
- Los logs se configuran con `logging.level.com.shopflow=DEBUG` en cada `application.properties`
- Para pruebas REST se recomienda usar **Postman** o **Thunder Client** (VS Code)
