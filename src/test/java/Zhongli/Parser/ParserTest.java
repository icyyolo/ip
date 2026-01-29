package Zhongli.Parser;

import Zhongli.ZhongliException.ZhongliException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {

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
            assertEquals("Text '2020/06/20' could not be parsed at index 4\n" +
                    "Date Should be in this format YYYY-MM-DD", e.getMessage());
        } catch (Exception e) {
            fail();
        }

        try {
            Parser.parseDate("2020-06/20");
        } catch (ZhongliException e) {
            assertEquals("Text '2020-06/20' could not be parsed at index 7\n" +
                    "Date Should be in this format YYYY-MM-DD", e.getMessage());
        } catch (Exception e) {
            fail();
        }

        try {
            Parser.parseDate("2020 06-20");
        } catch (ZhongliException e) {
            assertEquals("Text '2020 06-20' could not be parsed at index 4\n" +
                    "Date Should be in this format YYYY-MM-DD", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void parseDate_invalidDate_exceptionThrown() {
        try {
            Parser.parseDate("2020/00/20");
        } catch (ZhongliException e) {
            assertEquals("Text '2020/00/20' could not be parsed at index 4\n" +
                    "Date Should be in this format YYYY-MM-DD", e.getMessage());
        } catch (Exception e) {
            fail();
        }

        try {
            Parser.parseDate("2020/01/32");
        } catch (ZhongliException e) {
            assertEquals("Text '2020/01/32' could not be parsed at index 4\n" +
                    "Date Should be in this format YYYY-MM-DD", e.getMessage());
        } catch (Exception e) {
            fail();
        }

        try {
            Parser.parseDate("0000/01/32");
        } catch (ZhongliException e) {
            assertEquals("Text '0000/01/32' could not be parsed at index 4\n" +
                    "Date Should be in this format YYYY-MM-DD", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }
}
