# Chat Application 💬

This is a full-stack real-time chat application built with **Spring Boot** for the backend and **Vue.js** for the frontend (in a separate repository). It supports both **one-to-one** and **group messaging**, with real-time communication over **WebSocket (STOMP)**.

---

## 🔧 Backend Technologies

- Java 17
- Spring Boot 3.x
- Spring WebSocket (STOMP protocol)
- Spring Security (JWT authentication)
- Spring Data JPA
- MySQL
- Lombok
- Maven

---

## 📁 Project Structure

```
chat-application/
├── config/                   # WebSocket, security, CORS, etc.
├── controller/               # REST & WebSocket message controllers
├── dto/                      # DTO classes for authentication and chat
├── entity/                   # JPA entities (User, Message, Group, etc.)
├── repository/               # JPA repositories
├── service/                  # Business logic
├── ChatApplication.java      # Main Spring Boot application
└── application.yml           # Application configuration
```

---

## ✅ Features

- User signup/login with JWT
- One-to-one private chat
- Group messaging support
- Real-time updates using WebSocket (STOMP)
- WebSocket authentication using JWT
- View message history per user
- Backend APIs to support a modern chat UI

---

## 🧪 How to Run the Project

### 1. Prerequisites

- Java 17
- MySQL
- Maven

### 2. Clone the Repository

```bash
git clone https://github.com/Sahenulislam/chat-application.git
cd chat-application
```

### 3. Setup MySQL Database

Create a database named `chat_db`:

```sql
CREATE DATABASE chat_db;
```

Update your `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/chat_db
    username: root
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### 4. Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

Spring Boot app will start at:  
`http://localhost:8080`

---

## 📡 API Endpoints

### 🔐 Auth
- `POST /api/auth/signup` – User registration  
- `POST /api/auth/login` – Get JWT token

### 💬 Messaging
- `POST /api/messages/send` – Send a message
- `GET /api/messages/history/{userId}` – Get chat history with a user

### 📲 WebSocket
- Connect to: `ws://localhost:8080/ws`
- Subscribe: `/user/{username}/queue/messages`
- Send to: `/app/chat.sendPrivateMessage`

---

## 🌐 WebSocket Sample (Frontend)

```js
const socket = new SockJS('http://localhost:8080/ws');
const stompClient = Stomp.over(socket);

stompClient.connect({ Authorization: `Bearer ${jwtToken}` }, () => {
  stompClient.subscribe('/user/sahenul/queue/messages', (message) => {
    console.log("Received:", JSON.parse(message.body));
  });

  stompClient.send("/app/chat.sendPrivateMessage", {}, JSON.stringify({
    senderId: 1,
    recipientId: 2,
    content: "Hello!"
  }));
});
```

---

## 🖼️ Frontend

> The frontend is built with Vue 3 and supports real-time messaging. It is in a separate repository (private/public depending on your setup).

---

## ✍️ Author

**Sahenul Islam**  
Backend Developer | Spring Boot Enthusiast  
🔗 GitHub: [github.com/Sahenulislam](https://github.com/Sahenulislam)

---

## 🪪 License

This project is licensed under the MIT License.
