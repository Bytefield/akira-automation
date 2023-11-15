package utils;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocatorReader {

    private final JSONObject jsonObject;

    public LocatorReader(String jsonFilePath) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(jsonFilePath)) {
            if (inputStream == null) {
                throw new IllegalStateException("Locator file not found!");
            }
            String jsonText = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            jsonObject = new JSONObject(jsonText);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse locator file", e);
        }
    }

    public List<String> getLocator(String pageName, String locatorName) throws Exception {
        List<String> locators = new ArrayList<>();
        JSONObject locatorObject;
        try {
            locatorObject = jsonObject.getJSONObject(pageName).getJSONObject(locatorName);
        } catch (Exception e) {
            throw new Exception("Locator name provided not found in JSON", e);
        }

        if (locatorObject.length() > 0) {
            for (Iterator<String> it = locatorObject.keys(); it.hasNext(); ) {
                String key = it.next();
                String value = locatorObject.getString(key);
                String locator = key + ":" + value;
                locators.add(locator);
            }
        }

        return locators;
    }
}
