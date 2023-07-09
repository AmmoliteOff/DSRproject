FROM adoptopenjdk:17-jdk-hotspot

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /src

# Копируем исходный код проекта внутрь контейнера
COPY src ./src

# Собираем проект с помощью Maven
RUN mvn -B package --file pom.xml

# Копируем собранный JAR-файл внутрь контейнера
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
