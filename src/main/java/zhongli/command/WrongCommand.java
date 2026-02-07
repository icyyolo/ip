package zhongli.command;

import zhongli.gui.Gui;
import zhongli.storage.Storage;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;

/**
 * Represents a wrong command is entered.
 * The ui will display an error message.
 *
 */
public class WrongCommand extends Command {
    private String userInput;

    public WrongCommand(String userInput) {
        super();
        this.userInput = userInput;
    }

    @Override
    public void run(TaskList taskList, Ui ui, Storage storage) {
        ui.displayExceptionMessage("This is a wrong command");
    }

    @Override
    public void runGui(TaskList taskList, Gui gui, Storage storage) {
        assert gui != null : "gui is null";
        gui.displayError("The previous command [" + userInput + "] is not a correct input.");
    }
}
