package com.simbirsoft.practicum.tests;

import com.simbirsoft.practicum.pages.FormFieldsPage;
import com.simbirsoft.practicum.utils.WebDriverFactory;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

@Epic("Автоматизация форм")
@Feature("Поля формы")
public class FormTest {
    private static final Logger logger = LoggerFactory.getLogger(FormTest.class);

    private WebDriver driver;
    private FormFieldsPage formPage;

    @BeforeMethod
    @Parameters({"headless"})
    @Step("Инициализация драйвера и переход на страницу формы")
    public void setUp(@Optional("false") String headless) {
        boolean isHeadless = Boolean.parseBoolean(headless);

        logger.info("Запуск теста в Chrome, headless режим: {}", isHeadless);

        driver = WebDriverFactory.createChromeDriver(isHeadless);
        driver.get("https://practice-automation.com/form-fields/");

        waitForPageLoad();

        formPage = new FormFieldsPage(driver);
        logger.info("Страница формы успешно загружена");
    }

    @Test
    @Story("Полное заполнение формы")
    @Description("Тестирование полного цикла заполнения формы с анализом инструментов автоматизации")
    @Severity(SeverityLevel.CRITICAL)
    public void completeFormSubmissionTest() {
        logger.info("Запуск теста полного заполнения формы");

        List<String> drinks = Arrays.asList("milk", "coffee");
        String toolsAnalysis = formPage.getToolsAnalysis();

        formPage.enterName("Anna")
                .enterPassword("mTZlqn37&#")
                .selectDrinks(drinks)
                .selectColor("yellow")
                .selectAutomationOption("yes")
                .enterEmail("tsybizova.anya@mail.ru")
                .enterMessage(toolsAnalysis)
                .clickSubmitButton();

        String alertText = formPage.getAlertText();

        logger.info("Текст уведомления: {}", alertText);

        Allure.addAttachment("Данные формы", "text/plain",
                "Имя: Anna\n" +
                        "Пароль: mTZlqn37&#\n" +
                        "Напитки: milk, coffee\n" +
                        "Цвет: yellow\n" +
                        "Отношение к автоматизации: yes\n" +
                        "Email: tsybizova.anya@mail.ru\n" +
                        "Сообщение: " + toolsAnalysis + "\n" +
                        "Текст уведомления: " + alertText);

        Assert.assertEquals(alertText, "Message received!",
                "Текст уведомления не соответствует ожидаемому");

        logger.info("Тест полного заполнения формы завершен успешно");
    }

    @Test
    @Story("Негативный сценарий - отправка пустой формы")
    @Description("Тестирование поведения системы при отправке формы без заполнения полей")
    @Severity(SeverityLevel.NORMAL)
    public void emptyFormSubmissionTest() {
        logger.info("Запуск теста отправки пустой формы");

        formPage.clickSubmitButton();

        boolean isAlertPresent = false;
        try {
            driver.switchTo().alert();
            isAlertPresent = true;
            logger.warn("Обнаружено неожиданное уведомление при отправке пустой формы");
        } catch (Exception e) {
            isAlertPresent = false;
            logger.info("Уведомление отсутствует - ожидаемое поведение");
        }

        Allure.addAttachment("Результат негативного теста", "text/plain",
                "Уведомление присутствует: " + isAlertPresent + "\n" +
                        "Ожидаемый результат: false (уведомление не должно появляться при отправке пустой формы)");

        Assert.assertFalse(isAlertPresent,
                "Уведомление не должно появляться при отправке пустой формы");

        logger.info("Тест отправки пустой формы завершен");
    }

    @AfterMethod
    @Step("Закрытие браузера")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Браузер закрыт");
        }
    }

    /**
     * Ожидание загрузки страницы
     */
    private void waitForPageLoad() {
        try {
            org.openqa.selenium.support.ui.WebDriverWait wait =
                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
            wait.until(webDriver ->
                    ((org.openqa.selenium.JavascriptExecutor) webDriver)
                            .executeScript("return document.readyState").equals("complete"));
        } catch (Exception e) {
            logger.warn("Страница не полностью загружена, но продолжаем выполнение");
        }
    }
}
