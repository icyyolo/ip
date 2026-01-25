package Command;

import TaskList.TaskList;
import Ui.Ui;

public class ListTaskCommand extends Command {

    private final TaskList taskList;
    private final Ui ui;

    public ListTaskCommand(TaskList taskList, Ui ui) {
        super();
        this.taskList = taskList;
        this.ui = ui;
    }

    @Override
    public void run() {
        ui.listTasksArray(taskList);
    }
}
