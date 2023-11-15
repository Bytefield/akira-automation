package exceptions;

public class CustomExceptions {
    public static class BrowserNotFound extends Exception {
        public BrowserNotFound (String message) {
            super("BrowserNotFound Exception. " + message);
        }
    }

    public static class ElementNotFoundByLocatorsProvided extends Exception {
        public ElementNotFoundByLocatorsProvided (String message) {
            super("ElementNotFoundByLocatorsProvided. " + message);
        }
    }
}
