package zhongli.command;

import zhongli.gui.Dialogue;
import zhongli.storage.Storage;
import zhongli.tasklist.TaskList;

/**
 * Represents a command to display help information
 *
 */
public class HelpCommand extends Command {
    private static final String helpDescription =
            "Display all different commands";
    private static final String helpDialogue =
            "Here are all the different commands: \n\n";

    public HelpCommand() {
        super();
    }

    @Override
    public void runGui(TaskList taskList, Dialogue dialogue, Storage storage) {
        String helpMessage = helpDialogue + CommandType.getAllCommandsHelpMessage();
        dialogue.displayMessage(helpMessage);
    }

    public static String getHelpDescription() {
        return helpDescription;
    }
}
