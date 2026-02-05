package zhongli.command;

import java.io.IOException;

import zhongli.gui.Gui;
import zhongli.storage.Storage;
import zhongli.task.Task;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents a Delete Task to tasklist command.
 * If the index of the task is valid and has been successfully deleted from the text file,
 * the ui will display a success message.
 * Else the ui will display an error message.
 *
 */
public class DeleteCommand extends Command {

    private final String command;

    /**
     * Represents a Delete Task command
     *
     * @param command - command entered by user
     */
    public DeleteCommand(String command) {
        super();
        this.command = command;
    }

    public String executeCommand(TaskList taskList, Gui gui, Storage storage) {
        try {
            String number = this.command.split(" ")[1];
            int index = Integer.parseInt(number) - 1;

            Task deletedTask = taskList.getTask(index);
            taskList.deleteTask(index);

            storage.writeTaskListToFile(taskList);
            gui.displayTask(
                    deletedTask,
                    "Noted. I've removed this task:\n"
                    + "Now you have " + taskList.getSize() + " in the lists"
            );
            return "";
        } catch (IndexOutOfBoundsException e) {
            gui.displayError("Please input a number after delete");
            return "";
        } catch (NumberFormatException e) {
            gui.displayError("Please input a valid number");
            return "";
        } catch (ZhongliException | IOException e) {
            gui.displayError(e.getMessage());
            return "";
        }
    }

    @Override
    public void run(TaskList taskList, Ui ui, Storage storage) {
        try {
            String number = this.command.split(" ")[1];
            int index = Integer.parseInt(number) - 1;

            Task deletedTask = taskList.getTask(index);
            taskList.deleteTask(index);

            ui.displaySuccessfulDeleteTask(deletedTask, taskList);

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
        return executeCommand(taskList, gui, storage);
    }
}
