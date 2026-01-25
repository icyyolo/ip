package Command;

import Storage.Storage;
import Task.Task;
import TaskList.TaskList;
import Ui.Ui;

import java.io.IOException;

public class AddTaskCommand extends Command {

    private final Task task;
    private final TaskList taskList;
    private final Ui ui;
    private final Storage storage;

    public AddTaskCommand(Task task, TaskList taskList, Ui ui, Storage storage) {
        super();
        this.task = task;
        this.taskList = taskList;
        this.ui = ui;
        this.storage = storage;
    }

    @Override
    public void run() {
        taskList.addTask(task);
        try {
            this.storage.writeTaskListToFile(taskList);
        } catch (IOException e) {
            ui.displayExceptionMessage(e.getMessage());
        }
    }
}
