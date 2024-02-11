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
curl -i -X POST http://localhost:8080/help-service/v1/support -H 'Content-Type: text/plain' -d 'All done !'
```
```shell
curl -i -X POST http://127.0.0.1:8080/help-service/v1/support -H 'Content-Type: application/json' -d '{"phrase" : "All done !"}'
```