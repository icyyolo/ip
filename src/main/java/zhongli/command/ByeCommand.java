package zhongli.command;

import zhongli.gui.Dialogue;
import zhongli.storage.TaskStorage;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;

/**
 * Represents a command to end interaction with the chatbot.
 * It will set the boolean variable, setIsExit, in Command class to false.
 *
 */
public class ByeCommand extends Command {
    private static final String helpDescription =
            "Exits the application";

    public ByeCommand() {
        super();
    }

    @Override
    public void runGui(TaskList taskList, Dialogue dialogue, TaskStorage storage) {
        assert dialogue != null : "gui is null";
        dialogue.displayMessage(Ui.displayGoodbyeMessage());
    }

    public static String getHelpDescription() {
        return helpDescription;
    }
}
