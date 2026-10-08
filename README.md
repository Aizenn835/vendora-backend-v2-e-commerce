# Vendora

A multi-vendor e-commerce marketplace where buyers, sellers, and an admin share one platform. Sellers list their own products, a single cart can hold items from several sellers, and checkout splits the order into one sub-order per seller with a platform commission taken from each.

Built as a portfolio project with Spring Boot and React. All prices are in Philippine Pesos (₱).

> **Status:** In active development. Stage 1 (authentication and roles) is complete and Stage 2 (seller registration and admin approval) is in progress. See the [roadmap](#roadmap).

## Features

**Customer**
- Register, log in, and browse the product catalog
- Search, filter, and paginate products
- Cart that supports items from multiple sellers
- Checkout, order history, and wishlist

**Seller**
- Apply to become a seller and wait for admin approval
- Manage their own products
- View and ship their own sub-orders
- See earnings after commission

**Admin**
- Approve, reject, or suspend sellers
- View all sales and platform commissions

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java, Spring Boot, Spring Security, Spring Data JPA |
| Database | PostgreSQL |
| Auth | JWT (stateless), BCrypt password hashing |
| Frontend | React |
| Testing | JUnit 5, Mockito |
| Docs and deployment | Swagger (OpenAPI), Docker |

## How It Works

### Roles

| Role | How a user gets it |
|---|---|
| CUSTOMER | Automatically on public registration |
| SELLER | A customer applies, then an admin approves the application |
| ADMIN | Seeded at startup from environment variables, with no public way to become one |

The role is never accepted from the client. Registration always creates a `CUSTOMER`, and the role changes to `SELLER` only when an admin approves a seller application.

### Seller approval flow

1. A customer registers and logs in.
2. The customer submits a shop application, creating a `SellerProfile` with status `PENDING`.
3. An admin reviews the application.
4. On approval, the profile status becomes `APPROVED` and the user's role becomes `SELLER` in a single transaction.
5. The user logs in again to receive a token that carries the `SELLER` role.

### Orders and commission

Checkout creates one `Order` for the customer and one `SubOrder` for each seller in the cart, all inside one `@Transactional` method so either everything is saved or nothing is. Each sub-order has its own status and its own `Earning` record that splits the sub-order total into the seller's share and the platform commission.

Sub-order statuses: `PLACED`, `PREPARING`, `SHIPPED`, `DELIVERED`, `CANCELLED`

## Key Business Rules

- A seller can only edit or delete their own products.
- A seller can only view and update their own sub-orders.
- Prices are always calculated on the server and never trusted from the client.
- Commission is a percentage taken from each sub-order total.
- Seller-only actions check the seller's profile status as well as the token role, so a suspended seller is blocked even if their token has not expired.

## Core Entities

- `User` (with role)
- `SellerProfile` (shop name, status: `PENDING` / `APPROVED` / `SUSPENDED`)
- `Product` (belongs to one seller)
- `Cart` and `CartItem`
- `Order` (one per checkout, belongs to the customer)
- `SubOrder` (one per seller inside an order, with its own status)
- `SubOrderItem`
- `Earning` (seller share and platform commission per sub-order)

```
User  1 ---- 0..1  SellerProfile  1 ---- *  Product
```

## Architecture

The backend follows a layered architecture:

- **Controller** handles HTTP requests and responses
- **Service** holds business rules and transactions
- **Repository** handles database access through Spring Data JPA
- **DTOs** are scoped to a single purpose, so request and response models are never the entities themselves
- **GlobalExceptionHandler** maps exceptions to proper HTTP status codes (400, 401, 403, 404, 409)

## Getting Started

### Prerequisites

- Java 17 or newer
- Maven
- PostgreSQL
- Node.js (for the frontend, once added)

### Setup

1. Clone the repository.

```bash
git clone https://github.com/Aizenn835/vendora.git
cd vendora
```

2. Create a PostgreSQL database named `marketplace`.

3. Set these environment variables:

| Variable | Purpose |
|---|---|
| `DB_PASSWORD` | PostgreSQL password |
| `JWT_SECRET` | Secret used to sign tokens (use a long random string, at least 32 characters) |
| `ADMIN_EMAIL` | Email for the seeded admin account |
| `ADMIN_PASSWORD` | Password for the seeded admin account |

4. Run the backend.

```bash
./mvnw spring-boot:run
```

On first startup, the admin account is created automatically if it does not already exist.

## API Overview

Implemented so far:

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/auth/register` | Public | Create a customer account |
| POST | `/api/auth/login` | Public | Log in and receive a JWT |

Protected endpoints expect the header `Authorization: Bearer <token>`.

More endpoints will be documented here and in Swagger as each stage is completed.

## Roadmap

- [x] Stage 1: Auth and roles (register, login, protected endpoints)
- [ ] Stage 2: Seller registration and admin approval
- [ ] Stage 3: Product management with ownership checks
- [ ] Stage 4: Public catalog with search, filters, and pagination
- [ ] Stage 5: Cart supporting multiple sellers
- [ ] Stage 6: Checkout that splits into sub-orders
- [ ] Stage 7: Seller dashboard (orders, status updates, earnings)
- [ ] Stage 8: Admin dashboard (sales, commissions)
- [ ] Stage 9: Tests (JUnit 5 and Mockito), Docker, Swagger docs
- [ ] Stage 10: Deployment and final README polish

## Author

**Lei**, third-year BS Information Technology student, aiming for a Junior Java Developer or Associate Software Engineer role.

GitHub: [Aizenn835](https://github.com/Aizenn835)
