package zhongli.command;

import zhongli.gui.Gui;
import zhongli.storage.Storage;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;

/**
 * Represents a command to end interaction with the chatbot.
 * It will set the boolean variable, setIsExit, in Command class to false.
 *
 */
public class ByeCommand extends Command {

    public ByeCommand() {
        super();
    }

    @Override
    public void runGui(TaskList taskList, Gui gui, Storage storage) {
        assert gui != null : "gui is null";
        gui.displayMessage("Bye lol");
    }
}
