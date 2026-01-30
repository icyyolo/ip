package Zhongli.Command;

import Zhongli.Storage.Storage;
import Zhongli.Task.Task;
import Zhongli.TaskList.TaskList;
import Zhongli.Ui.Ui;
import Zhongli.ZhongliException.ZhongliException;

import java.io.IOException;

/**
 * Represents a mark Task as not done command.
 * If the index of the task is valid and has been successfully updated in the text file,
 * the ui will display a success message.
 * Else the ui will display an error message.
 *
 */
public class UnmarkCommand extends Command{

    private final String command;
    private final static String successMessage = "OK, I've marked this task as not done yet";

    public UnmarkCommand(String command) {
        super();
        this.command = command;
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
}
