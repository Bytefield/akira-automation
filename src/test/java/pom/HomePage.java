package pom;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import utils.ElementFinder;

public class HomePage {

    private WebDriver driver;
    private final ElementFinder elementFinder;

    private final String pageName = "HomePage";

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.elementFinder = new ElementFinder(driver);
    }

    public HomePage getGooglePage() {
        driver.get("https://www.google.com");
        return this;
    }

    public HomePage testTitle() {
        String title = driver.getTitle();
        Assert.assertEquals(title, "Google");
        return this;
    }

    public HomePage enterTextInInputText(String message) throws Exception {
        try {
            WebElement inputText = elementFinder.findElement(pageName, "inputText");
            inputText.sendKeys(message);
            return this;
        } catch (Exception e) {
            throw new NoSuchElementException("Input text not found");
        }
    }

    public ResultsPage clickSubmitButton() throws Exception {
        try {
            WebElement inputButton = elementFinder.findElement(pageName, "submitButton");
            inputButton.click();
            return new ResultsPage(driver);
        } catch (Exception e) {
            throw new NoSuchElementException("Submit button not found!");
        }
        // TODO: change method type to next page pom class
    }
}
