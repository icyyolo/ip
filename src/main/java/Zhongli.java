import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.*;
import java.time.*;

import Task.*;
import Ui.Ui;
import TaskList.TaskList;
import ZhongliException.ZhongliException;
import Parser.Parser;

public class Zhongli {

    private final static String filePath = ".taskstxt";

    public static void printHorizontalLine() {
        String horizontalLine = "_____________________________________________________________________________________";
        System.out.println(horizontalLine);
    }

    public static void markTasks(TaskList taskList, int index, boolean isDone) throws ZhongliException {
        Task curr = taskList.getTask(index);
        if (isDone) {
            curr.markDone();
        } else {
            curr.markUndone();
        }
    }

    public static void displayMarkTasks(String[] userInputArray, String successMessage, TaskList taskList, Ui ui) {
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

    public static void deleteTasks(String[] str, Ui ui, TaskList taskList) {
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

    public static ToDo parseToDo(String input) throws ZhongliException {
        String[] toDoArr = input.split("todo", 2);
        if (toDoArr.length < 2) {
            throw new ZhongliException("Missing Description of ToDo");
        }
        String description = toDoArr[1].trim();
        if (description.isEmpty()) {
            throw new ZhongliException("Description cannot be empty");
        }
        return new ToDo(description);
    }

    public static Deadline parseDeadline(String input) throws ZhongliException {
        String[] descriptionArr = input.split("deadline", 2);
        if (descriptionArr.length < 2) {
            throw new ZhongliException("Missing Description of Deadline");
        }
        String[] deadlineArr = descriptionArr[1].split("/by", 2);
        if (deadlineArr.length < 2) {
            throw new ZhongliException("Missing /by command");
        }
        String deadline = deadlineArr[0].trim();
        if (deadline.isEmpty()) {
            throw new ZhongliException("Description cannot be empty");
        }

        String endTime = deadlineArr[1].trim();
        if (endTime.isEmpty()) {
            throw new ZhongliException("End Time cannot be empty");
        }
        LocalDate endTimeDate = Parser.parseDate(endTime);
        return new Deadline(deadline, endTimeDate);
    }

    public static Event parseEvent(String input) throws ZhongliException {
        String[] eventArr = input.split("event", 2);
        if (eventArr.length < 2) {
            throw new ZhongliException("Missing Description of Event");
        }

        String[] fromArr = eventArr[1].split("/from", 2);
        if (fromArr.length < 2) {
            throw new ZhongliException("Missing /from command");
        }

        String[] toArr = fromArr[1].split("/to", 2);
        if (toArr.length < 2) {
            throw new ZhongliException("Missing /to command");
        }

        String description = fromArr[0].trim();
        if (description.isEmpty()) {
            throw new ZhongliException("Description cannot be empty");
        }
        String startTime = toArr[0].trim();
        if (startTime.isEmpty()) {
            throw new ZhongliException("Start Time cannot be empty");
        }
        LocalDate startTimeDate = Parser.parseDate(startTime);
        String endTime = toArr[1].trim();
        if (endTime.isEmpty()) {
            throw new ZhongliException("End Time cannot be empty");
        }
        LocalDate endTimeDate = Parser.parseDate(endTime);
        return new Event(description, startTimeDate, endTimeDate);
    }

    public static void chatbotLoop(Scanner input, Ui ui, TaskList taskList) {
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
                    ToDo newTodo= parseToDo(userInput);
                    addTaskToArray(newTodo, userInput, taskList, ui);
                    ui.displaySuccessfulAddedTask(newTodo, taskList);
                } catch (ZhongliException e) {
                    ui.displayExceptionMessage(e.getMessage());
                }
            } else if (firstWord.equalsIgnoreCase("deadline")) {
                try {
                    Deadline newDeadline = parseDeadline(userInput);
                    addTaskToArray(newDeadline, userInput, taskList, ui);
                    ui.displaySuccessfulAddedTask(newDeadline, taskList);
                } catch (ZhongliException e) {
                    ui.displayExceptionMessage(e.getMessage());
                }
            } else if (firstWord.equalsIgnoreCase("event")) {
                try {
                    Event newEvent = parseEvent(userInput);
                    addTaskToArray(newEvent, userInput, taskList, ui);
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

    private static void addTaskToArray(Task task, String taskString, TaskList taskList, Ui ui) {
        taskList.addTask(task);
        try {
            if (! taskString.endsWith("\n")) {
                taskString += "\n";
            }
            writeToFile(filePath, taskString);
        } catch (IOException e) {
            ui.displayExceptionMessage(e.getMessage());
        }
    }

    private static File readFile(String filePath, Ui ui) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                createFile(file);
            } catch (IOException e) {
                ui.displayExceptionMessage("Unable to find file");
            }
        }
        return file;
    }

    private static void createFile(File file) throws IOException {
        file.createNewFile();
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
                        currTask = parseToDo(curr);
                        break;
                    case ("deadline"):
                        currTask = parseDeadline(curr);
                        break;
                    case ("event"):
                        currTask = parseEvent(curr);
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

    private static ArrayList<Task> initializeChatBot(Ui ui) {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File textFile = readFile(filePath, ui);
            tasks = getTasksFromFile(textFile);
        } catch (FileNotFoundException e) {
            ui.displayExceptionMessage(e.getMessage());
        }
        return tasks;
    }

    private static void writeToFile(String filePath, String text) throws IOException{
        FileWriter fileWriter = new FileWriter(filePath, true);
        fileWriter.write(text);
        fileWriter.close();
    }

    public static void main(String[] args) {
        Ui ui = new Ui();
        Scanner input = new Scanner(System.in);
        TaskList taskList = new TaskList(initializeChatBot(ui));
        ui.displayWelcomeMessage();
        chatbotLoop(input, ui, taskList);
        ui.displayGoodbyeMessage();
    }
}
