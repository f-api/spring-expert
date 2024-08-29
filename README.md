# 스프링 숙련 과제

<br>

## API 명세서

### 인증/인가 관련 상태 코드:
  - 400 *Bad Request*: JWT 토큰이 없는 경우, 잘못된 형식의 JWT 토큰, 잘못된 JWT 클레임 등이 발생한 경우
  - 401 *Unauthorized*: 유효하지 않은 JWT 서명, 만료된 JWT 토큰, 지원되지 않는 JWT 토큰, 기타 JWT 검증 오류 발생 시
  - 403 *Forbidden*: 관리자 권한이 없는 경우, 접근이 거부된 경우

<br>

### **Users**

| **Method** | **Endpoint**         | **Description**            | **Parameters**         | **Request Body**                        | **Response**                                                                                                             | **Status Code** |
|------------|----------------------|----------------------------|------------------------|-----------------------------------------|--------------------------------------------------------------------------------------------------------------------------|-----------------|
| `GET`      | `/users`             | 모든 사용자 목록 조회      | 없음                   | 없음                                    | `[ { "id": int, "username": string, "email": string, "createdAt": string, "modifiedAt": string } ]`                      | `200 OK`        |
| `GET`      | `/users/{userId}`    | 사용자 ID로 사용자 조회    | `userId`: 사용자 ID    | 없음                                    | `{ "id": int, "username": string, "email": string, "createdAt": string, "modifiedAt": string }`                          | `200 OK`        |
| `PUT`      | `/users/{userId}`    | 사용자 정보 수정           | `userId`: 사용자 ID    | `{ "username": string, "email": string }` | `{ "id": int, "username": string, "email": string, "createdAt": string, "modifiedAt": string }`                          | `200 OK`        |
| `DELETE`   | `/users/{userId}`    | 사용자 삭제                | `userId`: 사용자 ID    | 없음                                    | 없음                                                                                                                     | `200 OK`        |

<br>

### **Todos**

| **Method** | **Endpoint**                   | **Description**                 | **Parameters**                     | **Request Body**                                      | **Response**                                                                                                             | **Status Code**  |
|------------|--------------------------------|----------------------------------|------------------------------------|------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|------------------|
| `GET`      | `/todos`                       | 모든 할 일 목록 조회            | `page`: 페이지 번호 <br> `size`: 페이지 크기 | 없음                                                | `{ "totalPages": int, "totalElements": int, "content": [ { "id": int, "title": string, "contents": string, "createdAt": string } ] }` | `200 OK`         |
| `GET`      | `/todos/{todoId}`              | 할 일 ID로 조회                 | `todoId`: 할 일 ID                | 없음                                                 | `{ "id": int, "title": string, "contents": string, "createdAt": string }`                                                  | `200 OK`         |
| `PUT`      | `/todos/{todoId}`              | 할 일 정보 수정                 | `todoId`: 할 일 ID                | `{ "title": string, "contents": string }`             | `{ "id": int, "title": string, "contents": string, "createdAt": string, "modifiedAt": string }`                           | `200 OK`         |
| `DELETE`   | `/todos/{todoId}`              | 할 일 삭제                      | `todoId`: 할 일 ID                | 없음                                                 | 없음                                                                                                                     | `200 OK`         |
| `GET`      | `/todos/{todoId}/comments`     | 할 일에 대한 댓글 목록 조회     | `todoId`: 할 일 ID                | 없음                                                 | `[ { "id": int, "contents": string, "createdAt": string, "modifiedAt": string } ]`                                        | `200 OK`         |
| `POST`     | `/todos/{todoId}/comments`     | 할 일에 댓글 추가               | `todoId`: 할 일 ID                | `{ "userId": int, "contents": string }`               | `{ "id": int, "contents": string, "createdAt": string, "modifiedAt": string }`                                            | `200 OK`         |
| `GET`      | `/todos/optimized`             | 최적화된 할 일 목록 조회        | `page`: 페이지 번호 <br> `size`: 페이지 크기 | 없음                                                | `{ "totalPages": int, "totalElements": int, "content": [ { "id": int, "title": string, "contents": string, "createdAt": string } ] }` | `200 OK`         |

<br>

### **Comments**

| **Method** | **Endpoint**                    | **Description**            | **Parameters**              | **Request Body**                                      | **Response**                                                                                                             | **Status Code**  |
|------------|---------------------------------|----------------------------|-----------------------------|------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|------------------|
| `GET`      | `/todos/comments/{commentId}`   | 댓글 ID로 조회             | `commentId`: 댓글 ID        | 없음                                                 | `{ "id": int, "user": { "id": int, "username": string }, "contents": string, "createdAt": string, "modifiedAt": string }`  | `200 OK`         |
| `PUT`      | `/todos/comments/{commentId}`   | 댓글 정보 수정             | `commentId`: 댓글 ID        | `{ "contents": string }`                             | `{ "id": int, "contents": string, "createdAt": string, "modifiedAt": string }`                                            | `200 OK`         |
| `DELETE`   | `/todos/comments/{commentId}`   | 댓글 삭제                  | `commentId`: 댓글 ID        | 없음                                                 | 없음                                                                                                                     | `200 OK`         |

<br>

### **Auth**

| **Method** | **Endpoint**        | **Description**            | **Parameters**   | **Request Body**                                                     | **Response**                                                                                     | **Status Code** |
|------------|---------------------|----------------------------|------------------|----------------------------------------------------------------------|----------------------------------------------------------------------------------------------|-----------------|
| `POST`     | `/auth/signup`      | 회원가입                  | 없음             | `{ "username": string, "email": string, "password": string, "userRole": string }` | `{ "bearerToken": "string" }`                                                                 | `200 OK`        |
| `POST`     | `/auth/signin`      | 로그인                    | 없음             | `{ "email": string, "password": string }`                             | `{ "bearerToken": "string" }`                                                                 | `200 OK`        |

<br>

### **Managers**

| **Method** | **Endpoint**                           | **Description**               | **Parameters**                | **Request Body**                                      | **Response**                                                                                                             | **Status Code**  |
|------------|----------------------------------------|-------------------------------|-------------------------------|------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|------------------|
| `GET`      | `/todos/{todoId}/managers`            | 할 일에 대한 매니저 목록 조회 | `todoId`: 할 일 ID            | 없음                                                 | `[ { "id": int, "username": string, "email": string } ]`                                                                 | `200 OK`         |
| `POST`     | `/todos/{todoId}/managers`            | 할 일에 매니저 추가           | `todoId`: 할 일 ID            | `{ "todoUserId": int, "managerUserId": int }`         | 없음                                                                                                                     | `200 OK`         |
| `GET`      | `/todos/{todoId}/managers/optimized`  | 최적화된 매니저 목록 조회     | `todoId`: 할 일 ID            | 없음                                                 | `[ { "id": int, "username": string, "email": string } ]`                                                                 | `200 OK`         |

<br>

## ERD

<img width="995" alt="image" src="https://github.com/user-attachments/assets/74fc1559-567b-4414-afbd-aa71fdc70d6c">
