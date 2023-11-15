package tests;

import org.testng.annotations.Test;
import pom.HomePage;

public class HomePageTest extends TestBase{

    @Test
    public void testHomePage() throws Exception {

        HomePage homePage = new HomePage(driver);
        homePage
                .testTitle()
                .enterTextInInputText("dirtyspaniard")
                .clickSubmitButton();
    }
}