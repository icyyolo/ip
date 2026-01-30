package Zhongli.Command;

import Zhongli.Storage.Storage;
import Zhongli.TaskList.TaskList;
import Zhongli.Ui.Ui;

/**
 * Represents a command to end interaction with the chatbot
 * It will set a boolean variable in Command class to false
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
