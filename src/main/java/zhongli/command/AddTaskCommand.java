package zhongli.command;

import java.awt.*;
import java.io.IOException;

import zhongli.gui.Gui;
import zhongli.parser.Parser;
import zhongli.storage.Storage;
import zhongli.task.Task;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents an Add Task to tasklist command.
 * If the task is valid and has been successfully added to the text file,
 * the ui will display a success message.
 * Else the ui will display an error message.
 *
 */
public class AddTaskCommand extends Command {
    private String userInput;

    /**
     * Represents a Add Task command
     *
     * @param userInput - command entered by user
     */
    public AddTaskCommand(String userInput) {
        super();
        this.userInput = userInput;
    }

    public void executeCommand(TaskList taskList, Gui gui, Storage storage) {
        try {
            Task task = Parser.parseTaskFromInput(userInput);
            assert task != null : "task is null";

            taskList.addTask(task);

            storage.writeTaskListToFile(taskList);

            gui.displayTask(task, "Got it. I've added this task:\n" + "Now you have " + taskList.getSize() + " in the list");

        } catch (IOException | ZhongliException e) {
            gui.displayError(e.getMessage());
        }
    }

    @Override
    public void runGui(TaskList taskList, Gui gui, Storage storage) {
        assert taskList != null : "taskList is null";
        assert storage != null : "storage is null";
        assert gui != null : "gui is null";
        executeCommand(taskList, gui, storage);
    }
}
