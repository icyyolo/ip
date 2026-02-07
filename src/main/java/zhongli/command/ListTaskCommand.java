package zhongli.command;

import zhongli.gui.Dialogue;
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
    public void runGui(TaskList taskList, Dialogue dialogue, Storage storage) {
        assert dialogue != null : "gui is null";
        dialogue.addTaskList(taskList);
    }
}
