package Zhongli.Task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {

    @Test
    public void toString_success() {
        ToDo todo = new ToDo("test");
        assertEquals("[T][ ] test", todo.toString());
    }

    @Test
    public void markDone_success() {
        ToDo todo = new ToDo("test");
        todo.markDone();
        assertEquals("[T][X] test", todo.toString());
    }

    @Test
    public void markUndone_success() {
        ToDo todo = new ToDo("test");
        todo.markUndone();
        assertEquals("[T][ ] test", todo.toString());
    }

    @Test
    public void getDescription_success() {
        ToDo todo = new ToDo("test");
        assertEquals("test", todo.getDescription());
    }
}

