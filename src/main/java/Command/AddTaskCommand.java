package Command;

import Parser.Parser;
import Storage.Storage;
import Task.Task;
import TaskList.TaskList;
import Ui.Ui;
import ZhongliException.ZhongliException;

import java.io.IOException;

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
