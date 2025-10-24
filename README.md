# SDET Practicum 2025 - Test Automation Project

![Java](https://img.shields.io/badge/Java-17-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.36.0-green)
![TestNG](https://img.shields.io/badge/TestNG-7.8.0-red)
![Maven](https://img.shields.io/badge/Maven-3.8.1-blue)
![Allure](https://img.shields.io/badge/Allure-2.24.0-purple)

## 📋 О проекте 

Проект автоматизации тестирования UI для формы на сайте practice-automation.com. Реализован в рамках тестового задания SDET-практикума от компании SimbirSoft.

## 🎯 Выполненные требования

- ✅ **Java 17** - использована рекомендованная версия
- ✅ **Selenium WebDriver + Chrome** - автоматизация браузера
- ✅ **TestNG** - фреймворк для тестирования
- ✅ **Maven** - система сборки
- ✅ **Page Object Model** - паттерн проектирования
- ✅ **Page Factory** - инициализация элементов
- ✅ **Fluent Interface** - цепочечные вызовы методов
- ✅ **CSS, XPath, ID селекторы** - все типы локаторов
- ✅ **Allure отчеты** - детальная отчетность
- ✅ **Позитивный и негативный тесты** - полное покрытие

## 🛠 Технологии

- **Java 17**
- **Selenium WebDriver 4.36.0**
- **TestNG 7.8.0**
- **Allure 2.24.0**
- **Maven**
- **WebDriverManager** - автоматическое управление драйверами
- **SLF4J** - логирование

## 📁 Структура проекта
practicumOctTZ/
├── screenshots/ # Скриншоты отчетов Allure
├── src/
│ ├── main/java/com/simbirsoft/practicum/
│ │ ├── pages/
│ │ │ └── FormFieldsPage.java # Page Object формы
│ │ └── utils/
│ │ └── WebDriverFactory.java # Фабрика WebDriver
│ └── test/java/com/simbirsoft/practicum/tests/
│ └── FormTest.java # Тестовый класс
├── src/test/resources/
│ └── testng.xml # Конфигурация TestNG
├── .gitignore
├── pom.xml # Конфигурация Maven
└── README.md

## 🚀 Быстрый старт

### Предварительные требования
- Java 17 или выше
- Maven 3.6 или выше
- Chrome браузер

### Запуск тестов

```bash
# Клонировать репозиторий
git clone https://github.com/stray322/practicumOctTZ.git
cd practicumOctTZ

# Запустить тесты
mvn clean test

# Сгенерировать и открыть Allure отчет
mvn allure:serve
```

## 📊 Результаты тестирования
### Статистика выполнения
Всего тестов: 2

Успешных: 2

Успешность: 100%

Общее время: 31s 467ms

## Скриншоты отчетов
Общий обзор выполнения тестов
https://screenshots/allure-overview.png 

Детали успешного заполнения формы
https://screenshots/allure-positive-test.png
https://screenshots/allure-positive-test(2).png

Детали отправки пустой формы
https://screenshots/allure-negative-test.png

## 📝 Тест-кейсы

### Позитивный тест-кейс

**TC-001: Успешное заполнение и отправка формы**

- **Предусловие:** Открыт браузер на странице https://practice-automation.com/form-fields/
- **Шаги:**
    1. Заполнить поле Name: "Anna"
    2. Заполнить поле Password: "mTZlqn37&#"
    3. Выбрать напитки: Milk, Coffee
    4. Выбрать цвет: Yellow
    5. Выбрать отношение к автоматизации: Yes
    6. Заполнить Email: "tsybizova.anya@mail.ru"
    7. Заполнить Message анализом инструментов
    8. Нажать кнопку Submit
- **Ожидаемый результат:** Появляется алерт с текстом "Message received!"

### Негативный тест-кейс

**TC-002: Отправка пустой формы**

- **Предусловие:** Открыт браузер на странице https://practice-automation.com/form-fields/
- **Шаги:**
    1. Оставить все поля пустыми
    2. Нажать кнопку Submit
- **Ожидаемый результат:** Алерт не появляется

## 🔧 Ключевые особенности
### Page Object Pattern

java
public FormFieldsPage enterName(String name) {
    wait.until(ExpectedConditions.visibilityOf(nameField)).clear();
    nameField.sendKeys(name);
    return this; // Fluent Interface
}

## Обработка исключений
- Резервные локаторы для повышения надежности
- Явные ожидания вместо Thread.sleep
- JavaScript клики для стабильности

## Allure интеграция
- Детальные шаги тестов
- Параметры тестов
- Скриншоты и аттачменты

## 👥 Автор
Анна Солянникова

GitHub: stray322

Email: tsybizova.anya@mail.ru

## 📄 Лицензия
Этот проект создан в рамках тестового задания SDET-практикума от SimbirSoft.
