# Hibernate task

### Общая информация
Выполнены задания 1-4. 

Работоспособность приложения проверяется через интеграционные тесты, с использованием testcontainers.
Корректность запросов (напр. наличие EntityGraph-а, правильность Criteria-запросов) так же 
проверяется через консольный вывод в интеграционных тестах.

### Инструция по запуску бд
При необходимости вызова некоторых методов в main методе, следует поднять БД-контейнер: 
```docker compose up```

