# LMS Backend — CLAUDE.md

Đây là đồ án tốt nghiệp: Hệ thống quản lý thư viện trực tuyến tích hợp AI (HCMUT).
**Luôn update file này sau mỗi lần làm xong tính năng mới hoặc quyết định kiến trúc quan trọng.**

---

## Tech Stack

- Java 21, Spring Boot 3.3.5, Maven multi-module
- PostgreSQL + Flyway (migrations ở `library-bootstrap/src/main/resources/db/migration/`)
- Kafka (bitnami/kafka:4.0.0, KRaft mode) — async email
- JWT (RS256, Nimbus JOSE) + UUID whitelist tokens
- Spring Security OAuth2 (Google)
- spring-dotenv:4.0.0 — đọc `.env` file
- `open-in-view: false` — bắt buộc `@Transactional` khi access lazy-loaded fields

---

## Module Structure

```
library-bootstrap/           — Entry point, application.yml, Flyway migrations
library-shared/              — Shared: EmailService, KafkaTopics, Kafka DTOs, exceptions, utils
library-auth-module/         — JWT, Login, OAuth2, VerifyEmail, ForgotPassword, ResetPassword
library-user-module/         — User domain, SignUp, UpdateProfile, ChangePassword, Kafka consumers
library-catalog-module/      — (chưa làm)
library-circulation-module/  — Dashboard librarian (dummy), GetAllTransactions, GetTransactionsByItem
library-recommendation-module/ — (chưa làm)
```

---

## Architecture Rules

### Clean Architecture (bắt buộc theo)
```
domain/ → application/ → infrastructure/ → presentation/
```
- `domain/` không được import bất kỳ Spring/JPA annotation nào
- `application/` chỉ dùng domain + port interfaces
- `infrastructure/` chứa JPA entities, Kafka publishers/consumers, impl các port
- `presentation/` chỉ là controller, gọi UseCases

### Naming Conventions
- **Interface KHÔNG có prefix "I"** — ví dụ: `PasswordHasher` (không phải `IPasswordHasher`)
- UseCase: `XxxUseCase` interface + `XxxUseCaseImpl` trong `impl/`
- Repository domain: `XxxRepository` interface trong `domain/repository/`, impl trong `infrastructure/persistence/repository/impl/`

### Domain Events Pattern
```java
// ĐÚNG: poll events từ object GỐC trước khi save
User user = User.registerUser(...);
List<Object> events = user.pollDomainEvents(); // poll TRƯỚC
userRepository.save(user);
events.forEach(event -> publisher.publish(event));

// SAI: poll từ object trả về của save (object mới, không có events)
User saved = userRepository.save(user);
saved.pollDomainEvents(); // luôn trả về empty!
```

### Tránh Circular Dependency giữa modules
- Dùng Port interface ở module cần, impl ở module kia
- Ví dụ: `VerificationTokenGenerator` port định nghĩa ở `library-user-module`, impl ở `library-auth-module`

### Kafka Pattern
- Producer gửi **plain DTO** (record), KHÔNG truyền domain Value Object
- Ví dụ: `UserRegisteredMessage(Long userId, ...)` thay vì `UserRegisteredEvent(UserId userId, ...)`
- Consumer cần `@Transactional` khi access lazy-loaded JPA fields

---

## Kafka Topics

| Topic | Class constant | Producer | Consumer |
|-------|---------------|----------|----------|
| `user.registered` | `KafkaTopics.USER_REGISTERED` | `UserEventKafkaPublisher` | `UserRegisteredEventConsumer` |
| `user.forgot-password` | `KafkaTopics.USER_FORGOT_PASSWORD` | `ForgotPasswordKafkaPublisher` | `ForgotPasswordEventConsumer` |

Kafka group-id: `library-group`

---

## Token System

| Purpose | Type | Expiry | Lưu ở đâu |
|---------|------|--------|-----------|
| ACCESS | JWT RS256 | 3600s (1h) | Client only |
| REFRESH | JWT RS256 | 604800s (7d) | DB: `refresh_tokens` |
| VERIFY_EMAIL | JWT RS256 | 86400s (24h) | Client only (one-time via link) |
| RESET_PASSWORD | UUID | 900s (15m) | DB: `password_reset_tokens` |

`PurposeToken` enum: `ACCESS, REFRESH, VERIFY_EMAIL, RESET_PASSWORD`

---

## API Endpoints (đã hoàn thành)

### Auth — `/api/v1/auth`

| Method | Path | Mô tả |
|--------|------|-------|
| POST | `/register` | Đăng ký tài khoản, gửi email verify qua Kafka |
| POST | `/login` | Đăng nhập username/password |
| POST | `/logout` | Header: `re-token` |
| GET | `/verify-email?token=` | Verify email, redirect FE |
| POST | `/forgot-password` | Body: `{email}`, gửi reset link qua Kafka |
| POST | `/reset-password` | Body: `{token, newPassword, confirmPassword}` |
| POST | `/refresh-accesstoken` | Header: `re-token` |
| GET | `/login-with-social` | Param: `loginType` |
| POST | `/social-callback/{registrationId}` | OAuth2 callback |
| POST | `/onboarding-profile` | Sau OAuth2 đăng nhập lần đầu |

### User — `/api/v1/users`

| Method | Path | Auth | Mô tả |
|--------|------|------|-------|
| GET | `/my-profile` | Required | Lấy profile hiện tại |
| PUT | `/my-profile` | Required | Cập nhật profile, trả về `UserResponse` |
| PUT | `/my-profile/change-password` | Required | Đổi mật khẩu (min 8 ký tự) |

### Librarian — `/api/v1/librarians` (LIBRARIAN role)

| Method | Path | Response | Trạng thái |
|--------|------|----------|-----------|
| GET | `/dashboard/summary` | `DashboardSummaryResponse` | dummy data — **đang làm** |
| GET | `/dashboard/charts?period=` | `DashboardChartsResponse` | dummy data |
| GET | `/dashboard/risky-users?page&size&sortBy&sortDir` | `PageResponse<RiskyUserResponse>` | dummy data |

`DashboardPeriod` enum: `WEEKLY, MONTHLY, SIX_MONTHS, YEARLY`

**DashboardSummaryResponse structure:**
```
Overview:       totalUsers, activeUsers, totalPublications, totalItems, availableItems
TodayActivity:  borrowedToday, returnedToday, damagedToday, overdueCount  ← overdueCount trùng với PendingActions, cần thảo luận
PendingActions: waitingForPickup, overdueTransactions, reservationsPending
FinesResponse:  totalUnpaid (count), totalUnpaidAmount, collectedToday
```

**DashboardChartsResponse structure:**
```
weeklyBorrowReturnTrend: List<TrendPoint(date, borrowed, returned)>
itemStatusDistribution:  available, borrowed, reserved, inMaintenance, lost
topBorrowedPublications: List<TopBorrowedPublication(publicationId, title, borrowCount, coverImageUrl)>
fineTypeDistribution:    overdueReturn, damagedBook, lostBook
```

**RiskyUserResponse:** userId, fullName, email, phoneNumber, profilePictureUrl, creditScore, RiskyMetrics(overdueCount, unpaidFineCount, totalUnpaidAmount, damagedCount)

### Transactions — `/api/v1/transactions` (LIBRARIAN role)

| Method | Path | Mô tả |
|--------|------|-------|
| GET | `?page&size` | Tất cả giao dịch có phân trang |
| GET | `/items/{id}?page&size` | Giao dịch theo item |

`TransactionListResponse`: transactionId, userId, fullName, studentId, fineAmount, borrowedDate, dueDate, returnedDate, status

---

## Flyway Migrations

- `V1__create_schema.sql` — tạo toàn bộ schema
- `V2__seed_data.sql` — seed roles, data ban đầu
- `V3__add_password_reset_tokens.sql` — bảng `password_reset_tokens(token VARCHAR(36) PK, user_id BIGINT, expires_at TIMESTAMPTZ)`

---

## Config quan trọng (`application.yml`)

```yaml
jwt:
  expirationTime: ${EXP_TOKEN:3600}
  refreshExpTime: ${EXP_REFRESH_TOKEN:604800}

base:
  url: ${BASE_URL_WEBSITE:http://localhost:8080}
  frontend-url: ${FRONTEND_URL:http://localhost:3000}  # dùng cho redirect verify/reset

spring:
  jpa:
    open-in-view: false   # quan trọng: phải @Transactional khi lazy load
  kafka:
    consumer:
      properties:
        spring.json.trusted.packages: "com.library.*"
```

Đọc `.env` tự động qua spring-dotenv (khai báo trong `library-bootstrap/pom.xml`).

---

## Email Templates

`EmailTemplates` enum ở `library-shared`, inline HTML (không dùng file `.html` riêng).

`EmailServiceImpl.formatContent(fullName, link, link, link)` — VERIFY_EMAIL_TEMPLATE cần **4 args**: fullName + link × 3.

---

## Frontend URL Patterns (redirect)

- Verify email thành công: `{frontendUrl}/#/publicpage/verify-success`
- Verify email thất bại: `{frontendUrl}/#/publicpage/verify-failed`
- Reset password link: `{frontendUrl}/#/publicpage/reset-password?token={uuid}`

---

## Lỗi đã gặp & cách fix (để không lặp lại)

| Lỗi | Nguyên nhân | Fix |
|-----|------------|-----|
| `value too long for type character varying(7)` | `request.fullName()` truyền vào field `studentId` | Dùng `request.studentId()` |
| `SerializationException` khi deserialize Kafka | Domain Value Object (`UserId`) không Jackson-friendly | Dùng plain record DTO với `Long userId` |
| `failed to lazily initialize a collection` ở Kafka consumer | Consumer thread không có JPA session | Thêm `@Transactional` trên method consumer |
| `Format specifier '%s'` ở EmailService | Template có 4 `%s` nhưng chỉ truyền 2 args | `formatContent(fullName, link, link, link)` |
| `ConflictingBeanDefinitionException: kafkaTopicConfig` | Hai module cùng tên class `KafkaTopicConfig` | Đổi auth-module thành `AuthKafkaTopicConfig` |
| Domain events lost after save | `pollDomainEvents()` gọi trên object trả về từ repo (object mới) | Poll từ object gốc TRƯỚC khi gọi `save()` |

---

## Trạng thái hiện tại (cập nhật: 2026-04-24, worktree đã clean)

**Đã hoàn thành:**
- [x] Kafka infrastructure (Docker Compose, config, topics)
- [x] RegisterUser + VerifyEmail flow (JWT, Kafka async email)
- [x] ForgotPassword + ResetPassword flow (UUID token, Kafka async email)
- [x] UpdateProfile, ChangePassword
- [x] OAuth2 Google login + Onboarding
- [x] GetAllTransactions, GetTransactionsByItem (circulation)
- [x] Dashboard DTOs skeleton: DashboardSummaryResponse, DashboardChartsResponse, RiskyUserResponse (dummy data)

**Đang làm:**
- [x] `GET /api/v1/librarians/dashboard/summary` — real data, done

**Chưa làm:**
- [ ] Dashboard charts real data (`/dashboard/charts`)
- [ ] Dashboard risky-users real data (`/dashboard/risky-users`)
- [ ] library-catalog-module (quản lý sách, tìm kiếm)
- [ ] library-recommendation-module (AI gợi ý)
- [ ] Borrow/Return/Reserve flows

---

## Chạy local

```bash
# Start Kafka + PostgreSQL
docker compose up -d

# Build & run (từ root)
./mvnw spring-boot:run -pl library-bootstrap

# IntelliJ: Ctrl+F9 để hot-reload khi sửa code
```
