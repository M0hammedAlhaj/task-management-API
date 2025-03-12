# Task Management API

## Description

The Task Management API is a robust system built with Java Spring, PostgreSQL, WebSockets, Redis Caching, and Hibernate. It allows users to manage tasks effectively, assign tasks to others, comment on tasks, and receive real-time notifications. This API is designed to streamline task management and enhance collaboration within teams.

## Features

- **Task Management**: Create, update, delete, and view tasks.
- **Task Assignment**: Assign tasks to other users.
- **Comments**: Add comments to tasks for better communication.
- **Real-time Notifications**: Receive real-time notifications using WebSockets.
- **Authentication**: Secure access to the API with authentication.
- **Caching**: Improve performance with Redis caching.

## Technologies Used

- **Java Spring**: Framework for building the API.
- **PostgreSQL**: Database for storing task data.
- **WebSockets**: For real-time notifications.
- **Redis**: Caching to enhance performance.
- **Hibernate**: ORM for database interactions.

## Installation

1. **Clone the repository**:
   ```sh
   git clone https://github.com/M0hammedAlhaj/task-management-API.git
   cd task-management-API
   ```

2. **Set up PostgreSQL**:
   - Install PostgreSQL.
   - Create a database for the project.

3. **Configure environment variables**:
   - Create a `.env` file in the root directory.
   - Add the following variables:
     ```env
     SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/your_database
     SPRING_DATASOURCE_USERNAME=your_username
     SPRING_DATASOURCE_PASSWORD=your_password
     REDIS_HOST=localhost
     REDIS_PORT=6379
     ```

4. **Install dependencies**:
   ```sh
   ./mvnw install
   ```

5. **Run the application**:
   ```sh
   ./mvnw spring-boot:run
   ```

## Usage

### Task Management

## API Endpoints

| HTTP Method | Endpoint                       | Description                                    |
|-------------|--------------------------------|------------------------------------------------|
| POST        | /tasks                         | Create a new task                              |
| GET         | /tasks                         | Get all tasks                                  |
| GET         | /tasks/{taskId}                | Get a task by ID                               |
| PUT         | /tasks/{taskId}                | Update a task by ID                            |
| DELETE      | /tasks/{taskId}                | Delete a task by ID                            |
| POST        | /tasks/{taskId}/comments       | Add a comment to a task                        |
| GET         | /tasks/{taskId}/comments       | Get all comments for a task                    |
| POST        | /tasks/{taskId}/assign         | Assign a task to a user                        |
| GET         | /notifications                 | Get all notifications for the authenticated user|
| WebSocket   | /notifications                 | Connect to receive real-time notifications     |
| POST        | /auth/register                 | Register a new user                            |
| POST        | /auth/login                    | Authenticate a user and obtain a token         |


