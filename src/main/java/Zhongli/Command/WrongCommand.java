package Zhongli.Command;

import Zhongli.Storage.Storage;
import Zhongli.TaskList.TaskList;
import Zhongli.Ui.Ui;

/**
 * Represents a wrong command is entered.
 * The ui will display an error message.
 * 
 */
public class WrongCommand extends Command{

    public WrongCommand() {
        super();
    }

    @Override
    public void run(TaskList taskList, Ui ui, Storage storage) {
        ui.displayExceptionMessage("This is a wrong command");
    }
}
