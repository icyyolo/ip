package Zhongli.Task;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    LocalDate startTime = LocalDate.parse("2022-10-15");
    LocalDate endTime = LocalDate.parse("2022-11-15");

    @Test
    public void toString_success() {
        Event event = new Event("test", startTime, endTime);
        assertEquals("[E][ ] test (from: Oct 15 2022 to: Nov 15 2022)", event.toString());
    }

    @Test
    public void markDone_toString_success() {
        Event event = new Event("test", startTime, endTime);
        event.markDone();
        assertEquals("[E][X] test (from: Oct 15 2022 to: Nov 15 2022)", event.toString());
    }

    @Test
    public void markUndone_toString_success() {
        Event event = new Event("test", startTime, endTime);
        event.markUndone();
        assertEquals("[E][ ] test (from: Oct 15 2022 to: Nov 15 2022)", event.toString());
    }

    @Test
    public void getDescription_success() {
        Event event = new Event("test", startTime, endTime);
        assertEquals("test", event.getDescription());
    }

    @Test
    public void convertToText_success() {
        Event event = new Event("test", startTime, endTime);
        assertEquals("event test/from 2022-10-15/to 2022-11-15/unmark\n", event.convertToText());
    }

    @Test
    public void convertToText_markDone_success() {
        Event event = new Event("test", startTime, endTime);
        event.markDone();
        assertEquals("event test/from 2022-10-15/to 2022-11-15/mark\n", event.convertToText());
    }

    @Test
    public void convertToText_markUndone_success() {
        Event event = new Event("test", startTime, endTime);
        event.markUndone();
        assertEquals("event test/from 2022-10-15/to 2022-11-15/unmark\n", event.convertToText());
    }
}
