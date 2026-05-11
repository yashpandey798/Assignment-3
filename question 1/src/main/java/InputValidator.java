import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Validates user inputs — file path and log types.
 */
public class InputValidator {

    // Only these 4 types are allowed
    private static final List<String> VALID_LOG_TYPES = Arrays.asList("error", "warning", "info", "debug");

    /**
     * Checks if the file path is valid and file exists.
     * Throws InvalidFileException if not.
     */
    public static void validateFilePath(String filePath) throws InvalidFileException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new InvalidFileException("File path cannot be empty.");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new InvalidFileException("File does not exist at path: " + filePath);
        }
        if (!file.isFile()) {
            throw new InvalidFileException("Given path is not a file: " + filePath);
        }
    }

    /**
     * Checks if all provided log types are valid.
     * Throws InvalidLogTypeException if any type is invalid.
     */
    public static void validateLogTypes(List<String> logTypes) throws InvalidLogTypeException {
        for (String type : logTypes) {
            if (!VALID_LOG_TYPES.contains(type.toLowerCase().trim())) {
                throw new InvalidLogTypeException(
                    "Invalid log type: '" + type + "'. Valid types are: error, warning, info, debug"
                );
            }
        }
    }
}
