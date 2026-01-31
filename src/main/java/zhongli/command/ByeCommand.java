package zhongli.command;

import zhongli.storage.Storage;
import zhongli.taskList.TaskList;
import zhongli.ui.Ui;

/**
 * Represents a command to end interaction with the chatbot.
 * It will set the boolean variable, setIsExit, in Command class to false.
 *
 */
public class ByeCommand extends Command{

    public ByeCommand () {
        super();
    }

    @Override
    public void run(TaskList taskList, Ui ui, Storage storage) {
        super.setIsExit(true);
    }
}
