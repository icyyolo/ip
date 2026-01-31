package zhongli.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
