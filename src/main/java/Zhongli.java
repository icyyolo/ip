import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.File;

public class Zhongli {

    static ArrayList<Task> tasks;
    private final static String filePath = ".taskstxt";

    public static void printHorizontalLine() {
        String horizontalLine = "_____________________________________________________________________________________";
        System.out.println(horizontalLine);
    }

    public static void displayWelcomeMessage() {
        printHorizontalLine();
        System.out.println("Hello! I'm Zhongli");
        System.out.println("What can I do for you?");
        printHorizontalLine();
    }

    public static void displayGoodbyeMessage() {
        printHorizontalLine();
        System.out.println("Bye. Hope to see you again soon!");
        printHorizontalLine();
    }

    public static void displaySuccessfulAddedTask(Task task) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task.toString());
        System.out.println("Now you have " + tasks.size() + " in the lists");
    }

    public static void listTasksArray() {
        for (int i = 1; i <= tasks.size(); i++) {
            System.out.println(i + ". " + tasks.get(i-1).toString());
        }
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

    public static void deleteTasks(String[] str) {
        try {
            String number = str[1];
            int index = Integer.parseInt(number) - 1;
            getValidRange(index);
            Task deletedTask = tasks.get(index);
            tasks.remove(index);
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + deletedTask.toString());
            System.out.println("Now you have " + tasks.size() + " in the lists");
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
        return new Deadline(deadline, endTime);
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
        String endTime = toArr[1].trim();
        if (endTime.isEmpty()) {
            throw new ZhongliException("End Time cannot be empty");
        }
        return new Event(description, startTime, endTime);
    }

    public static void chatbotLoop(Scanner input) {
        String userInput = input.nextLine();
        while (!userInput.equals("bye")) {
            printHorizontalLine();
            String[] userInputArray = userInput.split(" ");
            String firstWord = userInputArray[0];
            if (userInput.equals("list")) {
                listTasksArray();
            } else if (firstWord.equals("mark")) {
                String successMessage = "Nice! I've marked this task as done";
                displayMarkTasks(userInputArray, successMessage);
            } else if (firstWord.equals("unmark")) {
                String successMessage = "OK, I've marked this task as not done yet";
                displayMarkTasks(userInputArray, successMessage);
            } else if (firstWord.equalsIgnoreCase("todo")) {
                try {
                    ToDo newTask = parseToDo(userInput);
                    tasks.add(newTask);
                    displaySuccessfulAddedTask(newTask);
                } catch (ZhongliException e) {
                    System.out.println(e.getMessage());
                }
            } else if (firstWord.equalsIgnoreCase("deadline")) {
                try {
                    Deadline newDeadline = parseDeadline(userInput);
                    tasks.add(newDeadline);
                    displaySuccessfulAddedTask(newDeadline);
                } catch (ZhongliException e) {
                    System.out.println(e.getMessage());
                }
            } else if (firstWord.equalsIgnoreCase("event")) {
                try {
                    Event newEvent = parseEvent(userInput);
                    tasks.add(newEvent);
                    displaySuccessfulAddedTask(newEvent);
                } catch (ZhongliException e) {
                    System.out.println(e.getMessage());
                }
            } else if (firstWord.equals("delete")) {
                deleteTasks(userInputArray);
            } else {
                System.out.println("The previous command is not a correct input.");
            }
            printHorizontalLine();
            userInput = input.nextLine();
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

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        tasks = initializeChatBot();
        displayWelcomeMessage();
        chatbotLoop(input);
        displayGoodbyeMessage();
    }
}
