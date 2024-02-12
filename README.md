DevDungeon Project
==================
Собрать исполняемый файл war
```shell
mvn -f  pom.xml clean package
```
```shell
mvn -f  pom.xml clean package -D  maven.test.skip=true
```
Запустить приложение
```shell
docker-compose up
```
Приложение работает на
http://localhost:8080/help-service/

```shell
curl -i -X POST http://127.0.0.1:8080/help-service/v1/support -H 'Content-Type: application/json' -d '{"phrase":"All done !"}'
```
```shell
curl -i -X POST http://127.0.0.1:8080/help-service/v1/support -H 'Content-Type: application/json' -d '{"phrase":"+1!"}'
```
 ```shell
curl -i -X GET http://localhost:8080/help-service/v1/support  
```
Для добавления нового контроллера.
Контроллер размещаем в папке 'org.example.servlet;'
Помечаем анотацией @Controller(path = Const.another) где указываем ендпоинт
Добавляем в конфиг SupportConfiguration