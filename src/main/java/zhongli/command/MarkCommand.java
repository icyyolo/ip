package zhongli.command;

import zhongli.storage.Storage;
import zhongli.task.Task;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;
import zhongli.zhongliexception.ZhongliException;

import java.io.IOException;

/**
 * Represents a mark Task as done command.
 * If the index of the task is valid and has been successfully updated in the text file,
 * the ui will display a success message.
 * Else the ui will display an error message.
 *
 */
public class MarkCommand extends Command{
    private final String command;
    private final static String successMessage = "Nice! I've marked this task as done";

    public MarkCommand(String command) {
        super();
        this.command = command;
    }

    @Override
    public void run(TaskList taskList, Ui ui, Storage storage) {
        String[] userInputArray = command.split(" ");
        try {
            int index = Integer.parseInt(userInputArray[1]) - 1;
            Task curr = taskList.getTask(index);
            curr.markDone();
            storage.writeTaskListToFile(taskList);
            ui.displayMarkTask(index, successMessage, taskList);
        } catch (IndexOutOfBoundsException e) {
            ui.displayExceptionMessage("Please input a number after delete");
        } catch (NumberFormatException e) {
            ui.displayExceptionMessage("Please input a valid number");
        } catch (ZhongliException | IOException e) {
            ui.displayExceptionMessage(e.getMessage());
        }
    }
}
