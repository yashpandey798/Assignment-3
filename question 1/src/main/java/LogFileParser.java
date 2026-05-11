import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Core class responsible for:
 * 1. Reading the log file
 * 2. Filtering logs by type
 * 3. Returning the most recent N log entries
 */
public class LogFileParser {

    /**
     * Parses the log file and returns most recent logs matching the given types.
     *
     * @param filePath      Path to the log file
     * @param numberOfLines Number of log lines to return (default 10)
     * @param logTypes      Types of logs to filter (default "error")
     * @return List of matching LogEntry objects (most recent first)
     * @throws InvalidFileException    if file path is invalid
     * @throws InvalidLogTypeException if log type is invalid
     * @throws IOException             if file reading fails
     */
    public List<LogEntry> parse(String filePath, int numberOfLines, List<String> logTypes)
            throws InvalidFileException, InvalidLogTypeException, IOException {

        // Step 1: Validate inputs
        InputValidator.validateFilePath(filePath);
        InputValidator.validateLogTypes(logTypes);

        // Step 2: Read all lines from file
        List<String> allLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    allLines.add(line);
                }
            }
        }

        // Step 3: Parse each line into LogEntry
        List<LogEntry> allEntries = new ArrayList<>();
        for (int i = 0; i < allLines.size(); i++) {
            String line = allLines.get(i);
            String detectedType = detectLogType(line);
            if (detectedType != null) {
                allEntries.add(new LogEntry(detectedType, line, i + 1));
            }
        }

        // Step 4: Filter by requested log types
        List<LogEntry> filteredEntries = new ArrayList<>();
        for (LogEntry entry : allEntries) {
            for (String type : logTypes) {
                if (entry.getLogType().equalsIgnoreCase(type.trim())) {
                    filteredEntries.add(entry);
                    break;
                }
            }
        }

        // Step 5: Reverse to get most recent first (from end of file)
        Collections.reverse(filteredEntries);

        // Step 6: Return only requested number of lines
        int limit = Math.min(numberOfLines, filteredEntries.size());
        return filteredEntries.subList(0, limit);
    }

    /**
     * Detects the log type from a log line.
     * Log file format: [INFO], [DEBUG], [ERROR], [WARNING]
     */
    private String detectLogType(String line) {
        String upperLine = line.toUpperCase();
        if (upperLine.contains("[ERROR]"))   return "error";
        if (upperLine.contains("[WARNING]")) return "warning";
        if (upperLine.contains("[INFO]"))    return "info";
        if (upperLine.contains("[DEBUG]"))   return "debug";
        return null; // not a recognized log line
    }
}
