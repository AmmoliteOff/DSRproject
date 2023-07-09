FROM node:14 as build
# Устанавливаем рабочую директорию
WORKDIR /app
# Копируем файлы package.json и package-lock.json для установки зависимостей
COPY package*.json ./
RUN npm install
# Копируем все файлы проекта в контейнер
COPY . .
# Собираем React приложение
RUN npm run build
FROM nginx:alpine
# Копируем собранные файлы из предыдущего этапа в папку с Nginx
COPY --from=build /app/build /usr/share/nginx/html
# Настраиваем конфигурацию Nginx
COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]
