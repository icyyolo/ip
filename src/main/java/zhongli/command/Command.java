package zhongli.command;

import zhongli.gui.Dialogue;
import zhongli.storage.TaskStorage;
import zhongli.tasklist.TaskList;

/**
 * Abstract base class for all executable commands in the application.
 * Defines the contract for command execution with task list, dialogue display, and storage operations.
 * Tracks whether the command signals application exit.
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
     * Executes the command with the specified task list, dialogue interface, and storage system.
     * Implementations must define the specific behavior for each command type.
     *
     * @param taskList The TaskList to operate on.
     * @param dialogue The Dialogue interface for displaying results and messages to the user.
     * @param storage The TaskStorage system for persisting task changes.
     */
    public abstract void runGui(TaskList taskList, Dialogue dialogue, TaskStorage storage);
}
