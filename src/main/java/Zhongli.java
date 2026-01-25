import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.*;
import java.time.*;

import Task.Task;
import Ui.Ui;

public class Zhongli {

    static ArrayList<Task> tasks;
    private final static String filePath = ".taskstxt";

    public static void printHorizontalLine() {
        String horizontalLine = "_____________________________________________________________________________________";
        System.out.println(horizontalLine);
    }

    public static void markTasks(int index, boolean isDone) throws ZhongliException {
        if (index < 0 || index > tasks.size()) {
            throw new ZhongliException("This index does not exist. Please try again");
        } else {
            Task curr = tasks.get(index);
            if (isDone) {
                curr.markDone();
            } else {
                curr.markUndone();
            }
        }
    }

    public static void displayMarkTasks(String[] userInputArray, String successMessage) {
        int index = Integer.parseInt(userInputArray[1]) - 1;
        try {
            markTasks(index, false);
            System.out.println(successMessage);
            System.out.println("  " + tasks.get(index).toString());
        } catch (ZhongliException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getValidRange(int index) throws ZhongliException {
        if (tasks.isEmpty()) {
            throw new ZhongliException("The list is empty, please add some tasks before deleting");
        } else if (index < 0 || index >= tasks.size()) {
            throw new ZhongliException("This index does not exist. The range should be between 1 and " + tasks.size());
        }
    }

    public static void deleteTasks(String[] str, Ui ui) {
        try {
            String number = str[1];
            int index = Integer.parseInt(number) - 1;
            getValidRange(index);
            Task deletedTask = tasks.get(index);
            tasks.remove(index);
            ui.displaySuccessfulDeleteTask(deletedTask, tasks);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Please input a number after delete");
        } catch (NumberFormatException e) {
            System.out.println("Please input a valid number");
        } catch (ZhongliException e) {
            System.out.println(e.getMessage());
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
        LocalDate endTimeDate = parseDate(endTime);
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
        LocalDate startTimeDate = parseDate(startTime);
        String endTime = toArr[1].trim();
        if (endTime.isEmpty()) {
            throw new ZhongliException("End Time cannot be empty");
        }
        LocalDate endTimeDate = parseDate(endTime);
        return new Event(description, startTimeDate, endTimeDate);
    }

    public static void chatbotLoop(Scanner input, Ui ui) {
        String userInput = input.nextLine();
        while (!userInput.equals("bye")) {
            printHorizontalLine();
            String[] userInputArray = userInput.split(" ");
            String firstWord = userInputArray[0];
            if (userInput.equals("list")) {
                ui.listTasksArray(tasks);
            } else if (firstWord.equals("mark")) {
                String successMessage = "Nice! I've marked this task as done";
                displayMarkTasks(userInputArray, successMessage);
            } else if (firstWord.equals("unmark")) {
                String successMessage = "OK, I've marked this task as not done yet";
                displayMarkTasks(userInputArray, successMessage);
            } else if (firstWord.equalsIgnoreCase("todo")) {
                try {
                    ToDo newTodo= parseToDo(userInput);
                    addTaskToArray(newTodo, userInput);
                    ui.displaySuccessfulAddedTask(newTodo, tasks);
                } catch (ZhongliException e) {
                    System.out.println(e.getMessage());
                }
            } else if (firstWord.equalsIgnoreCase("deadline")) {
                try {
                    Deadline newDeadline = parseDeadline(userInput);
                    addTaskToArray(newDeadline, userInput);
                    ui.displaySuccessfulAddedTask(newDeadline, tasks);
                } catch (ZhongliException e) {
                    System.out.println(e.getMessage());
                }
            } else if (firstWord.equalsIgnoreCase("event")) {
                try {
                    Event newEvent = parseEvent(userInput);
                    addTaskToArray(newEvent, userInput);
                    ui.displaySuccessfulAddedTask(newEvent, tasks);
                } catch (ZhongliException e) {
                    System.out.println(e.getMessage());
                }
            } else if (firstWord.equals("delete")) {
                deleteTasks(userInputArray, ui);
            } else {
                ui.displayWrongCommandErrorMessage(userInput);
            }
            printHorizontalLine();
            userInput = input.nextLine();
        }
    }

    private static void addTaskToArray(Task task, String taskString) {
        tasks.add(task);
        try {
            if (! taskString.endsWith("\n")) {
                taskString += "\n";
            }
            writeToFile(filePath, taskString);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static File readFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                createFile(file);
            } catch (IOException e) {
                System.out.println("Unable to find file");
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

    private static ArrayList<Task> initializeChatBot() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File textFile = readFile(filePath);
            tasks = getTasksFromFile(textFile);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    private static void writeToFile(String filePath, String text) throws IOException{
        FileWriter fileWriter = new FileWriter(filePath, true);
        fileWriter.write(text);
        fileWriter.close();
    }

    private static LocalDate parseDate(String dateText) throws ZhongliException {
        LocalDate date;
        // Special cases:
        if (dateText.equals("now")) {
            date = LocalDate.now();
            return date;
        }

        try {
            date = LocalDate.parse(dateText);
        } catch (DateTimeException e) {
            throw new ZhongliException(e.getMessage() +
                    "\nDate Should be in this format YYYY-MM-DD");
        }
        return date;
    }

    public static void main(String[] args) {
        Ui ui = new Ui();
        Scanner input = new Scanner(System.in);
        tasks = initializeChatBot();
        ui.displayWelcomeMessage();
        chatbotLoop(input, ui);
        ui.displayGoodbyeMessage();
    }
}
