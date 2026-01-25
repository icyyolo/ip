package Command;

import Storage.Storage;
import TaskList.TaskList;
import Ui.Ui;

public abstract class Command {
    private boolean isExit;

    public Command() {
        this.isExit = false;
    }

    public boolean getIsExit() {
        return isExit;
    }

    protected void setIsExit(boolean isExit) { this.isExit = isExit;}

    public abstract void run(TaskList taskList, Ui ui, Storage storage);
}
