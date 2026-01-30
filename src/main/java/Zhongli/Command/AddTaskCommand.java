package Zhongli.Command;

import Zhongli.Parser.Parser;
import Zhongli.Storage.Storage;
import Zhongli.Task.Task;
import Zhongli.TaskList.TaskList;
import Zhongli.Ui.Ui;
import Zhongli.ZhongliException.ZhongliException;

import java.io.IOException;

/**
 * Represents an Add Task to tasklist command.
 * If the task is valid and has been successfully added to the text file,
 * the ui will display a success message.
 * Else the ui will display an error message
 *
 */
public class AddTaskCommand extends Command {
    private String userInput;

    public AddTaskCommand(String userInput) {
        super();
        this.userInput = userInput;
    }

    @Override
    public void run(TaskList taskList, Ui ui, Storage storage) {
        try {
            Task task = Parser.parseTaskFromInput(userInput);
            taskList.addTask(task);
            storage.writeTaskListToFile(taskList);
            ui.displaySuccessfulAddedTask(task, taskList);
        } catch (IOException | ZhongliException e) {
            ui.displayExceptionMessage(e.getMessage());
        }
    }
}
