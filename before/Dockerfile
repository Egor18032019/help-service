FROM maven:3.8.8-eclipse-temurin-17-alpine as builder
WORKDIR /project
#- создаем директорию app внутри слоя образа.
COPY . /project
#- копируем все наши папки с текущего проекта в папку app в слое образа.
RUN mvn -f  pom.xml clean package

#- Запускаем томкат
FROM tomcat:9.0
# Копируем WAR в Tomcat
COPY --from=builder /project/target/help-v1.war ./usr/local/tomcat/webapps/help-service.war
