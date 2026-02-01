package zhongli.command;

import java.io.IOException;

import zhongli.gui.Gui;
import zhongli.storage.Storage;
import zhongli.task.Task;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents a mark Task as not done command.
 * If the index of the task is valid and has been successfully updated in the text file,
 * the ui will display a success message.
 * Else the ui will display an error message.
 *
 */
public class UnmarkCommand extends Command {

    private static final String successMessage = "OK, I've marked this task as not done yet";

    private final String command;

    /**
     * Represents a Mark Task as not complete command
     *
     * @param command - command entered by user
     */
    public UnmarkCommand(String command) {
        super();
        this.command = command;
    }

    public String executeCommand(TaskList taskList, Storage storage) {
        String[] userInputArray = command.split(" ");
        try {
            int index = Integer.parseInt(userInputArray[1]) - 1;

            Task curr = taskList.getTask(index);
            curr.markUndone();

            storage.writeTaskListToFile(taskList);
            return Ui.getMarkTaskMessage(index, successMessage, taskList);
        } catch (IndexOutOfBoundsException e) {
            return "Please input a number after delete";
        } catch (NumberFormatException e) {
            return "Please input a valid number";
        } catch (ZhongliException | IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public void run(TaskList taskList, Ui ui, Storage storage) {
        String[] userInputArray = command.split(" ");
        try {
            int index = Integer.parseInt(userInputArray[1]) - 1;

            Task curr = taskList.getTask(index);
            curr.markUndone();

            ui.displayMarkTask(index, successMessage, taskList);

            storage.writeTaskListToFile(taskList);
        } catch (IndexOutOfBoundsException e) {
            ui.displayExceptionMessage("Please input a number after delete");
        } catch (NumberFormatException e) {
            ui.displayExceptionMessage("Please input a valid number");
        } catch (ZhongliException | IOException e) {
            ui.displayExceptionMessage(e.getMessage());
        }
    }

    @Override
    public String runGui(TaskList taskList, Gui gui, Storage storage) {
        return executeCommand(taskList, storage);
    }
}
