package zhongli.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import zhongli.task.Task;
import zhongli.zhongliexception.ZhongliException;

public class ParserTest {
    private String description = "test";
    private String startTime = "2025-10-20";
    private String endTime = "2026-11-20";

    @Test
    public void parseDate_success() {
        try {
            assertEquals(LocalDate.now(), Parser.parseDate("now"));
            assertEquals(LocalDate.parse("2020-06-10"), Parser.parseDate("2020-06-10"));
            assertEquals(LocalDate.parse("2030-06-10"), Parser.parseDate("2030-06-10"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parseDate_invalidFormat_exceptionThrown() {
        try {
            Parser.parseDate("2020/06/20");
        } catch (ZhongliException e) {
            assertEquals("Text '2020/06/20' could not be parsed at index 4\n"
                    + "Date Should be in this format YYYY-MM-DD", e.getMessage());
        } catch (Exception e) {
            fail();
        }

        try {
            Parser.parseDate("2020-06/20");
        } catch (ZhongliException e) {
            assertEquals("Text '2020-06/20' could not be parsed at index 7\n"
                    + "Date Should be in this format YYYY-MM-DD", e.getMessage());
        } catch (Exception e) {
            fail();
        }

        try {
            Parser.parseDate("2020 06-20");
        } catch (ZhongliException e) {
            assertEquals("Text '2020 06-20' could not be parsed at index 4\n"
                    + "Date Should be in this format YYYY-MM-DD", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parseDate_invalidDate_exceptionThrown() {
        try {
            Parser.parseDate("2020/00/20");
        } catch (ZhongliException e) {
            assertEquals("Text '2020/00/20' could not be parsed at index 4\n"
                    + "Date Should be in this format YYYY-MM-DD", e.getMessage());
        } catch (Exception e) {
            fail();
        }

        try {
            Parser.parseDate("2020/01/32");
        } catch (ZhongliException e) {
            assertEquals("Text '2020/01/32' could not be parsed at index 4\n"
                    + "Date Should be in this format YYYY-MM-DD", e.getMessage());
        } catch (Exception e) {
            fail();
        }

        try {
            Parser.parseDate("0000/01/32");
        } catch (ZhongliException e) {
            assertEquals("Text '0000/01/32' could not be parsed at index 4\n"
                    + "Date Should be in this format YYYY-MM-DD", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parseTaskFromInput_success() {
        try {
            Task todo = Parser.parseTaskFromInput("todo " + description);
            Task deadline = Parser.parseTaskFromInput("deadline "
                    + description + " /by" + startTime);
            Task event = Parser.parseTaskFromInput("event "
                    + description + " /from" + startTime + " /to" + endTime);
            assertEquals("[T][ ] test", todo.toString());
            assertEquals("[D][ ] test (by: Oct 20 2025)", deadline.toString());
            assertEquals("[E][ ] test (from: Oct 20 2025 to: Nov 20 2026)",
                    event.toString());
        } catch (Exception e) {
            fail();
        }
        try {
            Parser.parseTaskFromInput("task " + description);
            fail();
        } catch (ZhongliException e) {
            assertEquals("task type not found", e.getMessage());
        }
    }

    @Test
    public void parseTaskFromInput_invalidToDo_exceptionThrown() {
        try {
            Parser.parseTaskFromInput("todo");
            fail();
        } catch (ZhongliException e) {
            assertEquals("Description cannot be empty", e.getMessage());
        }
    }

    public void catchZhongliException(String input, String output) {
        try {
            Parser.parseTaskFromInput(input);
            fail();
        } catch (ZhongliException e) {
            assertEquals(output, e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parseTaskFromInput_invalidDeadline_exceptionThrown() {
        catchZhongliException("deadline", "Missing /by command");

        catchZhongliException("deadline /by", "Description cannot be empty");

        catchZhongliException("deadline /by abcd", "Description cannot be empty");

        String dateError = "Text 'abcd' could not be parsed at index 0\n" + "Date Should be in this format YYYY-MM-DD";
        catchZhongliException("deadline 1234 /by abcd", dateError);
    }

    @Test
    public void parseTaskFromInput_invalidEvent_exceptionThrown() {
        catchZhongliException("event", "Missing /from command");

        catchZhongliException("event /from", "Missing /to command");

        catchZhongliException("event /from /to", "Description cannot be empty");

        catchZhongliException("event 1234 /from /to", "Start Time cannot be empty");

        catchZhongliException("event 1234 /from now /to", "End Time cannot be empty");
    }
}
