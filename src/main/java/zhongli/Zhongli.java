package zhongli;

import zhongli.alias.AliasList;
import zhongli.command.Command;
import zhongli.gui.Dialogue;
import zhongli.parser.Parser;
import zhongli.storage.TaskStorage;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;

/**
 * Represents a Zhongli class, which is the main class
 * to run the command line application
 *
 */
public class Zhongli {

    private static final String taskFilePath = ".taskstxt";
    private final TaskStorage taskStorage;
    private final Ui ui;
    private final TaskList taskList;
    private final AliasList aliasList;

    /**
     * Initialize the Ui, Storage and Tasklist object first.
     * Then, enter the main loop
     *
     */
    public Zhongli() {
        ui = new Ui();
        taskStorage = new TaskStorage(taskFilePath);
        taskList = this.taskStorage.initializeTaskList(ui);
        aliasList = new AliasList();
        assert taskList != null : "Tasklist should be not be null";
    }

    public void getGui(String input, Dialogue dialogue) {
        if (input.equals("clear")) {
            dialogue.clearChatbox();
        }
        Command command = Parser.parseCommand(input, aliasList);
        command.runGui(taskList, dialogue, taskStorage);
    }

    public static void main(String[] args) {
        new Zhongli();
    }
}
