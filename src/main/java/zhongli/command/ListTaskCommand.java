package zhongli.command;

import zhongli.gui.Gui;
import zhongli.storage.Storage;
import zhongli.tasklist.TaskList;

/**
 * Represents a list task list command.
 *
 */
public class ListTaskCommand extends Command {

    public ListTaskCommand() {
        super();
    }

    @Override
    public void runGui(TaskList taskList, Gui gui, Storage storage) {
        assert gui != null : "gui is null";
        gui.addTaskList(taskList);
    }
}
