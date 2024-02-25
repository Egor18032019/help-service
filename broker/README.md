Запустить все через докер

```shell
docker-compose up
```

Переменные прописаны в .env и application.properties
"kafka-t1:9092";//через общий докер
"localhost:29092";//в ручную
"172.19.0.2:9092";//через местный докер

| Порт              | Кейс                                |
|-------------------|-------------------------------------|    
| `kafka-t1:9092`   | *через общий докер*                 |
| `localhost:29092` | *в ручную*                          |
| `172.19.0.2:9092` | *через местный докер(не проверено)* |


```shell
curl -i -X GET http://localhost:8080/help-service/v1/support  
```

    Возвращает случайную фразу.

```shell
curl -i -X POST http://127.0.0.1:8080/help-service/v1/support -H 'Content-Type: application/json' -d '{"phrase":"All done !"}'
```

    Добавляет новую фразу в хранилище.

```shell
curl -i -X POST http://127.0.0.1:8080/help-service/v1/support -H 'Content-Type: application/json' -d '{"phrase":"+2!"}'
```

    Добавляет другую новую фразу в хранилище.

