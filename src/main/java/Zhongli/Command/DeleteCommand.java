package Zhongli.Command;

import Zhongli.Storage.Storage;
import Zhongli.Task.Task;
import Zhongli.TaskList.TaskList;
import Zhongli.Ui.Ui;
import Zhongli.ZhongliException.ZhongliException;

import java.io.IOException;

/**
 * Represents a Delete Task to tasklist command.
 * If the index of the task is valid and has been successfully deleted from the text file,
 * the ui will display a success message.
 * Else the ui will display an error message
 *
 */
public class DeleteCommand extends Command {

    private final String command;

    public DeleteCommand(String command) {
        super();
        this.command = command;
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
}
