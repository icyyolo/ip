import java.util.*;

import Command.Command;
import Storage.Storage;
import Task.*;
import Ui.Ui;
import TaskList.TaskList;
import Parser.Parser;

public class Zhongli {

    private final static String filePath = ".taskstxt";
    private final Storage storage;
    private final Ui ui;
    private final TaskList taskList;

    public Zhongli() {
        ui = new Ui();
        Scanner input = new Scanner(System.in);
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
