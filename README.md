# DSRproject
Проект летней практики DSR.
Собранные артефакты находятся в Actions. Можно скачать их, перейдя в нужный workflow.

## Frontend:
Для сборки и запуска frontend части проекта:
1. Скопировать проект с GitHub
2. Установить Node Js в систему
3. Перейти в папку `frontend`
4. `npm run build`
5. Установить и настроить nginx на раздачу статики. Пример:
```
server {
  listen 3000;
  server_name localhost;

  location / {
    root /usr/share/nginx/html;
    index index.html;
    try_files $uri $uri/ /index.html;
  }
}
```
## Backend:
Необходимо создать базу данных postgress.
После создания БД можно переходить к настройке приложения:
1. Перейти в src/main/resources
2. Открыть файл application.properties
3. Изменить данные для БД
4. Изменить данные от почты, которая будет отвечать за отправку кода пользователю

### Сборка проекта
1. Скопировать проект с GitHub
2. Перейти в папку backend
3. Убедится, что в системе установлена `Java` 17 версии и `JAVA_HOME`
4. Установить Maven в систему
5. `start mvn clean install`
6. Запустить сервер `java -jar JARPACKAGENAME`

## Запуск через Docker-compose:
1. Настроить application.properties `(Backend)`
2. Настроить constants.js `(Frontend)`
3. ***ВАЖНО*** изменить логин и пароль от базы данных в `docker-compose.yml`
4. Перейти в папку с проектом -> запустить ядро `Docker` -> прописать `docker-compose build` -> прописать `docker-compose up`
