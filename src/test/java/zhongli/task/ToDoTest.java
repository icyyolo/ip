package zhongli.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {

    @Test
    public void toString_success() {
        ToDo todo = new ToDo("test");
        assertEquals("[T][ ] test", todo.toString());
    }

    @Test
    public void markDone_toString_success() {
        ToDo todo = new ToDo("test");
        todo.markDone();
        assertEquals("[T][X] test", todo.toString());
    }

    @Test
    public void markUndone_toString_success() {
        ToDo todo = new ToDo("test");
        todo.markUndone();
        assertEquals("[T][ ] test", todo.toString());
    }

    @Test
    public void getDescription_success() {
        ToDo todo = new ToDo("test");
        assertEquals("test", todo.getDescription());
    }

    @Test
    public void convertToText_success() {
        ToDo todo = new ToDo("test");
        assertEquals("todo test/unmark\n", todo.convertToText());
    }

    @Test
    public void convertToText_markDone_success() {
        ToDo todo = new ToDo("test");
        todo.markDone();
        assertEquals("todo test/mark\n", todo.convertToText());
    }

    @Test
    public void convertToText_markUndone_success() {
        ToDo todo = new ToDo("test");
        todo.markUndone();
        assertEquals("todo test/unmark\n", todo.convertToText());
    }
}

