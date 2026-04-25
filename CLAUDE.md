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
library-bootstrap/             — Entry point, application.yml, Flyway migrations
library-shared/                — EmailService, KafkaTopics, Kafka DTOs, exceptions, utils
library-auth-module/           — JWT, Login, OAuth2, VerifyEmail, ForgotPassword, ResetPassword
library-user-module/           — User domain, SignUp, UpdateProfile, ChangePassword, Kafka consumers
library-catalog-module/        — Publications, Items, Authors, Categories (đã có skeleton)
library-circulation-module/    — Dashboard librarian, BorrowingTransactions, Reservations, Fines
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

### Dashboard / Reporting Queries
- **KHÔNG** dùng nhiều JPA repository calls riêng lẻ cho dashboard
- Dùng `NamedParameterJdbcTemplate` với native SQL — 1 round trip duy nhất
- Lý do: dashboard là reporting concern, không phải domain operation — JPA overhead là không cần thiết
- TimeZone: luôn dùng `ZoneId.of("Asia/Ho_Chi_Minh")` để tính "hôm nay" đúng giờ VN

---

## Kafka Topics

| Topic | Constant | Producer | Consumer |
|-------|----------|----------|----------|
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

## API Endpoints

### Auth — `/api/v1/auth`

| Method | Path | Mô tả |
|--------|------|-------|
| POST | `/register` | Đăng ký, gửi email verify qua Kafka |
| POST | `/login` | Đăng nhập username/password |
| POST | `/logout` | Header: `re-token` |
| GET | `/verify-email?token=` | Verify email, redirect FE |
| POST | `/forgot-password` | Body: `{email}`, gửi reset link qua Kafka |
| POST | `/reset-password` | Body: `{token, newPassword, confirmPassword}` |
| POST | `/refresh-accesstoken` | Header: `re-token` |
| GET | `/login-with-social?loginType=` | Bắt đầu OAuth2 |
| POST | `/social-callback/{registrationId}` | OAuth2 callback |
| POST | `/onboarding-profile` | Hoàn thiện profile sau OAuth2 lần đầu |

### User — `/api/v1/users`

| Method | Path | Mô tả |
|--------|------|-------|
| GET | `/my-profile` | Lấy profile hiện tại |
| PUT | `/my-profile` | Cập nhật profile, trả về `UserResponse` |
| PUT | `/my-profile/change-password` | Đổi mật khẩu (min 8 ký tự) |

### Librarian — `/api/v1/librarians` (LIBRARIAN role)

| Method | Path | Trạng thái |
|--------|------|-----------|
| GET | `/dashboard/summary` | Hoàn thành — real data |
| GET | `/dashboard/charts?period=` | Hoàn thành — real data |
| GET | `/dashboard/risky-users?page&size&sortBy&sortDir` | Hoàn thành — real data |

**`DashboardSummaryResponse`:**
```
overview:        totalUsers, activeUsers, totalPublications, totalItems, availableItems
todayTransaction: borrowedToday, returnedToday, damagedToday, lostToday, newlyOverdueToday
pendingActions:  waitingForPickup, overdueTransactions, reservationsPending
fineSummary:     unpaidFineCount, totalUnpaidAmount, collectedToday
```

**`DashboardChartsResponse`:**
```
weeklyBorrowReturnTrend: List<TrendPoint(date, borrowed, returned)>
  - WEEKLY/MONTHLY   → date format "yyyy-MM-dd", generate_series theo ngày
  - SIX_MONTHS/YEARLY → date format "yyyy-MM",   generate_series theo tháng
itemStatusDistribution:  available, borrowed, reserved, inMaintenance, lost  (snapshot, không theo period)
topBorrowedPublications: List<(publicationId, title, borrowCount, coverImageUrl)>  top 3 theo period
fineTypeDistribution:    overdueReturn, damagedBook, lostBook  (theo period)
```

`DashboardPeriod` enum: `WEEKLY, MONTHLY, SIX_MONTHS, YEARLY`

### Transactions — `/api/v1/transactions` (LIBRARIAN role)

| Method | Path | Mô tả |
|--------|------|-------|
| GET | `?page&size` | Tất cả giao dịch có phân trang |
| GET | `/items/{id}?page&size` | Giao dịch theo item |

---

## Flyway Migrations

- `V1__create_schema.sql` — toàn bộ schema, bao gồm `password_reset_tokens`
- `V2__seed_data.sql` — seed data: roles, users, publications, items (15), transactions (15), fines (8), reservations

**Không còn V3** — `password_reset_tokens` đã được gộp vào V1.

Seed data coverage cho dashboard test:
- `borrowedToday`: 2, `returnedToday`: 2, `newlyOverdueToday`: 2
- `overdueTransactions`: 5, `waitingForPickup`: 3, `reservationsPending`: 2
- `unpaidFineCount`: 5, `collectedToday`: 40.000đ

---

## Config quan trọng (`application.yml`)

```yaml
jwt:
  expirationTime: ${EXP_TOKEN:3600}
  refreshExpTime: ${EXP_REFRESH_TOKEN:604800}

base:
  url: ${BASE_URL_WEBSITE:http://localhost:8080}
  frontend-url: ${FRONTEND_URL:http://localhost:3000}

spring:
  jpa:
    open-in-view: false   # phải @Transactional khi lazy load
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
| Domain events lost after save | `pollDomainEvents()` gọi trên object trả về từ repo | Poll từ object gốc TRƯỚC khi gọi `save()` |

---

## Trạng thái hiện tại (cập nhật: 2026-04-24)

**Hoàn thành:**
- [x] Kafka infrastructure (Docker Compose, config, topics)
- [x] RegisterUser + VerifyEmail flow
- [x] ForgotPassword + ResetPassword flow
- [x] UpdateProfile, ChangePassword
- [x] OAuth2 Google login + Onboarding
- [x] GetAllTransactions, GetTransactionsByItem
- [x] `GET /dashboard/summary` — `NamedParameterJdbcTemplate`, 1 native query
- [x] `GET /dashboard/charts?period=` — `NamedParameterJdbcTemplate`, `generate_series` PostgreSQL

**Chưa làm:**
- [x] `GET /dashboard/risky-users` — `NamedParameterJdbcTemplate`, HAVING filter + dynamic ORDER BY
- [x] Notification system — WebSocket + Kafka + REST APIs
- [x] Borrow flow — BorrowRequest, ConfirmPickup, Lookup, ExpiredPickupScheduler
- [ ] library-recommendation-module (AI gợi ý)

---

## Notification System

**Flow:** Kafka topic `notification.send` → `NotificationEventConsumer` → lưu DB + push WebSocket

**WebSocket:**
- Endpoint: `/ws` (SockJS)
- Auth: STOMP CONNECT header `Authorization: Bearer <token>` — validated bởi `WebSocketAuthChannelInterceptor` qua `JwtUserExtractor` port (impl ở `library-auth-module`)
- Client subscribe: `/user/queue/notifications`
- Server push: `convertAndSendToUser(userId, "/queue/notifications", payload)`

**Kafka DTO:** `NotificationMessage(userId, type, title, message, link, referenceId)` — `library-shared`

**`NotificationType`:** `BOOK_RESERVED`, `BOOK_AVAILABLE`, `BORROW_SUCCESS`, `BORROW_CANCELLED_EXPIRED`, `OVERDUE_WARNING`, `FINE_ISSUED`, `SYSTEM_MAINTENANCE`, `RETURN_REMINDER`

**APIs — `/api/v1/users/notifications`:**
- `GET ?page&size` — danh sách có phân trang
- `GET /unread-count` — số chưa đọc
- `PUT /{id}/read` — đánh dấu 1 đã đọc
- `PUT /read-all` — đánh dấu tất cả đã đọc

**V3 migration:** thêm `title`, `reference_id` vào `notifications`; thêm `read_at` vào `user_notifications`; mở rộng `type` constraint

---

## Chạy local

```bash
# Start Kafka + PostgreSQL (lần đầu hoặc sau khi đổi schema)
docker compose down -v && docker compose up -d

# Build & run
./mvnw spring-boot:run -pl library-bootstrap

# IntelliJ: Ctrl+F9 để hot-reload khi sửa code
```
