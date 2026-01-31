package zhongli.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void toString_success() {
        Deadline deadline = new Deadline("test", LocalDate.parse("2022-10-15"));
        assertEquals("[D][ ] test (by: Oct 15 2022)", deadline.toString());
    }

    @Test
    public void markDone_toString_success() {
        Deadline deadline = new Deadline("test", LocalDate.parse("2022-10-15"));
        deadline.markDone();
        assertEquals("[D][X] test (by: Oct 15 2022)", deadline.toString());
    }

    @Test
    public void markUndone_toString_success() {
        Deadline deadline = new Deadline("test", LocalDate.parse("2022-10-15"));
        deadline.markUndone();
        assertEquals("[D][ ] test (by: Oct 15 2022)", deadline.toString());
    }

    @Test
    public void getDescription_success() {
        Deadline deadline = new Deadline("test", LocalDate.parse("2022-10-15"));
        assertEquals("test", deadline.getDescription());
    }

    @Test
    public void convertToText_success() {
        Deadline deadline = new Deadline("test", LocalDate.parse("2022-10-15"));
        assertEquals("deadline test/by 2022-10-15/unmark\n", deadline.convertToText());
    }

    @Test
    public void convertToText_markDone_success() {
        Deadline deadline = new Deadline("test", LocalDate.parse("2022-10-15"));
        deadline.markDone();
        assertEquals("deadline test/by 2022-10-15/mark\n", deadline.convertToText());
    }

    @Test
    public void convertToText_markUndone_success() {
        Deadline deadline = new Deadline("test", LocalDate.parse("2022-10-15"));
        deadline.markUndone();
        assertEquals("deadline test/by 2022-10-15/unmark\n", deadline.convertToText());
    }
}
