package Zhongli.Command;

import Zhongli.Storage.Storage;
import Zhongli.TaskList.TaskList;
import Zhongli.Ui.Ui;

/**
 * Represents a list task list command.
 *
 */
public class ListTaskCommand extends Command {

    public ListTaskCommand() {
        super();
    }

    @Override
    public void run(TaskList taskList, Ui ui, Storage storage) {
        ui.listTasksArray(taskList);
    }
}
