package zhongli;

import zhongli.alias.AliasList;
import zhongli.command.Command;
import zhongli.gui.Dialogue;
import zhongli.parser.Parser;
import zhongli.product.ProductList;
import zhongli.storage.AliasStorage;
import zhongli.storage.ProductStorage;
import zhongli.storage.TaskStorage;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;

/**
 * Represents the main application class for Zhongli, a command-line task management system.
 * Manages initialization of storage, user interface, and task/alias/product lists, and coordinates
 * command execution through the application lifecycle.
 *
 */
public class Zhongli {

    private static final String taskFilePath = ".taskstxt";
    private static final String aliasFilePath = ".aliasestxt";
    private static final String productsFilePath = ".productstxt";

    private final TaskStorage taskStorage;
    private final AliasStorage aliasStorage;
    private final ProductStorage productStorage;

    private final Ui ui;

    private final TaskList taskList;
    private final AliasList aliasList;
    private final ProductList productList;

    private boolean hasExit;


    /**
     * Initializes the UI, storage systems, and in-memory lists by loading
     * data from persistent storage files. Sets up the application ready for command processing.
     *
     */
    public Zhongli() {
        ui = new Ui();
        taskStorage = new TaskStorage(taskFilePath);
        aliasStorage = new AliasStorage(aliasFilePath);
        productStorage = new ProductStorage(productsFilePath);
        taskList = this.taskStorage.initializeTaskList(ui);
        aliasList = this.aliasStorage.initializeAliasList(ui);
        productList = this.productStorage.initializeProductList(ui);
        assert taskList != null : "Tasklist should be not be null";

        hasExit = false;
    }

    /**
     * Processes the given user input command and executes it through the graphical interface.
     * Clears the chat box before parsing if the input is "clear", then parses the command
     * and runs it with the current application state.
     *
     * @param input The raw user input string to be processed.
     * @param dialogue The dialogue interface component to display results and updates.
     */
    public void getGui(String input, Dialogue dialogue) {
        if (input.equals("clear")) {
            dialogue.clearChatbox();
        }
        Command command = Parser.parseCommand(input, aliasList, aliasStorage, productList, productStorage);
        command.runGui(taskList, dialogue, taskStorage);
        hasExit = command.getIsExit();
    }

    public boolean getHasExit() {
        return this.hasExit;
    }

    public static void main(String[] args) {
        new Zhongli();
    }
}
