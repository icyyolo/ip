package zhongli.command;

import java.io.IOException;

import zhongli.gui.Dialogue;
import zhongli.storage.Storage;
import zhongli.task.Task;
import zhongli.tasklist.TaskList;
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

    /**
     * Gets the index of the deleted task
     *
     */
    public int parseIndexForDeletedTask() throws NumberFormatException {
        String number = this.command.split(" ")[1];
        int index = Integer.parseInt(number) - 1;
        assert index >= 0 : "Index should not be less than 0";
        return index;
    }

    /**
     * Deletes the task from the task list
     * Then writes the updated task list to a file
     * Display the successful message
     * If there is any error, the respective error message will be displayed
     *
     */
    public void executeCommand(TaskList taskList, Dialogue dialogue, Storage storage) {
        try {
            int index = parseIndexForDeletedTask();

            Task deletedTask = taskList.getTask(index);
            assert deletedTask != null : "deletedTask is null";

            taskList.deleteTask(index);

            storage.writeTaskListToFile(taskList);
            dialogue.displayTask(
                    deletedTask,
                    "Noted. I've removed this task:\n"
                            + "Now you have "
                            + taskList.getSize()
                            + " in the lists"
            );
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
}
