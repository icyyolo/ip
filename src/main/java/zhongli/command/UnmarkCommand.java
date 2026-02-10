package zhongli.command;

import java.io.IOException;

import zhongli.gui.Dialogue;
import zhongli.storage.Storage;
import zhongli.task.Task;
import zhongli.tasklist.TaskList;
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
    private static final String helpDescription =
            "Mark a task as not complete";

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

    /**
     * Gets the index of the unmark task
     *
     */
    public int parseIndexForUnmarkTask() throws NumberFormatException {
        String[] userInputArray = command.split(" ");
        int index = Integer.parseInt(userInputArray[1]) - 1;
        assert index >= 0 : "Index should not be less than 0";
        return index;
    }

    /**
     * Marks the task as not complete.
     * Then writes the updated task list to a file
     * Display the successful message
     * If there is any error, the respective error message will be displayed
     *
     */
    public void executeCommand(TaskList taskList, Dialogue dialogue, Storage storage) {
        try {
            int index = parseIndexForUnmarkTask();

            Task curr = taskList.getTask(index);
            assert curr != null : "curr is null";

            curr.markUndone();

            storage.writeTaskListToFile(taskList);

            dialogue.displayTask(curr, successMessage);
        } catch (IndexOutOfBoundsException e) {
            dialogue.displayError("Please input a number after delete");
        } catch (NumberFormatException e) {
            dialogue.displayError("Please input a valid number");
        } catch (ZhongliException | IOException e) {
            dialogue.displayError(e.getMessage());
        }
    }

    @Override
    public void runGui(TaskList taskList, Dialogue dialogue, Storage storage) {
        assert taskList != null : "taskList is null";
        assert storage != null : "storage is null";
        assert dialogue != null : "gui is null";
        executeCommand(taskList, dialogue, storage);
    }

    public static String getHelpDescription() {
        return helpDescription;
    }
}
