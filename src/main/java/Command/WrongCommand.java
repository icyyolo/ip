package Command;

import Storage.Storage;
import Task.Task;
import TaskList.TaskList;
import Ui.Ui;

public class WrongCommand extends Command{

    public WrongCommand() {
        super();
    }

    @Override
    public void run(TaskList taskList, Ui ui, Storage storage) {
        ui.displayExceptionMessage("This is a wrong command");
    }
}
