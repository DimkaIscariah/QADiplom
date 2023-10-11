**Дипломный проект профессии "Тестировщик"**

Тестируется приложение, являющийся определённым веб-сервисом, предлагающим приобрести тур двумя способами:

1) При помощи оплаты картой
2) Приобретение в кредит по данным банковской карты

Приложение сохраняет в собственной базе данных информацию, каким способом был произведён платёж и успешно ли он проведён.


**Работа с проектом**

Для работы с проектом на компьютере необходимо следующее ПО:

1) Git
2) Docker Desktop
3) IntelliJ IDEA
4) Google Chrome

**Запуск проекта и тестов**

1) Склонировать с GitHub проект на компьютер для запуска
2) В IntelliJ IDEA открыть склонированный проект
3) Из файла docker.compose.yml запустить контейнеры, используя в терминале команду docker-compose up
4) Используя Dockerfile запустить gate-simulator
5) Перед запуском приложения важно проверить, что в файлах build.gradle и application.properties активна необходимая база данных, другая соответственно должна быть неактивна. То есть, при работе с MYSQL необходимо закомментить базу Postgresql и наоборот.
6) Запускаем приложение в терминале при помощи команды:

• java -jar aqa-shop.jar -P:jdbc.url=jdbc:mysql://localhost:3306/mysql -P:jdbc.user=app -P:jdbc.password=pass (для работы с базой MYSQL)

• java -jar aqa-shop.jar -P:jdbc.url=jdbc:postgresql://localhost:5432/postgresql -P:jdbc.user=app -P:jdbc.password=pass (для работы с базой Postgresql)
7) В терминале ожидаем сообщения JVM Running, означающее, что приложение успешно запустилось
8) Производим запуск автотестов в терминале с помощью команды: ./gradlew clean test
9) По завершении тестов, используя Allure сформировать отчёт. Для этого в терминале применить команду: ./gradlew allureserve
Сам отчёт автоматически открывается в браузере.
10) Изучив отчёт, остановить работы сервисов с помощью клавиш CTRL+C.
11) Для перезапуска приложения, тестов и оформления нового отчёта повторить шаги 3-9.
12) Тестируемый веб-сервис доступен по адресу http://localhost:8080/

