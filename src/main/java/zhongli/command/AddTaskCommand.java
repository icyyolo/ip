package zhongli.command;

import zhongli.parser.Parser;
import zhongli.storage.Storage;
import zhongli.task.Task;
import zhongli.taskList.TaskList;
import zhongli.ui.Ui;
import zhongli.zhongliexception.ZhongliException;

import java.io.IOException;

/**
 * Represents an Add Task to tasklist command.
 * If the task is valid and has been successfully added to the text file,
 * the ui will display a success message.
 * Else the ui will display an error message.
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
