package tests;

import config.Config;
import exceptions.CustomExceptions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pom.CookiesPopupPage;
import utils.LocatorReader;

public class TestBase {

    public WebDriver driver;

    @BeforeClass
    public void setUp() throws CustomExceptions.BrowserNotFound {
        driver = Config.getDriver("chrome", true);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void checkAndHandleCookiesPopup() {
        CookiesPopupPage cookiesPopupPage = new CookiesPopupPage(driver);
        if (cookiesPopupPage.checkPopupDisplayed()) {
            cookiesPopupPage.closePopup();
        }
    }
}