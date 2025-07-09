# 💬 Real-Time Chat Application – Spring Boot + WebSocket

This is a full-featured real-time chat backend built using Spring Boot, WebSocket (STOMP), and JWT-based authentication. It supports user registration, login, private messaging, and message persistence in a relational database.

---

## 🚀 Features
- ✅ User registration & login with JWT authentication
- ✅ Real-time messaging using WebSocket and STOMP protocol
- ✅ Send message to specific user
- ✅ Message storage in MySQL database
- ✅ CORS configuration for frontend communication
- ✅ Active user session tracking

---

## 🛠️ Tech Stack
- Java 17
- Spring Boot 3
- Spring Security (JWT Auth)
- Spring WebSocket
- JPA + Hibernate
- MySQL
- Lombok
- Maven

---

## 📁 Project Structure
- `config/` – WebSocket, JWT, and security config
- `controller/` – REST and WebSocket endpoints
- `dto/` – Data transfer objects for requests/responses
- `model/` – JPA entities (User, Message, etc.)
- `service/` – Business logic for auth and messaging

---

## 🔐 Authentication
- Login returns a JWT token
- Secure endpoints require token in the `Authorization` header
- WebSocket handshake secured with token validation

---

## 💾 Message Storage
- Messages are stored in MySQL
- Stored with metadata (sender, receiver, timestamp)
- Easily retrievable for user chat history (can be extended to REST history endpoint)

---

## 🧪 To Do / Enhancements
- [ ] Add Swagger for API documentation
- [ ] Add unit + integration tests
- [ ] Add Docker support for easy deployment
- [ ] Add typing indicator and read receipts
- [ ] UI client using Vue/Angular (optional)

---

## 📦 API Testing
- Postman collection included (or manually test JWT + WebSocket)

---

## 🧠 Architecture Diagram
*(Add `docs/architecture.png` showing REST login + WebSocket message flow)*

---

## 🤝 Contribution
Feel free to fork and build on top of this chat system!

---
