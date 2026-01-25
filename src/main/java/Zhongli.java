import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.*;
import java.time.*;

import Storage.Storage;
import Task.*;
import Ui.Ui;
import TaskList.TaskList;
import ZhongliException.ZhongliException;
import Parser.Parser;

public class Zhongli {

    private final static String filePath = ".taskstxt";
    private Storage storage;

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

    public void displayMarkTasks(String[] userInputArray, String successMessage, TaskList taskList, Ui ui) {
        try {
            int index = Integer.parseInt(userInputArray[1]) - 1;
            markTasks(taskList, index, false);
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
                displayMarkTasks(userInputArray, successMessage, taskList, ui);
            } else if (firstWord.equals("unmark")) {
                String successMessage = "OK, I've marked this task as not done yet";
                displayMarkTasks(userInputArray, successMessage, taskList, ui);
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
//            if (! taskString.endsWith("\n")) {
//                taskString += "\n";
//            }
            this.storage.writeTaskListToFile(taskList);
        } catch (IOException e) {
            ui.displayExceptionMessage(e.getMessage());
        }
    }

    private static ArrayList<Task> getTasksFromFile(File file) throws FileNotFoundException {
        Scanner s = new Scanner(file);
        ArrayList<Task> tasks = new ArrayList<>();
        boolean isCorrupted = false;
        int lineNum = 0;
        while (s.hasNext()) {
            lineNum++;
            isCorrupted = false;
            String curr = s.nextLine();
            String errorMsg = "";
            String typeOfTask = curr.split(" ")[0].toLowerCase(Locale.ROOT);
            Task currTask = null;
            try {
                switch (typeOfTask) {
                    case ("todo"):
                        currTask = Parser.parseToDo(curr);
                        break;
                    case ("deadline"):
                        currTask = Parser.parseDeadline(curr);
                        break;
                    case ("event"):
                        currTask = Parser.parseEvent(curr);
                        break;
                    default:
                        isCorrupted = true;
                        errorMsg = "Default task type not found";
                }
            } catch (ZhongliException e) {
                isCorrupted = true;
                errorMsg = e.getMessage();
            }
            if (isCorrupted) {
                System.out.println("Line Number " + lineNum + " has error: "+ errorMsg);
                continue;
            }
            tasks.add(currTask);

        }
        return tasks;
    }

    private ArrayList<Task> initializeChatBot(Ui ui) {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File textFile = this.storage.readFile(filePath, ui);
            tasks = getTasksFromFile(textFile);
        } catch (FileNotFoundException e) {
            ui.displayExceptionMessage(e.getMessage());
        }
        return tasks;
    }

    public Zhongli() {
        Ui ui = new Ui();
        Scanner input = new Scanner(System.in);
        storage = new Storage(filePath);
        TaskList taskList = new TaskList(initializeChatBot(ui));
        ui.displayWelcomeMessage();
        chatbotLoop(input, ui, taskList);
        ui.displayGoodbyeMessage();
    }

    public static void main(String[] args) {
        new Zhongli();
    }
}
