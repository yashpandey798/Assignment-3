import java.util.Arrays;
import java.util.List;

/**
 * Main class — Entry point of the Log File Parser program.
 *
 * Usage:
 *   java Main <filePath> [numberOfLines] [logTypes]
 *
 * Examples:
 *   java Main resources/Log_19_10_17_11_42_01.log
 *   java Main resources/Log_19_10_17_11_42_01.log 5
 *   java Main resources/Log_19_10_17_11_42_01.log 5 error,info
 *   java Main resources/Log_19_10_17_11_42_01.log 10 error,warning,info,debug
 */
public class Main {

    public static void main(String[] args) {

        // --- Default values ---
        String filePath;
        int numberOfLines = 10;                          // default 10
        List<String> logTypes = Arrays.asList("error"); // default error

        // --- Check minimum argument ---
        if (args.length < 1) {
            System.out.println("Usage: java Main <filePath> [numberOfLines] [logTypes]");
            System.out.println("Example: java Main resources/Log_19_10_17_11_42_01.log 5 error,info");
            return;
        }

        // --- Read arguments ---
        filePath = args[0];

        if (args.length >= 2) {
            try {
                numberOfLines = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid numberOfLines. Using default: 10");
            }
        }

        if (args.length >= 3) {
            String[] types = args[2].split(",");
            logTypes = Arrays.asList(types);
        }

        // --- Run Parser ---
        try {
            LogFileParser parser = new LogFileParser();
            List<LogEntry> results = parser.parse(filePath, numberOfLines, logTypes);

            System.out.println("=======================================================");
            System.out.println(" Log File Parser Results");
            System.out.println(" File      : " + filePath);
            System.out.println(" Lines     : " + numberOfLines);
            System.out.println(" Log Types : " + logTypes);
            System.out.println("=======================================================");

            if (results.isEmpty()) {
                System.out.println("No logs found for the given type(s).");
            } else {
                for (LogEntry entry : results) {
                    System.out.println(entry);
                }
            }

            System.out.println("=======================================================");
            System.out.println("Total results: " + results.size());

        } catch (InvalidFileException e) {
            System.out.println("FILE ERROR: " + e.getMessage());
        } catch (InvalidLogTypeException e) {
            System.out.println("LOG TYPE ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
