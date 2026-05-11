/**
 * Custom exception thrown when the provided file path is invalid or file does not exist.
 */
public class InvalidFileException extends Exception {

    public InvalidFileException(String message) {
        super(message);
    }
}
