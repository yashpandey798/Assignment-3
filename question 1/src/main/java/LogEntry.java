/**
 * Model class representing a single log entry.
 */
public class LogEntry {

    private String logType;    // error, warning, info, debug
    private String message;    // full log line
    private int lineNumber;    // line number in file

    public LogEntry(String logType, String message, int lineNumber) {
        this.logType = logType;
        this.message = message;
        this.lineNumber = lineNumber;
    }

    public String getLogType() {
        return logType;
    }

    public String getMessage() {
        return message;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return "[Line " + lineNumber + "] " + message;
    }
}
