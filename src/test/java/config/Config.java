package config;

import exceptions.CustomExceptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class Config {

    public static WebDriver getDriver(String browser, boolean headless) throws CustomExceptions.BrowserNotFound {
        return switch (browser) {
            case ("chrome") -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) chromeOptions.addArguments("--headless");
                yield new ChromeDriver();
            }
            case ("firefox") -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) firefoxOptions.addArguments("--headless");
                yield new FirefoxDriver();
            }
            default -> throw new CustomExceptions.BrowserNotFound("Browser not provided or found!");
        };
    }
}
