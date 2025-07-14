# ✅ Java TODO API with MongoDB and Redis (Dockerized)

This is a simple **Java-based TODO REST API** that uses:

- ✅ **Spring Boot** for the application framework  
- ✅ **MongoDB** for data storage  
- ✅ **Redis** for caching  
- ✅ **Docker Compose** to run the full environment (App + MongoDB + Redis)

It is designed to be deployed easily in any environment, including **AWS EC2**.

---

## 📁 Project Structure

```

todo-api-java-mongo-redis-docker/
├── Dockerfile               # Docker build for the Java Spring Boot App
├── docker-compose.yml       # Compose file to run app + MongoDB + Redis
├── todo-api.jar             # Compiled Spring Boot application JAR
├── README.md                # You're reading it
└── (Java source files)      # Inside src/, if applicable

````

---

## ⚙️ Requirements

> On your **local machine** or **EC2 instance**, you should have:

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)
- An internet connection (to pull base images on first build)

---

## 🚀 How to Run the App (Using Docker Compose)

### 🧱 Step 1: Clone the Repository
```bash
git clone https://github.com/sanyam-harness/todo-api-java-mongo-redis-docker.git
cd todo-api-java-mongo-redis-docker
````

### 🐳 Step 2: Run with Docker Compose

```bash
docker-compose up --build
```

This will:

* Build the Java application container
* Start MongoDB (`mongo:7.0`) on port `27017`
* Start Redis (`redis:7.2`) on port `6379`
* Start your Spring Boot App on port `8080`

If ports like `27017`, `6379`, or `8080` are already in use, stop existing services or update the ports in `docker-compose.yml`.

---

## 🌐 API Endpoints

Base URL: `http://<YOUR_EC2_PUBLIC_IP>:8080`

### ➕ Create a TODO

```http
POST /todos
Content-Type: application/json

{
  "title": "Sample Task",
  "description": "This is a task to complete"
}
```

### 📄 List All TODOs

```http
GET /todos
```

### 🧾 Get TODO by ID

```http
GET /todos/{id}
```

### ♻️ Update TODO

```http
PUT /todos/{id}
Content-Type: application/json

{
  "title": "Updated Task",
  "description": "Updated description"
}
```

### ❌ Delete TODO

```http
DELETE /todos/{id}
```

> Redis is used to cache the response of `GET /todos`. After creating, updating, or deleting a task, the cache is invalidated automatically.

---

## ☁️ Deploying on AWS EC2

### ✅ Prerequisites:

* EC2 instance (Ubuntu preferred)
* Docker + Docker Compose installed
* Security group allows inbound traffic on port `8080`

### 📦 Deploy Steps:

1. **SSH into your EC2 instance:**

```bash
ssh -i path/to/java-key-pair.pem ubuntu@<EC2_PUBLIC_IP>
```

2. **Transfer project files:**

```bash
scp -i path/to/java-key-pair.pem -r todo-api-java-mongo-redis-docker ubuntu@<EC2_PUBLIC_IP>:~/
```

3. **Run on EC2:**

```bash
cd todo-api-java-mongo-redis-docker
docker-compose up --build
```

4. Access the app:

```bash
http://<EC2_PUBLIC_IP>:8080/todos
```

---

## 🛠️ Troubleshooting

| Problem                      | Solution                                                       |
| ---------------------------- | -------------------------------------------------------------- |
| `port already in use`        | Use `lsof -i :<port>` to find and stop the process             |
| Redis/Mongo already running? | Stop them using `sudo systemctl stop mongod` or `redis-server` |
| App not reachable?           | Make sure your EC2 security group allows port `8080`           |

---

### 🚀 Deploy Java TODO API (with MongoDB + Redis) on EC2 with Docker Compose

> These steps assume:
>
> * Your EC2 instance is running at **`13.53.124.194`**
> * You have the key file `java-key-pair.pem` on your Mac
> * You’ve already copied your Java project (with `Dockerfile` and `docker-compose.yml`) to the EC2 instance

---

#### 🔐 1. SSH into EC2

```bash
ssh -i ~/Desktop/java-key-pair.pem ubuntu@13.53.124.194
```

---

#### 📁 2. Navigate to the project directory

```bash
cd ~/todo-api-java-mongo-redis-docker
```

---

#### 🐳 3. Build and run all containers using Docker Compose

```bash
docker-compose up --build -d
```

---

#### ✅ 4. Check running containers

```bash
docker ps
```

Expected Output:

```
CONTAINER ID   IMAGE                     COMMAND                  ...     PORTS
xxxxxxx        todo-java-app             "java -jar app.jar"      ...     0.0.0.0:8080->8080/tcp
xxxxxxx        mongo:7                   "docker-entrypoint.s…"   ...     0.0.0.0:27017->27017/tcp
xxxxxxx        redis:7                   "docker-entrypoint.s…"   ...     0.0.0.0:6379->6379/tcp
```

---

#### 🌐 5. Test the Java API from your Mac (Postman or curl)

```bash
curl http://13.53.124.194:8080/todos
```

You should get either an empty array `[]` or saved todo items as JSON.

---


## 📜 License

This project is open-source and available under the MIT License.

---

## 👨‍💻 Author

**Sanyam Jain**
[GitHub Profile →](https://github.com/sanyam-harness)
