package com.simbirsoft.practicum.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Фабрика для создания экземпляров WebDriver
 */
public class WebDriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);

    /**
     * Создает экземпляр ChromeDriver с указанными настройками
     *
     * @param headless запуск в headless режиме
     * @return настроенный экземпляр WebDriver
     */
    public static WebDriver createChromeDriver(boolean headless) {
        try {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            if (headless) {
                options.addArguments("--headless=new");
            }
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            // Добавляем аргументы для стабильности в CI
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");

            WebDriver driver = new ChromeDriver(options);

            // Общие настройки драйвера
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

            logger.info("Создан ChromeDriver (headless: {})", headless);
            return driver;

        } catch (Exception e) {
            logger.error("Ошибка при создании ChromeDriver", e);
            throw new RuntimeException("Не удалось создать ChromeDriver", e);
        }
    }

    /**
     * Создает экземпляр ChromeDriver с автоматическим определением режима из системных свойств
     *
     * @return настроенный экземпляр WebDriver
     */
    public static WebDriver createChromeDriver() {
        // Чтение системного свойства 'headless' (по умолчанию false)
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        return createChromeDriver(isHeadless);
    }

    /**
     * Создает экземпляр ChromeDriver для CI/CD окружения
     *
     * @return настроенный экземпляр WebDriver
     */
    public static WebDriver createChromeDriverForCI() {
        // Для CI/CD всегда используем headless режим
        return createChromeDriver(true);
    }
}
