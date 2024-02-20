 




```shell
curl -i -X GET http://localhost:8090/help-service/v1/support  
```
    Возвращает случайную фразу.

```shell
curl -i -X POST http://127.0.0.1:8090/help-service/v1/support -H 'Content-Type: application/json' -d '{"phrase":"All done !"}'
```
    Добавляет новую фразу в хранилище.
```shell
curl -i -X POST http://127.0.0.1:8090/help-service/v1/support -H 'Content-Type: application/json' -d '{"phrase":"+1!"}'
```
    Добавляет другую новую фразу в хранилище.

