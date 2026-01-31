package zhongli.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;


public class TaskTest {
    @Test
    public void getStringIsDone_newTask_success() {
        Task todo = new ToDo("test");
        assertEquals("/unmark", todo.getStringIsDone());
    }

    @Test
    public void getStringIsDone_markDone_success() {
        Task todo = new ToDo("test");
        todo.markDone();
        assertEquals("/mark", todo.getStringIsDone());
    }

    @Test
    public void getStringIsDone_markUndone_success() {
        Task todo = new ToDo("test");
        todo.markUndone();
        assertEquals("/unmark", todo.getStringIsDone());
    }

    @Test
    public void formatDate_correctFormat_success() {
        Task todo = new ToDo("test");
        assertEquals("Oct 15 2022", todo.formatDate(LocalDate.parse("2022-10-15")));
    }
}
