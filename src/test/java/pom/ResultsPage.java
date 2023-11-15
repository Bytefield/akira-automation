package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.ElementFinder;
import java.util.logging.Logger;

import java.util.List;

public class ResultsPage {

    private WebDriver driver;
    private final ElementFinder elementFinder;
    private static final Logger logger = Logger.getLogger(ElementFinder.class.getName());

    private final String pageName = "ResultsPage";

    public ResultsPage(WebDriver driver) throws Exception {
        this.driver = driver;
        this.elementFinder = new ElementFinder(driver);
    }

    public ResultsPage checkUrl() {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/search?"), "Wrong url, skipping!");
        return this;
    }

    public ResultsPage getSearchResults() throws Exception {
        WebElement searchResultsContainer = elementFinder.findElement(pageName, "resultsContainer");
        List<WebElement> firstChildDivs = searchResultsContainer.findElements(By.xpath(".//div[1]"));

        for (WebElement firstChildDiv : firstChildDivs) {
            logger.warning(firstChildDiv.getText());
        }
        return this;
    }
}
