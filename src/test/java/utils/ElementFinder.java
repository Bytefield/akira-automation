package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Creates an array containing locator strings that can be used to find an element by CSS or XPath.
 */
public class ElementFinder {

    private final WebDriver driver;
    private static final Logger logger = Logger.getLogger(ElementFinder.class.getName());
    private static final String LOCATORS_JSON_FILE = "/home/papi/workspace/akira/src/test/java/utils/locators.json";
    private static final int WAIT_INTERVAL = 2000;
    private static final int ATTEMPTS = 3;
    private static final int MAX_WAIT_TIME = WAIT_INTERVAL * ATTEMPTS;

    public ElementFinder(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement findElement(String pageName, String locatorName) throws Exception {
        List<String> locators = getLocatorsFromJson(pageName, locatorName);

        for (String locator : locators) {
            String[] parts = locator.split(":", 2);
            String type = parts[0];
            String expression = parts[1];

            try {
                long startTime = System.currentTimeMillis();
                By by = getLocatorBy(type, expression);
                WebElement element = null;

                int attempt = 0;
                while (System.currentTimeMillis() - startTime <= MAX_WAIT_TIME) {
                    attempt++;
                    String logSuccessMessage = "\n";
                    String logErrorMessage = "\n";
                    try {
                        element = new WebDriverWait(driver, Duration.ofMillis(0))
                                .until(ExpectedConditions.visibilityOfElementLocated(by));
                        logSuccessMessage += ">   Attempt number (" + attempt + ")\n";
                        logSuccessMessage += ">>  Page name: " + pageName + ", locator name: " + locatorName + ".\n";
                        logSuccessMessage += ">>  Element found using locator: " + locator + "\n";
                        logger.warning(logSuccessMessage);
                        break; // If the element is found, exit the loop
                    } catch (org.openqa.selenium.TimeoutException e) {
                        logErrorMessage += ">   Attempt number (" + attempt + ")\n";
                        logErrorMessage += ">>  Page name: " + pageName + ", locator name: " + locatorName + ".\n";
                        logErrorMessage += ">>  Element not found using locator: " + locator + "\n";
                        logger.warning(logErrorMessage);
                    }
                    Thread.sleep(WAIT_INTERVAL);
                }

                if (element != null) {
                    return element;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted while waiting for element visibility", e);
            }
        }

        throw new NoSuchElementException("No element found for: " + pageName + " > " + locatorName + ".");
    }

    private List<String> getLocatorsFromJson(String pageName, String locatorName) {
        try {
            // Create an ObjectMapper to read JSON
            ObjectMapper objectMapper = new ObjectMapper();

            // Read the JSON file into a JsonNode
            JsonNode rootNode = objectMapper.readTree(new File(LOCATORS_JSON_FILE));

            // Ensure the JSON structure is valid and contains the expected properties
            if (!rootNode.has(pageName) || !rootNode.get(pageName).has(locatorName)) {
                throw new NoSuchElementException("Locator not found for: " + pageName + " > " + locatorName + ".");
            }

            // Get the locator JSON node for the specified pageName and locatorName
            JsonNode locatorData = rootNode.get(pageName).get(locatorName);

            // Iterate through the properties of the locatorData JSON node
            List<String> locators = new ArrayList<>();
            Iterator<String> fieldNames = locatorData.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                String locatorValue = locatorData.get(fieldName).asText();
                String locator = fieldName + ":" + locatorValue;
                locators.add(locator);
            }

            return locators;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }

    private By getLocatorBy(String type, String expression) {
        return switch (type) {
            case "css" -> By.cssSelector(expression);
            case "xpath" -> By.xpath(expression);
            // Add more cases for other locator types if necessary
            default -> throw new IllegalArgumentException("Unsupported locator type: " + type);
        };
    }
}
