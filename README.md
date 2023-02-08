Запуск из папки проекта:
docker-compose up

Порты:
Сервер: 5500
Клиент: 3000

### Перевод средств

POST http://localhost:8080/transfer
Content-Type: application/json

```json
{
  "cardFromNumber": "1111111111111111",
  "cardToNumber": "2222222222222222",
  "cardFromCVV": "111",
  "cardFromValidTill": "02/22",
  "amount": {
    "currency": "RUR",
    "value": 50000
  }
}
```

### Подтверждение оперции

POST http://localhost:8080/confirmOperation
Content-Type: application/json

```json
{
  "operationId": "1368019029",
  "code": "-1422195792"
}
```