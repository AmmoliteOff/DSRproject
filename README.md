# DSRproject
Проект летней практики DSR

## Frontend:
Для сборки и запуска frontend части проекта:
1. Скопировать проект с GitHub
2. Перейти в папку `frontend`
3. `npm run build`
4. `cd build`
5. `npm install -g serve`
6. `serve -s build`

## Backend:
Необходимо создать базу данный postgress. Настройка БД происходит в application.properties.
После создания БД можно переходить к сборке приложения
1. Скопировать проект с GitHub
2. Перейти в папку backend
3. Убедится, что в системе установлена `Java` 19 версии и `JAVA_HOME`
4. Установить Maven в систему
5. `start mvn clean install`
6. Запустить сервер `java -jar JARPACKAGENAME`
