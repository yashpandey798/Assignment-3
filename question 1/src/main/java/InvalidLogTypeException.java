/**
 * Custom exception thrown when an invalid log type is provided.
 * Valid types are: error, warning, info, debug
 */
public class InvalidLogTypeException extends Exception {

    public InvalidLogTypeException(String message) {
        super(message);
    }
}
