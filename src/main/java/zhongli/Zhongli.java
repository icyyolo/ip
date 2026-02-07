package zhongli;

import zhongli.command.Command;
import zhongli.gui.Dialogue;
import zhongli.parser.Parser;
import zhongli.storage.Storage;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;

/**
 * Represents a Zhongli class, which is the main class
 * to run the command line application
 *
 */
public class Zhongli {

    private static final String filePath = ".taskstxt";
    private final Storage storage;
    private final Ui ui;
    private final TaskList taskList;

    /**
     * Initialize the Ui, Storage and Tasklist object first.
     * Then, enter the main loop
     *
     */
    public Zhongli() {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = this.storage.initializeTaskList(ui);
        assert taskList != null : "Tasklist should be not be null";
    }

    public void getGui(String input, Dialogue dialogue) {
        if (input.equals("clear")) {
            dialogue.clearChatbox();
        }
        Command command = Parser.parseCommand(input);
        command.runGui(taskList, dialogue, storage);
    }

    public static void main(String[] args) {
        new Zhongli();
    }
}
