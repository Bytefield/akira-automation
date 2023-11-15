package tests;

import org.testng.TestNG;

import java.util.Arrays;
import java.util.List;

public class TestRunner {

    public static void main(String[] args) {
        TestNG testng = new TestNG();
        List<String> testSuites = List.of("src/test/java/tests/test-suite.xml");
        testng.setTestSuites(testSuites);
        testng.run();
    }
}
