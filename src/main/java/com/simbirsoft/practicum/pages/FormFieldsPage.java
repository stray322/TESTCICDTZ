package com.simbirsoft.practicum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

/**
 * Page Object класс для работы с элементами страницы формы.
 * Реализует паттерны Page Object Model, Page Factory и Fluent Interface.
 */
public class FormFieldsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    /**
     * Локатор поля ввода имени с использованием ID селектора
     */
    @FindBy(id = "name-input")
    private WebElement nameField;

    /**
     * Локатор поля ввода пароля с использованием ID селектора
     */
    @FindBy(id = "password")
    private WebElement passwordField;

    /**
     * Локатор чекбокса "Milk" с использованием CSS селектора
     */
    @FindBy(css = "input[value='Milk']")
    private WebElement milkCheckbox;

    /**
     * Локатор чекбокса "Coffee" с использованием CSS селектора
     */
    @FindBy(css = "input[value='Coffee']")
    private WebElement coffeeCheckbox;

    /**
     * Локатор радио-кнопки "Yellow" с использованием CSS селектора
     */
    @FindBy(css = "input[value='Yellow']")
    private WebElement yellowRadio;

    /**
     * Локатор выпадающего списка автоматизации с использованием ID селектора
     */
    @FindBy(id = "automation")
    private WebElement automationDropdown;

    /**
     * Локатор поля ввода email с использованием ID селектора
     */
    @FindBy(id = "email")
    private WebElement emailField;

    /**
     * Локатор поля ввода сообщения с использованием ID селектора
     */
    @FindBy(id = "message")
    private WebElement messageField;

    /**
     * Локатор списка элементов инструментов автоматизации с использованием XPath селектора
     */
    @FindBy(xpath = "//label[contains(text(), 'Automation tools')]/following-sibling::ul/li")
    private List<WebElement> listItems;

    /**
     * Локатор кнопки отправки формы с использованием CSS селектора
     */
    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    /**
     * Конструктор класса FormFieldsPage
     *
     * @param driver экземпляр WebDriver для управления браузером
     */
    public FormFieldsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Вводит имя в соответствующее поле формы
     *
     * @param name имя для ввода
     * @return this для реализации Fluent Interface
     */
    @Step("Ввод имени '{name}' в поле 'Name'")
    public FormFieldsPage enterName(String name) {

        wait.until(ExpectedConditions.urlContains("form-fields"));

        try {
            wait.until(ExpectedConditions.visibilityOf(nameField)).clear();
            nameField.sendKeys(name);
        } catch (Exception e) {
            WebElement nameFieldAlt = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@placeholder='Name' or @name='name']")));
            nameFieldAlt.clear();
            nameFieldAlt.sendKeys(name);
        }
        return this;
    }

    /**
     * Вводит пароль в соответствующее поле формы
     *
     * @param password пароль для ввода
     * @return this для реализации Fluent Interface
     */
    @Step("Ввод пароля в поле 'Password'")
    public FormFieldsPage enterPassword(String password) {
        try {
            wait.until(ExpectedConditions.visibilityOf(passwordField)).clear();
            passwordField.sendKeys(password);
        } catch (Exception e) {
            WebElement passwordFieldAlt = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@type='password']")));
            passwordFieldAlt.clear();
            passwordFieldAlt.sendKeys(password);
        }
        return this;
    }

    /**
     * Выбирает указанные напитки из чекбоксов
     *
     * @param drinks список напитков для выбора
     * @return this для реализации Fluent Interface
     */
    @Step("Выбор чекбоксов: Milk и Coffee")
    public FormFieldsPage selectDrinks(List<String> drinks) {
        for (String drink : drinks) {
            if ("milk".equalsIgnoreCase(drink)) {
                clickWithRetry(milkCheckbox, "input[value='Milk']");
            } else if ("coffee".equalsIgnoreCase(drink)) {
                clickWithRetry(coffeeCheckbox, "input[value='Coffee']");
            }
        }
        return this;
    }

    /**
     * Выбирает цвет из радио-кнопок
     *
     * @param color цвет для выбора
     * @return this для реализации Fluent Interface
     */
    @Step("Выбор радиокнопки цвета 'Yellow'")
    public FormFieldsPage selectColor(String color) {
        if ("yellow".equalsIgnoreCase(color)) {
            clickWithRetry(yellowRadio, "input[value='Yellow']");
        }
        return this;
    }

    /**
     * Выбирает опцию из выпадающего списка автоматизации
     *
     * @param value значение для выбора
     * @return this для реализации Fluent Interface
     */
    @Step("Выбор опции '{value}' из выпадающего списка 'Automation'")
    public FormFieldsPage selectAutomationOption(String value) {
        try {
            wait.until(ExpectedConditions.visibilityOf(automationDropdown));
            Select select = new Select(automationDropdown);
            select.selectByValue(value.toLowerCase());
        } catch (Exception e) {
            WebElement dropdownAlt = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//select[contains(@name, 'automation') or contains(@id, 'automation')]")));
            Select select = new Select(dropdownAlt);
            select.selectByValue(value.toLowerCase());
        }
        return this;
    }

    /**
     * Вводит email в соответствующее поле формы
     *
     * @param email email для ввода
     * @return this для реализации Fluent Interface
     */
    @Step("Ввод email '{email}' в поле 'Email'")
    public FormFieldsPage enterEmail(String email) {
        try {
            wait.until(ExpectedConditions.visibilityOf(emailField)).clear();
            emailField.sendKeys(email);
        } catch (Exception e) {
            WebElement emailFieldAlt = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@type='email']")));
            emailFieldAlt.clear();
            emailFieldAlt.sendKeys(email);
        }
        return this;
    }

    /**
     * Вводит сообщение в соответствующее поле формы
     *
     * @param message сообщение для ввода
     * @return this для реализации Fluent Interface
     */
    @Step("Ввод сообщения в поле 'Message'")
    public FormFieldsPage enterMessage(String message) {
        try {
            wait.until(ExpectedConditions.visibilityOf(messageField)).clear();
            messageField.sendKeys(message);
        } catch (Exception e) {
            WebElement messageFieldAlt = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//textarea[@id='message' or @name='message']")));
            messageFieldAlt.clear();
            messageFieldAlt.sendKeys(message);
        }
        return this;
    }

    /**
     * Анализирует список инструментов автоматизации и возвращает информацию о количестве и самом длинном инструменте
     *
     * @return строка с количеством инструментов и названием самого длинного инструмента
     */
    @Step("Получение информации о списке инструментов")
    public String getToolsAnalysis() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(listItems));

            if (listItems.isEmpty()) {
                List<WebElement> altListItems = driver.findElements(
                        By.xpath("//*[contains(text(), 'Automation tools')]//following-sibling::ul/li"));

                if (altListItems.isEmpty()) {
                    return "Количество инструментов: 0, Самый длинный инструмент: ";
                }

                int toolsCount = altListItems.size();
                String longestTool = altListItems.stream()
                        .map(WebElement::getText)
                        .max(Comparator.comparingInt(String::length))
                        .orElse("");

                return String.format("Количество инструментов: %d, Самый длинный инструмент: %s", toolsCount, longestTool);
            }

            int toolsCount = listItems.size();
            String longestTool = listItems.stream()
                    .map(WebElement::getText)
                    .max(Comparator.comparingInt(String::length))
                    .orElse("");

            return String.format("Количество инструментов: %d, Самый длинный инструмент: %s", toolsCount, longestTool);
        } catch (Exception e) {
            return "Количество инструментов: 0, Самый длинный инструмент: ";
        }
    }

    /**
     * Нажимает на кнопку отправки формы
     *
     * @return this для реализации Fluent Interface
     */
    @Step("Нажатие на кнопку 'Submit'")
    public FormFieldsPage clickSubmitButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(submitButton));
            js.executeScript("arguments[0].click();", submitButton);
        } catch (Exception e) {
            WebElement submitButtonAlt = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(), 'Submit')]")));
            js.executeScript("arguments[0].click();", submitButtonAlt);
        }
        return this;
    }

    /**
     * Получает текст всплывающего уведомления после отправки формы
     *
     * @return текст уведомления или сообщение об его отсутствии
     */
    @Step("Получение текста алерта")
    public String getAlertText() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            String alertText = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
            return alertText;
        } catch (Exception e) {
            return "Алерт не найден";
        }
    }

    /**
     * Универсальный метод для выполнения клика с обработкой исключений и повторными попытками
     *
     * @param element веб-элемент для клика
     * @param cssSelector CSS селектор для альтернативного поиска элемента
     */
    private void clickWithRetry(WebElement element, String cssSelector) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            scrollToElement(element);
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            WebElement altElement = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(cssSelector)));
            scrollToElement(altElement);
            js.executeScript("arguments[0].click();", altElement);
        }
    }

    /**
     * Прокручивает страницу к указанному элементу
     *
     * @param element веб-элемент, к которому нужно прокрутить страницу
     */
    private void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", element);
    }
}
