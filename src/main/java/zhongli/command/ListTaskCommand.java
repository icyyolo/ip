package zhongli.command;

import zhongli.gui.Gui;
import zhongli.storage.Storage;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;

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

    @Override
    public void runGui(TaskList taskList, Gui gui, Storage storage) {
        gui.addTaskList(taskList);
    }
}
