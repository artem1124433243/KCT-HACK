# **\_\_ENDPOINTS\_\_**

### 

### ***1. Получить все ачивки:***

**GET /api/achievements**



### ***2. Получить ачивки пользователя по ID:***

**POST /api/achievement**

**Content-Type: application/json**



*{*

    *"byType": "ID",*

    *"value": "user123"*

*}*



### ***3. Получить пользователей с определенной ачивкой:***

**POST /api/user\_achievements**

**Content-Type: application/json**



*{*

    *"byType": "achievement",*

    *"value": "1"*

*}*



### ***4. Получить активность пользователя по ID:***

**POST /api/user\_activity**

**Content-Type: application/json**



*{*

    *"byType": "ID",*

    *"value": "user123"*

*}*



### ***5. Получить пользователей, отсортированных по посещениям:***

**POST /api/user\_activity**

**Content-Type: application/json**



*{*

    *"byType": "visited\_hackathons"*

*}*



### ***6. Получить пользователей, отсортированных по баллам:***

**POST /api/user\_activity**

**Content-Type: application/json**



*{*

    *"byType": "points"*

*}*

