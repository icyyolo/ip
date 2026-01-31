package zhongli;

import zhongli.command.Command;
import zhongli.storage.Storage;
import zhongli.ui.Ui;
import zhongli.tasklist.TaskList;
import zhongli.parser.Parser;

public class Zhongli {

    private final static String filePath = ".taskstxt";
    private final Storage storage;
    private final Ui ui;
    private final TaskList taskList;

    public Zhongli() {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = this.storage.initializeTaskList(ui);

        ui.displayWelcomeMessage();

        runLoop();

        ui.displayGoodbyeMessage();
    }

    public void runLoop() {
        boolean isExitCommand = false;

        while (!isExitCommand) {
            String input = this.ui.readCommand();

            ui.printHorizontalLine();

            Command command = Parser.parseCommand(input);
            command.run(taskList, ui, storage);

            isExitCommand = command.getIsExit();
            if (isExitCommand) {    //To avoid printing one more horizontal line
                break;
            }
            
            ui.printHorizontalLine();
        }
    }

    public static void main(String[] args) {
        new Zhongli();
    }
}
