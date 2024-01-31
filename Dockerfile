FROM maven:3.8.8-eclipse-temurin-17-alpine as builder
WORKDIR /project
#- создаем директорию app внутри слоя образа.
COPY . /project
#- копируем все наши папки с текущего проекта в папку app в слое образа.
RUN mvn -f /project/pom.xml clean package -D  maven.test.skip=true

# - запускаем maven, который билдит наш проект и получаем jar-ник.
FROM eclipse-temurin:17-jre-alpine
#снова указываем на основании какого образа, мы будем запускать наш проект, здесь уже мы не используем jdk, а только jre - так как нам не нужны инструменты разработчика.
WORKDIR /app
# создаем директорию app в новом слое образа.
COPY --from=builder /project/target/*.war ./app.war
#- копируем с предыдущего слоя с папки target наш jar-ник в папку app.
#RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
#RUN chown -R javauser:javauser /app
#USER javauser
#EXPOSE 8080
# - указываем на каком порту должен работать наш контейнер
#CMD ["java", "-war", "app.war"]
#- запускаем наше приложение в контейнере.
