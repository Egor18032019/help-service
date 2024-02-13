T1 Project
==================
Собрать исполняемый файл war
```shell
mvn -f  pom.xml clean package
```
Собрать исполняемый файл war без тестов
```shell
mvn -f  pom.xml clean package -D  maven.test.skip=true
```
Запустить докер контейнер(после сборки war)
```shell
docker-compose.yml up
```

или запустить приложение
```shell
docker-compose stop
mvn -f  pom.xml clean package
docker-compose up
```
для запуска полностью контейнере необходимо использовать [docker-compose-dev.yml](docker-compose-dev.yml)

Приложение работает на
http://localhost:8080/help-service/

Методы:

 ```shell
curl -i -X GET http://localhost:8080/help-service/v1/support  
```
    Возвращает случайную фразу.

```shell
curl -i -X POST http://127.0.0.1:8080/help-service/v1/support -H 'Content-Type: application/json' -d '{"phrase":"All done !"}'
```
    Добавляет новую фразу в хранилище.
```shell
curl -i -X POST http://127.0.0.1:8080/help-service/v1/support -H 'Content-Type: application/json' -d '{"phrase":"+1!"}'
```
    Добавляет другую новую фразу в хранилище.


Для добавления нового контроллера.
Контроллер размещаем в папке 'org.example.servlet;'
Помечаем аннотацией @Controller(path = Const.another) где указываем ендпоинт.
Добавляем в конфиг SupportConfiguration.