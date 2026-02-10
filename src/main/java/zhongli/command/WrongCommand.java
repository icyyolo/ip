package zhongli.command;

import zhongli.gui.Dialogue;
import zhongli.storage.TaskStorage;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;

/**
 * Represents a wrong command is entered.
 * The ui will display an error message.
 *
 */
public class WrongCommand extends Command {
    private String userInput;

    /**
     * Represents a command that does not match the other commands.
     * When you call runGui(), it will display an error message.
     *
     */
    public WrongCommand(String userInput) {
        super();
        this.userInput = userInput;
    }

    @Override
    public void runGui(TaskList taskList, Dialogue dialogue, TaskStorage storage) {
        assert dialogue != null : "gui is null";
        dialogue.displayError(Ui.displayWrongCommandErrorMessage(this.userInput));
    }
}
