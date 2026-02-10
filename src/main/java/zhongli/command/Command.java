package zhongli.command;

import zhongli.gui.Dialogue;
import zhongli.storage.TaskStorage;
import zhongli.tasklist.TaskList;

/**
 * Represents a command class.
 *
 */
public abstract class Command {
    private boolean isExit;

    public Command() {
        this.isExit = false;
    }

    public boolean getIsExit() {
        return isExit;
    }

    protected void setIsExit(boolean isExit) {
        this.isExit = isExit;
    }

    /**
     * Runs the current command with the given parameters.
     *
     */
    public abstract void runGui(TaskList taskList, Dialogue dialogue, TaskStorage storage);
}
