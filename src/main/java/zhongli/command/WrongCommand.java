package zhongli.command;

import zhongli.gui.Dialogue;
import zhongli.storage.Storage;
import zhongli.tasklist.TaskList;

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
    public void runGui(TaskList taskList, Dialogue dialogue, Storage storage) {
        assert dialogue != null : "gui is null";
        dialogue.displayError("The previous command [" + userInput + "] is not a correct input.");
    }
}
