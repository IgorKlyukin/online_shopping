# Реализация проекта интернет магазина "Breaking Bad"

Проект в первую очередь направлен на демонстрацию бэка, в связи с чем фронт не был оформлен через CSS.

### В проекте реализованы следующие фичи:
1) Главная страница
   1) Отображение пунктов меню зависит от авторизованности пользователя
   2) Поддержка i18n

2) Регистрация
   1) При регистрации пользователя автоматически авторизуется, пропуская шаг входа
   2) При регистрации осуществляется проверка на уникальность поля email

3) Авторизация (вход)
   1) Неавторизованный пользователь может взаимодействовать с каталогом и корзиной
   2) Только авторизованный пользователь может осуществить оформление заказа
   3) При авторизации корзина анонимного пользователя объединяется с корзиной авторизованного пользователя

4) Выход

5) Личный кабинет
   1) С возможностью редактирования имени, email, телефона
   2) Для поля email осуществляется проверка на уникальность

6) Каталог
   1) Иерархия категорий с привязанными продуктами
   2) Добавление товара в корзину
   3) Отображение на страницах каталога мини-корзины с количеством товара и итоговой суммой
   4) Для навигации по каталогу реализованы "Хлебные крошки"

7) Корзина
   1) Редактирование добавленных товаров в корзину: увеличение и уменьшение количества товара, удаление товара
   2) Оформление заказа

8) История заказов

9) Подробная информация о заказе

# Tech stack

### Back
1) Spring Boot 2
2) Spring Security
3) Spring Data Jpa
4) PostgreSQL (PL/SQL + CTE)
5) H2 (для тестирования)
6) Lombok
7) Stream
8) REST
9) JUnit 5
10) Mockito

### Front
1) Thymeleaf
2) HTML
3) jQuery

#### Для запуска ничего настраивать не требуется, просто запустите.

Для БД настроена автогенерация схемы. Для БД H2 и PostgreSQL автогенерация данных.
По умолчанию используется БД H2.

При использовании БД PostgreSQL задайте свои значения для url, name, password и установите 
```properties
    spring.datasource.platform=postgresql
```

в application.properties.

После первого использования БД PostgreSQL в application.properties закомментируйте автогенерацию данных 
```properties
    spring.datasource.initialization-mode=always
```


Для тестирования по умолчанию используется БД H2, параметр 
```properties
    spring.datasource.platform=h2
```


Постоянное изменение значений `spring.datasource.platform` связано с использование пользовательской функцией.
