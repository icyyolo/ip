package Command;

import Storage.Storage;
import Task.Task;
import TaskList.TaskList;
import Ui.Ui;
import ZhongliException.ZhongliException;

import java.io.IOException;

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
            ui.displayMarkTask(index, successMessage, taskList);
            storage.writeTaskListToFile(taskList);
        } catch (IndexOutOfBoundsException e) {
            ui.displayExceptionMessage("Please input a number after delete");
        } catch (NumberFormatException e) {
            ui.displayExceptionMessage("Please input a valid number");
        } catch (ZhongliException e) {
            ui.displayExceptionMessage(e.getMessage());
        } catch (IOException e) {
            ui.displayExceptionMessage(e.getMessage());
        }
    }
}
