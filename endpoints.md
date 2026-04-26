# API Endpoints

## Auth

### `POST /api/auth/signup`
- регистрация нового пользователя в системе.
```http
POST /api/auth/signup
Content-Type: application/json

{"name":"Ivan","lastName":"Petrov","username":"ivanp","email":"ivan@example.com","password":"Pass1234!","birthday":"2000-05-10"}
```
```json
{"message":"успешная регистрация"}
```

### `POST /api/auth/signin`
- вход пользователя и получение JWT-токена.
```http
POST /api/auth/signin
Content-Type: application/json

{"email":"ivan@example.com","password":"Pass1234!"}
```
```json
{"token":"eyJhbGciOiJIUzI1NiJ9..."}
```

## User

### `GET /api/user/{id}`
- получить профиль пользователя по id.
```http
GET /api/user/2b6e89a7-9491-45ea-a2f4-9625f33fd6c1
Authorization: Bearer <JWT>
```
```json
{"id":"2b6e89a7-9491-45ea-a2f4-9625f33fd6c1","fullName":"Ivan Petrov","email":"ivan@example.com"}
```

### `POST /api/user/{id}`
- обновить данные пользователя (профиль).
```http
POST /api/user/2b6e89a7-9491-45ea-a2f4-9625f33fd6c1
Authorization: Bearer <JWT>
Content-Type: application/json

{"name":"Ivan","lastName":"Sidorov","telegram":"@ivan"}
```
```json
{"id":"2b6e89a7-9491-45ea-a2f4-9625f33fd6c1","lastName":"Sidorov","telegram":"@ivan"}
```

### `POST /api/user/{id}/change-password`
- смена пароля пользователем.
```http
POST /api/user/2b6e89a7-9491-45ea-a2f4-9625f33fd6c1/change-password
Authorization: Bearer <JWT>
Content-Type: application/json

{"oldPassword":"Pass1234!","newPassword":"NewPass1234!"}
```
```json
{"message":"Password changed successfully"}
```

## Skills

### `GET /api/skills/getall-skills`
- получить справочник навыков.
```http
GET /api/skills/getall-skills
```
```json
[{"id":1,"name":"Java","category":"BACKEND"}]
```

## Schedule

### `GET /api/schedule`
- получить весь список событий расписания.
```http
GET /api/schedule
```
```json
[{"id":1,"title":"Открытие хакатона","startTime":"2026-05-10T10:00:00"}]
```

### `GET /api/schedule/admin`
- альтернативный endpoint списка событий (админский роут).
```http
GET /api/schedule/admin
```
```json
[{"id":1,"title":"Открытие хакатона"}]
```

### `GET /api/schedule/{id}`
- получить конкретное событие по id.
```http
GET /api/schedule/1
```
```json
{"id":1,"title":"Открытие хакатона","description":"Старт"}
```

## Event Registrations

### `POST /api/events/{eventId}/register-solo`
- зарегистрировать текущего пользователя на событие индивидуально.
```http
POST /api/events/1/register-solo
Authorization: Bearer <JWT>
```
```json
{"message":"Успешная регистрация"}
```

### `GET /api/events/{eventId}/registrations`
- получить список регистраций на событие для судей/админов.
```http
GET /api/events/1/registrations
Authorization: Bearer <JWT>
```
```json
[{"id":12,"userId":"2b6e89a7-9491-45ea-a2f4-9625f33fd6c1","status":"ACTIVE"}]
```

## Teams

### `POST /api/teams`
- создать новую команду.
```http
POST /api/teams
Content-Type: application/json

{"name":"CodeStorm","description":"Команда backend+frontend","projectName":"HackAI","projectDescription":"Платформа","creatorId":"2b6e89a7-9491-45ea-a2f4-9625f33fd6c1","creatorName":"Ivan Petrov"}
```
```json
{"id":"a1a7f1b0-435d-4219-bdb4-7e320833f695","name":"CodeStorm","creatorId":"2b6e89a7-9491-45ea-a2f4-9625f33fd6c1","isActive":true}
```

### `GET /api/teams/{teamId}`
- получить карточку команды.
```http
GET /api/teams/a1a7f1b0-435d-4219-bdb4-7e320833f695
```
```json
{"id":"a1a7f1b0-435d-4219-bdb4-7e320833f695","name":"CodeStorm","isActive":true}
```

### `GET /api/teams/user/{userId}`
- получить все команды пользователя (как лидера или участника).
```http
GET /api/teams/user/2b6e89a7-9491-45ea-a2f4-9625f33fd6c1
```
```json
[{"id":"a1a7f1b0-435d-4219-bdb4-7e320833f695","name":"CodeStorm"}]
```

### `GET /api/teams/{teamId}/members`
- получить состав участников команды.
```http
GET /api/teams/a1a7f1b0-435d-4219-bdb4-7e320833f695/members
```
```json
[{"id":1,"userId":"2b6e89a7-9491-45ea-a2f4-9625f33fd6c1","userName":"Ivan Petrov","isCreator":true}]
```

### `GET /api/teams/{teamId}/is-creator/{userId}`
- проверить, является ли пользователь создателем команды.
```http
GET /api/teams/a1a7f1b0-435d-4219-bdb4-7e320833f695/is-creator/2b6e89a7-9491-45ea-a2f4-9625f33fd6c1
```
```json
{"isCreator":true}
```

### `GET /api/teams/{teamId}/is-member/{userId}`
- проверить, состоит ли пользователь в команде.
```http
GET /api/teams/a1a7f1b0-435d-4219-bdb4-7e320833f695/is-member/2b6e89a7-9491-45ea-a2f4-9625f33fd6c1
```
```json
{"isMember":true}
```

## Team Join Requests

### `POST /api/join-requests`
- отправить заявку на вступление в команду.
```http
POST /api/join-requests
Content-Type: application/json

{"teamId":"a1a7f1b0-435d-4219-bdb4-7e320833f695","userId":"f8bca2c9-676e-4f5d-9d16-29f6b5b91f08","userName":"Petr Ivanov","message":"Хочу присоединиться"}
```
```json
{"id":11,"teamId":"a1a7f1b0-435d-4219-bdb4-7e320833f695","status":"PENDING"}
```

### `POST /api/join-requests/process`
- обработать заявку (approve/reject).
```http
POST /api/join-requests/process
Content-Type: application/json

{"requestId":11,"action":"approve"}
```
```json
{"id":11,"teamId":"a1a7f1b0-435d-4219-bdb4-7e320833f695","status":"APPROVED"}
```

### `GET /api/join-requests/team/{teamId}/pending?userId={creatorId}`
- получить pending-заявки в команду для создателя.
```http
GET /api/join-requests/team/a1a7f1b0-435d-4219-bdb4-7e320833f695/pending?userId=2b6e89a7-9491-45ea-a2f4-9625f33fd6c1
```
```json
[{"id":11,"userId":"f8bca2c9-676e-4f5d-9d16-29f6b5b91f08","status":"PENDING"}]
```

### `GET /api/join-requests/user/{userId}`
- показать пользователю историю его заявок в команды.
```http
GET /api/join-requests/user/f8bca2c9-676e-4f5d-9d16-29f6b5b91f08
```
```json
[{"id":11,"teamId":"a1a7f1b0-435d-4219-bdb4-7e320833f695","status":"APPROVED"}]
```

## Team Invitations

### `POST /api/invitations`
- отправить приглашение пользователю в команду.
```http
POST /api/invitations
Content-Type: application/json

{"teamId":"a1a7f1b0-435d-4219-bdb4-7e320833f695","invitedUserId":"f8bca2c9-676e-4f5d-9d16-29f6b5b91f08","invitedUserName":"Petr Ivanov","invitedByUserId":"2b6e89a7-9491-45ea-a2f4-9625f33fd6c1","invitedByUserName":"Ivan Petrov","message":"Присоединяйся к команде"}
```
```json
{"id":17,"teamId":"a1a7f1b0-435d-4219-bdb4-7e320833f695","status":"PENDING"}
```

### `POST /api/invitations/respond`
- принять или отклонить приглашение.
```http
POST /api/invitations/respond
Content-Type: application/json

{"invitationId":17,"userId":"f8bca2c9-676e-4f5d-9d16-29f6b5b91f08","action":"accept"}
```
```json
{"id":17,"teamId":"a1a7f1b0-435d-4219-bdb4-7e320833f695","status":"ACCEPTED"}
```

### `GET /api/invitations/user/{userId}`
- получить все приглашения пользователя.
```http
GET /api/invitations/user/f8bca2c9-676e-4f5d-9d16-29f6b5b91f08
```
```json
[{"id":17,"teamId":"a1a7f1b0-435d-4219-bdb4-7e320833f695","status":"PENDING"}]
```

### `GET /api/invitations/user/{userId}/pending`
- получить только непросмотренные/активные приглашения пользователя.
```http
GET /api/invitations/user/f8bca2c9-676e-4f5d-9d16-29f6b5b91f08/pending
```
```json
[{"id":17,"teamId":"a1a7f1b0-435d-4219-bdb4-7e320833f695","status":"PENDING"}]
```

### `GET /api/invitations/team/{teamId}?userId={creatorId}`
- получить все приглашения, отправленные от имени команды.
```http
GET /api/invitations/team/a1a7f1b0-435d-4219-bdb4-7e320833f695?userId=2b6e89a7-9491-45ea-a2f4-9625f33fd6c1
```
```json
[{"id":17,"invitedUserId":"f8bca2c9-676e-4f5d-9d16-29f6b5b91f08","status":"PENDING"}]
```

### `GET /api/invitations/team/{teamId}/pending?userId={creatorId}`
- получить только pending-приглашения команды.
```http
GET /api/invitations/team/a1a7f1b0-435d-4219-bdb4-7e320833f695/pending?userId=2b6e89a7-9491-45ea-a2f4-9625f33fd6c1
```
```json
[{"id":17,"invitedUserId":"f8bca2c9-676e-4f5d-9d16-29f6b5b91f08","status":"PENDING"}]
```

## Team Hackathon Registrations

### `POST /api/hackathon-registrations`
- зарегистрировать команду на конкретный хакатон.
```http
POST /api/hackathon-registrations
Content-Type: application/json

{"teamId":"a1a7f1b0-435d-4219-bdb4-7e320833f695","hackathonId":"hack-2026-spring","hackathonName":"Spring Hackathon 2026","registeredBy":"2b6e89a7-9491-45ea-a2f4-9625f33fd6c1"}
```
```json
{"id":1,"teamId":"a1a7f1b0-435d-4219-bdb4-7e320833f695","hackathonId":"hack-2026-spring"}
```

### `GET /api/hackathon-registrations/team/{teamId}`
- получить все регистрации одной команды на хакатоны.
```http
GET /api/hackathon-registrations/team/a1a7f1b0-435d-4219-bdb4-7e320833f695
```
```json
[{"id":1,"teamId":"a1a7f1b0-435d-4219-bdb4-7e320833f695","hackathonId":"hack-2026-spring"}]
```

### `GET /api/hackathon-registrations/hackathon/{hackathonId}`
- получить список команд, зарегистрированных на хакатон.
```http
GET /api/hackathon-registrations/hackathon/hack-2026-spring
```
```json
[{"id":1,"teamId":"a1a7f1b0-435d-4219-bdb4-7e320833f695","hackathonId":"hack-2026-spring"}]
```

## Team Admin (`ADMIN`)

### `GET /api/admin/teams`
- получить полный список команд (админ-панель).
### `GET /api/admin/teams/{teamId}`
- получить подробности команды по id (админ-панель).
### `PUT /api/admin/teams/{teamId}`
- изменить данные команды (название, описание, активность).
### `DELETE /api/admin/teams/{teamId}`
- удалить команду.
### `GET /api/admin/teams/search?query={query}`
- поиск команд по названию.
### `GET /api/admin/teams/active`
- получить только активные команды.
### `GET /api/admin/teams/user/{userId}`
- получить команды конкретного пользователя через админку.

```http
GET /api/admin/teams/active
Authorization: Bearer <JWT_ADMIN>
```
```json
[{"id":"a1a7f1b0-435d-4219-bdb4-7e320833f695","name":"CodeStorm","isActive":true}]
```

## Admin Panel (`ADMIN`)

### `POST /api/admin/add-skills`
- добавить новые навыки в справочник.
### `DELETE /api/admin/delete-skills`
- удалить навыки из справочника.
### `GET /api/admin/getall-users`
- получить список всех пользователей.
### `POST /api/admin/update-user/{id}`
- изменить данные пользователя.
### `POST /api/admin/block-user/{id}`
- заблокировать пользователя.
### `POST /api/admin/delete-user/{id}`
- удалить пользователя.
### `POST /api/admin/add-points/{id}`
- начислить пользователю баллы.
### `POST /api/admin/schedule/create-event`
- создать событие в расписании.
### `PUT /api/admin/schedule/update-event/{id}`
- обновить событие в расписании.
### `DELETE /api/admin/schedule/delete-event/{id}`
- удалить событие из расписания.

```http
GET /api/admin/getall-users
Authorization: Bearer <JWT_ADMIN>
```
```json
[{"id":"2b6e89a7-9491-45ea-a2f4-9625f33fd6c1","fullName":"Ivan Petrov"}]
```

## Swagger / OpenAPI

### `GET /swagger-ui.html`
- открыть Swagger UI.
### `GET /swagger-ui/**`
- служебные ресурсы Swagger UI.
### `GET /v3/api-docs/**`
- получить OpenAPI JSON-схему API.

```http
GET /v3/api-docs
```
```json
{"openapi":"3.0.1","info":{"title":"KDT_HACK API","version":"v1"}}
```