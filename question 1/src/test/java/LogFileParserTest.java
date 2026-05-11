import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

/**
 * JUnit test cases for LogFileParser
 */
public class LogFileParserTest {

    private LogFileParser parser;
    private String validFilePath = "resources/Log_19_10_17_11_42_01.log";

    @Before
    public void setUp() {
        parser = new LogFileParser();
    }

    // -------------------------------------------------------
    // TEST 1: Valid file + default type (error) + default lines
    // -------------------------------------------------------
    @Test
    public void testValidFileWithErrorType() throws Exception {
        List<String> types = Arrays.asList("error");
        List<LogEntry> results = parser.parse(validFilePath, 10, types);
        assertNotNull(results);
        assertTrue(results.size() <= 10);
        for (LogEntry entry : results) {
            assertEquals("error", entry.getLogType());
        }
    }

    // -------------------------------------------------------
    // TEST 2: Valid file + info type
    // -------------------------------------------------------
    @Test
    public void testValidFileWithInfoType() throws Exception {
        List<String> types = Arrays.asList("info");
        List<LogEntry> results = parser.parse(validFilePath, 5, types);
        assertNotNull(results);
        assertTrue(results.size() <= 5);
    }

    // -------------------------------------------------------
    // TEST 3: Multiple types — error and debug
    // -------------------------------------------------------
    @Test
    public void testMultipleLogTypes() throws Exception {
        List<String> types = Arrays.asList("error", "debug");
        List<LogEntry> results = parser.parse(validFilePath, 10, types);
        assertNotNull(results);
        for (LogEntry entry : results) {
            assertTrue(
                entry.getLogType().equals("error") || entry.getLogType().equals("debug")
            );
        }
    }

    // -------------------------------------------------------
    // TEST 4: Invalid file path → should throw InvalidFileException
    // -------------------------------------------------------
    @Test(expected = InvalidFileException.class)
    public void testInvalidFilePath() throws Exception {
        List<String> types = Arrays.asList("error");
        parser.parse("invalid/path/file.log", 10, types);
    }

    // -------------------------------------------------------
    // TEST 5: Invalid log type → should throw InvalidLogTypeException
    // -------------------------------------------------------
    @Test(expected = InvalidLogTypeException.class)
    public void testInvalidLogType() throws Exception {
        List<String> types = Arrays.asList("trace"); // invalid type
        parser.parse(validFilePath, 10, types);
    }

    // -------------------------------------------------------
    // TEST 6: numberOfLines = 0 → should return empty list
    // -------------------------------------------------------
    @Test
    public void testZeroNumberOfLines() throws Exception {
        List<String> types = Arrays.asList("info");
        List<LogEntry> results = parser.parse(validFilePath, 0, types);
        assertNotNull(results);
        assertEquals(0, results.size());
    }

    // -------------------------------------------------------
    // TEST 7: Results should be most recent (last line numbers first)
    // -------------------------------------------------------
    @Test
    public void testMostRecentLogsFirst() throws Exception {
        List<String> types = Arrays.asList("info");
        List<LogEntry> results = parser.parse(validFilePath, 5, types);
        if (results.size() > 1) {
            // First result should have higher line number than last
            assertTrue(results.get(0).getLineNumber() > results.get(results.size() - 1).getLineNumber());
        }
    }
}
