package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pom.HomePage;
import pom.ResultsPage;

public class MainTest extends TestBase {

    @Test
    public void mainTest() throws Exception {

        driver.get("https://www.google.com");

        checkAndHandleCookiesPopup();

        HomePage homePage = new HomePage(driver);
        ResultsPage resultsPage = new ResultsPage(driver);

        homePage
                .testTitle()
                .enterTextInInputText("DirtySpaniard")
                .clickSubmitButton();
        resultsPage
                .checkUrl()
                .getSearchResults();
    }
}
