package tests;

import org.testng.annotations.Test;
import pom.ResultsPage;

public class ResultsPageTest extends TestBase{

    @Test
    public void testResultsPage() throws Exception {

        ResultsPage resultsPage = new ResultsPage(driver);
        resultsPage
                .checkUrl();
    }

}
