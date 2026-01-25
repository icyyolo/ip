package Command;

import Storage.Storage;
import Task.Task;
import TaskList.TaskList;
import Ui.Ui;

public class ByeCommand extends Command{

    public ByeCommand () {
        super();
    }

    @Override
    public void run(TaskList taskList, Ui ui, Storage storage) {
        super.setIsExit(true);
    }
}
