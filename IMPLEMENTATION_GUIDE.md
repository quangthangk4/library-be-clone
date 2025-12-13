# Library Management System - Implementation Guide

## Tổng Quan
Hệ thống quản lý thư viện được xây dựng theo Clean Architecture + Domain-Driven Design (DDD) với kiến trúc microservices.

## Bounded Contexts

### 1. User Context (library-user-module) ✅ IN PROGRESS
**Chức năng**: Quản lý người dùng, vai trò và quyền hạn

**Đã hoàn thành**:
- ✅ Domain Layer (100%)
  - UserAggregate, RoleAggregate, Permission entities
  - Value Objects: UserId, RoleId, PermissionId, Email, UserProfile
  - UserStatus enum
  - Domain Services: UserDomainService
  - Specifications: CanBorrowBooksSpecification, IsActiveUserSpecification
  - Domain Events: UserCreatedEvent, UserStatusChangedEvent
  - Repository Interfaces: UserRepositoryInterface, RoleRepository, PermissionRepository

- ✅ Application Layer (100%)
  - DTOs: CreateUserRequest, UpdateUserProfileRequest, ChangePasswordRequest, UserResponse, RoleResponse, PermissionResponse
  - Use Cases:
    - CreateUserUseCase
    - GetUserByIdUseCase
    - UpdateUserProfileUseCase
    - AssignRoleToUserUseCase
  - Mappers: UserMapper (MapStruct)

- ✅ Infrastructure Layer (70%)
  - JPA Entities: UserJpaEntity, RoleJpaEntity, PermissionJpaEntity
  - Spring Data JPA Repositories: UserJpaRepository, RoleJpaRepository, PermissionJpaRepository

**Cần hoàn thành**:
- Infrastructure Layer:
  - EntityMapper (convert JPA entities ↔ Domain models)
  - Repository Implementations (UserRepositoryImpl, RoleRepositoryImpl, PermissionRepositoryImpl)

- API Layer:
  - REST Controllers
  - Request/Response mapping
  - Exception handling

### 2. Catalog Context (library-catalog-module) ⏳ PENDING
**Chức năng**: Quản lý ấn phẩm, tác giả, nhà xuất bản, thể loại, tags

**Entities cần implement**:
- Publication (Aggregate Root)
- Item
- Author
- Publisher
- Category
- Tag

**Junction Tables**:
- PublicationAuthor (N:M)
- PublicationCategory (N:M)
- PublicationTag (N:M)

### 3. Circulation Context (library-circulation-module) ⏳ PENDING
**Chức năng**: Quản lý mượn/trả sách, đặt trước, phạt

**Entities cần implement**:
- BorrowingTransaction (Aggregate Root)
- Reservation
- Fine

**Business Rules**:
- BR-01: Mỗi độc giả chỉ mượn tối đa 5 ấn phẩm
- BR-02: Thời gian mượn tiêu chuẩn: 14 ngày
- BR-03: Gia hạn tối đa 2 lần, mỗi lần 7 ngày
- BR-06: Phí phạt: 1,000 VNĐ/ngày/ấn phẩm

### 4. Recommendation Context (library-recommendation-module) ⏳ PENDING
**Chức năng**: AI recommendations, search history, user interactions

**Entities cần implement**:
- SearchHistory
- UserInteraction
- Rating
- Review

## Kiến Trúc Layered cho mỗi Module

### Domain Layer
```
domain/
├── model/              # Aggregates, Entities
├── valueobject/        # Value Objects
├── repository/         # Repository Interfaces
├── service/            # Domain Services
├── specification/      # Business Rules as Specifications
└── event/              # Domain Events
```

### Application Layer
```
application/
├── dto/
│   ├── request/        # Input DTOs
│   └── response/       # Output DTOs
├── usecase/            # Use Case Interfaces & Implementations
└── mapper/             # DTO ↔ Domain Model Mappers
```

### Infrastructure Layer
```
infrastructure/
├── persistence/
│   ├── entity/         # JPA Entities
│   └── repository/     # JPA Repositories & Implementations
└── mapper/             # JPA Entity ↔ Domain Model Mappers
```

### API Layer (Presentation)
```
api/
├── controller/         # REST Controllers
├── dto/                # API-specific DTOs (if needed)
└── exception/          # Exception Handlers
```

## Database Schema Summary

### Users & Authorization
- `users`: User accounts
- `roles`: Role definitions
- `permissions`: Permission definitions
- `user_roles`: User-Role junction (N:M)
- `role_permissions`: Role-Permission junction (N:M)

### Catalog
- `publications`: Publication metadata
- `items`: Physical/digital copies
- `authors`: Author information
- `publishers`: Publisher information
- `categories`: Hierarchical categories
- `tags`: Flexible tagging system
- `publication_authors`: Junction (N:M)
- `publication_categories`: Junction (N:M)
- `publication_tags`: Junction (N:M)

### Circulation
- `borrowing_transactions`: Borrowing records
- `reservations`: Book reservations
- `fines`: Late fees

### AI & Analytics
- `search_history`: Search queries and clicks
- `user_interactions`: Detailed user behavior
- `ratings`: User ratings (1-5 stars)
- `reviews`: User reviews

## Technologies

- **Java 21**
- **Spring Boot 3.3.5**
- **PostgreSQL** (database)
- **Spring Data JPA** (persistence)
- **MapStruct** (object mapping)
- **Lombok** (boilerplate reduction)
- **Hypersistence Utils** (TSID for primary keys)
- **Jakarta Validation** (input validation)
- **Spring Security** (authentication & authorization)

## Next Steps

### User Module
1. ✅ Complete Entity Mapper (JPA ↔ Domain)
2. ✅ Implement Repository Implementations
3. ✅ Create REST Controllers
4. Create integration tests

### Catalog Module
1. Design domain model
2. Implement all layers following User Module pattern
3. Create Flyway migrations

### Circulation Module
1. Design domain model with business rules
2. Implement all layers
3. Integration with User & Catalog modules

### Recommendation Module
1. Design event-driven architecture for data collection
2. Implement data collection endpoints
3. Prepare data structure for AI/ML integration

## Pattern Examples

### Creating a New Entity
1. Start with Domain Model (pure Java, business logic)
2. Create Value Objects for identity and complex types
3. Define Repository Interface in domain layer
4. Create JPA Entity in infrastructure layer
5. Implement Repository using Spring Data JPA
6. Create DTOs in application layer
7. Implement Use Cases
8. Create REST Controller

### Adding Business Logic
- Simple validation → Domain Model method
- Complex cross-aggregate logic → Domain Service
- Reusable business rules → Specification Pattern
- State changes → Domain Events

## Benefits of This Architecture

1. **Independence**: Domain logic không phụ thuộc framework
2. **Testability**: Dễ unit test business logic
3. **Flexibility**: Dễ thay đổi infrastructure (database, framework)
4. **Maintainability**: Code rõ ràng, dễ maintain
5. **Scalability**: Modules độc lập, có thể scale riêng
