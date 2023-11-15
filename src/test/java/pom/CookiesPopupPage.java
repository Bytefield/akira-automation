package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ElementFinder;
import utils.LocatorReader;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class CookiesPopupPage {

    private static final int TIMEOUT_SECONDS = 15;
    private WebDriver driver;
    private ElementFinder elementFinder;
    private final String pageName = "CookiesPopupPage";

    public CookiesPopupPage(WebDriver driver) {
        this.driver = driver;
        this.elementFinder = new ElementFinder(driver);
    }

    public boolean checkPopupDisplayed() {
        try {
            WebElement cookiesPopup = elementFinder.findElement(pageName, "window");
            return cookiesPopup.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closePopup() {
        if (checkPopupDisplayed()) {
            try {
                WebElement rejectButton = elementFinder.findElement(pageName, "acceptAll");
                new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS))
                        .until(ExpectedConditions.elementToBeClickable(rejectButton));
                rejectButton.click();
            } catch (Exception e) {
                e.printStackTrace();
                // Handle the exception or rethrow it if you cannot recover from it
            }
        }
    }
}
