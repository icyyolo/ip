package zhongli.command;

import java.io.IOException;

import zhongli.gui.Gui;
import zhongli.storage.Storage;
import zhongli.task.Task;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents a mark Task as done command.
 * If the index of the task is valid and has been successfully updated in the text file,
 * the ui will display a success message.
 * Else the ui will display an error message.
 *
 */
public class MarkCommand extends Command {
    private static final String successMessage = "Nice! I've marked this task as done";

    private final String command;

    /**
     * Represents a Mark Task as not complete command
     *
     * @param command - command entered by user
     */
    public MarkCommand(String command) {
        super();
        this.command = command;
    }

    public void executeCommand(TaskList taskList, Gui gui, Storage storage) {
        String[] userInputArray = command.split(" ");
        try {
            int index = Integer.parseInt(userInputArray[1]) - 1;
            assert index >= 0 : "Index should not be less than 0";

            Task curr = taskList.getTask(index);
            assert curr != null : "curr is null";

            curr.markDone();
            storage.writeTaskListToFile(taskList);
            gui.displayTask(curr, successMessage);
        } catch (IndexOutOfBoundsException e) {
            gui.displayError("Please input a number after delete");
        } catch (NumberFormatException e) {
            gui.displayError("Please input a valid number");
        } catch (ZhongliException | IOException e) {
            gui.displayError(e.getMessage());
        }
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

    @Override
    public void runGui(TaskList taskList, Gui gui, Storage storage) {
        assert taskList != null : "taskList is null";
        assert storage != null : "storage is null";
        assert gui != null : "gui is null";
        executeCommand(taskList, gui, storage);
    }
}
