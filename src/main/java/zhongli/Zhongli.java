package zhongli;

import zhongli.command.Command;
import zhongli.gui.Gui;
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
    }

    public void commandLineApplication() {
        ui.displayWelcomeMessage();
        runLoop();
        ui.displayGoodbyeMessage();
    }

    /**
     * Represents the main loop, where the chatbot interaction happens
     *
     */
    public void runLoop() {
        boolean isExitCommand = false;

        while (!isExitCommand) {
            String input = this.ui.readCommand();

            ui.printHorizontalLine();
            Command command = Parser.parseCommand(input);
            command.run(taskList, ui, storage);

            isExitCommand = command.getIsExit();
            if (isExitCommand) { //To avoid printing one more horizontal line
                break;
            }
            ui.printHorizontalLine();
        }
    }

    public String getGui(String input, Gui gui) {
        if (input.equals("clear")) {
            gui.clearChatbox();
            return "Cleared Screen";
        }
        Command command = Parser.parseCommand(input);
        return command.runGui(taskList, gui, storage);
    }

    public static void main(String[] args) {
        new Zhongli();
    }
}
