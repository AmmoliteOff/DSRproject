# Используем базовый образ Node.js
FROM node:14-alpine as build

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файлы package.json и package-lock.json
COPY package*.json ./

# Устанавливаем зависимости
RUN npm install

# Копируем исходный код проекта внутрь контейнера
COPY . .

# Собираем React приложение
RUN npm run build

# Новый этап сборки образа на основе Nginx
FROM nginx:1.21-alpine

# Копируем собранные статические файлы React приложения из предыдущего этапа
COPY --from=build /app/build /usr/share/nginx/html

# Устанавливаем команду запуска Nginx
CMD ["nginx", "-g", "daemon off;"]
