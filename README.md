# 🧠 Task Manager API

A simple Spring Boot REST API for managing tasks — built step-by-step to learn Spring Boot fundamentals, testing, logging, and Git workflow.

---

## 🚀 Features

✅ Create, read, update, and delete tasks  
✅ Mark tasks as completed  
✅ Input validation & global exception handling  
✅ Logging with SLF4J  
✅ In-memory H2 database (switchable to MySQL)  
✅ Clean layered architecture (Controller → Service → Repository)  

---

## 🧩 Tech Stack

- Java 17+  
- Spring Boot 3  
- Spring Web  
- Spring Data JPA  
- H2 Database / MySQL  
- Maven  
- Lombok  

---

## 📁 Project Structure

```
taskmanager/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/example/taskmanager/
    │   │   ├── controller/
    │   │   ├── service/
    │   │   ├── repository/
    │   │   ├── model/
    │   │   ├── exception/
    │   │   └── TaskmanagerApplication.java
    │   └── resources/
    │       └── application.properties
    └── test/
        └── java/com/example/taskmanager/
```

---

## ⚙️ Setup & Run

### 🧰 Prerequisites

- JDK 17 or higher  
- Maven  
- (Optional) MySQL installed & running  

### ▶️ Run the app

```bash
git clone https://github.com/<your-username>/taskmanager.git
cd taskmanager
mvn spring-boot:run
```

The app starts on:  
👉 http://localhost:8080/

---

## 💾 Configuration

### ✅ Using H2 (in-memory, default)

```
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=update
```

H2 Console → http://localhost:8080/h2-console  

### 🔄 Switch to MySQL

```
spring.datasource.url=jdbc:mysql://localhost:3306/taskdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

---

## 🧠 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/tasks | Get all tasks |
| POST | /api/tasks | Add a new task |
| PUT | /api/tasks/{id}/done | Mark task as done |
| DELETE | /api/tasks/{id} | Delete a task |

#### ✅ Example Request (JSON)

```json
POST /api/tasks
{
  "title": "Learn Spring Boot",
  "completed": false
}
```

#### ✅ Example Response

```json
{
  "id": 1,
  "title": "Learn Spring Boot",
  "completed": false
}
```

---

## 🧪 Testing with Postman

| Action | Method | URL | Body |
|--------|--------|-----|------|
| Fetch all tasks | GET | http://localhost:8080/api/tasks | — |
| Add a task | POST | http://localhost:8080/api/tasks | {"title":"New Task","completed":false} |
| Mark as done | PUT | http://localhost:8080/api/tasks/1/done | — |
| Delete | DELETE | http://localhost:8080/api/tasks/1 | — |

💡 Make sure to set `Content-Type: application/json` for POST requests.

---

## 🧱 Architecture Overview

Controller → handles HTTP requests  
Service → business logic  
Repository → DB communication (JPA)  
Entity → Task model  
Exception → global error handling  

---

## 🪶 Logging Example

```java
private static final Logger log = LoggerFactory.getLogger(TaskService.class);
log.info("Fetching all tasks");
```

---

## 🌐 CORS Support

```java
@CrossOrigin(origins = "*")
```

---

## ✅ Validation & Exception Handling

```java
@Valid
@NotBlank(message = "Title cannot be blank")
```

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound() {
        return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
    }
}
```

---

## 🧩 Future Improvements

- [ ] Add search/filter by title  
- [ ] Add frontend (React / Angular)  
- [ ] Add user authentication (Spring Security)  

---

## 🧑‍💻 Author

**Mithun Shanjai**  
Built with ❤️ while learning Spring Boot fundamentals step-by-step.

---

## 🪄 License

This project is open-sourced under the **MIT License**.

---
