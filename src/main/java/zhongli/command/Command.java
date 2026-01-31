package zhongli.command;

import zhongli.storage.Storage;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;

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
    public abstract void run(TaskList taskList, Ui ui, Storage storage);
}
