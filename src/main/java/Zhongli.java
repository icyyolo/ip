import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.*;
import java.time.*;

import Command.Command;
import Storage.Storage;
import Task.*;
import Ui.Ui;
import TaskList.TaskList;
import ZhongliException.ZhongliException;
import Parser.Parser;

public class Zhongli {

    private final static String filePath = ".taskstxt";
    private final Storage storage;
    private final Ui ui;
    private final TaskList taskList;

    public static void printHorizontalLine() {
        String horizontalLine = "_____________________________________________________________________________________";
        System.out.println(horizontalLine);
    }

    public void markTasks(TaskList taskList, int index, boolean isDone) throws ZhongliException {
        Task curr = taskList.getTask(index);
        if (isDone) {
            curr.markDone();
        } else {
            curr.markUndone();
        }
    }

    public void displayMarkTasks(
            String[] userInputArray,
            String successMessage,
            TaskList taskList,
            Ui ui,
            boolean isDone) {
        try {
            int index = Integer.parseInt(userInputArray[1]) - 1;
            markTasks(taskList, index, isDone);
            ui.displayMarkTask(index, successMessage, taskList);
        } catch (IndexOutOfBoundsException e) {
            ui.displayExceptionMessage("Please input a number after delete");
        } catch (NumberFormatException e) {
            ui.displayExceptionMessage("Please input a valid number");
        } catch (ZhongliException e) {
            ui.displayExceptionMessage(e.getMessage());
        }
    }

    public void deleteTasks(String[] str, Ui ui, TaskList taskList) {
        try {
            String number = str[1];
            int index = Integer.parseInt(number) - 1;
            Task deletedTask = taskList.getTask(index);
            taskList.deleteTask(index);
            ui.displaySuccessfulDeleteTask(deletedTask, taskList);
        } catch (IndexOutOfBoundsException e) {
            ui.displayExceptionMessage("Please input a number after delete");
        } catch (NumberFormatException e) {
            ui.displayExceptionMessage("Please input a valid number");
        } catch (ZhongliException e) {
            ui.displayExceptionMessage(e.getMessage());
        }
    }

    public void chatbotLoop(Scanner input, Ui ui, TaskList taskList) {
        String userInput = input.nextLine();
        while (!userInput.equals("bye")) {
            printHorizontalLine();
            String[] userInputArray = userInput.split(" ");
            String firstWord = userInputArray[0];
            if (userInput.equals("list")) {
                ui.listTasksArray(taskList);
            } else if (firstWord.equals("mark")) {
                String successMessage = "Nice! I've marked this task as done";
                displayMarkTasks(userInputArray, successMessage, taskList, ui, true);
            } else if (firstWord.equals("unmark")) {
                String successMessage = "OK, I've marked this task as not done yet";
                displayMarkTasks(userInputArray, successMessage, taskList, ui, false);
            } else if (firstWord.equalsIgnoreCase("todo")) {
                try {
                    ToDo newTodo= Parser.parseToDo(userInput);
                    addTaskToArray(newTodo, taskList, ui);
                    ui.displaySuccessfulAddedTask(newTodo, taskList);
                } catch (ZhongliException e) {
                    ui.displayExceptionMessage(e.getMessage());
                }
            } else if (firstWord.equalsIgnoreCase("deadline")) {
                try {
                    Deadline newDeadline = Parser.parseDeadline(userInput);
                    addTaskToArray(newDeadline, taskList, ui);
                    ui.displaySuccessfulAddedTask(newDeadline, taskList);
                } catch (ZhongliException e) {
                    ui.displayExceptionMessage(e.getMessage());
                }
            } else if (firstWord.equalsIgnoreCase("event")) {
                try {
                    Event newEvent = Parser.parseEvent(userInput);
                    addTaskToArray(newEvent, taskList, ui);
                    ui.displaySuccessfulAddedTask(newEvent, taskList);
                } catch (ZhongliException e) {
                    ui.displayExceptionMessage(e.getMessage());
                }
            } else if (firstWord.equals("delete")) {
                deleteTasks(userInputArray, ui, taskList);
            } else {
                ui.displayWrongCommandErrorMessage(userInput);
            }
            printHorizontalLine();
            userInput = input.nextLine();
        }
    }

    private void addTaskToArray(Task task, TaskList taskList, Ui ui) {
        taskList.addTask(task);
        try {
            this.storage.writeTaskListToFile(taskList);
        } catch (IOException e) {
            ui.displayExceptionMessage(e.getMessage());
        }
    }

    public Zhongli() {
        ui = new Ui();
        Scanner input = new Scanner(System.in);
        storage = new Storage(filePath);
        taskList = this.storage.initializeTaskList(ui);
        ui.displayWelcomeMessage();
//        chatbotLoop(input, ui, taskList);
        runLoop();
        ui.displayGoodbyeMessage();
    }

    public void runLoop() {
        boolean isExitCommand = false;
        while (!isExitCommand) {
            String input = this.ui.readCommand();
            ui.printHorizontalLine();
            Command command = Parser.parseCommand(input);
            command.run(taskList, ui, storage);
            isExitCommand = command.getIsExit();
            if (isExitCommand) {    //To avoid printing one more horizontal line
                break;
            }
            ui.printHorizontalLine();
        }
    }

    public static void main(String[] args) {
        new Zhongli();
    }
}
