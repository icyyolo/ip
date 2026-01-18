import java.util.*;

public class Zhongli {

    static ArrayList<Task> tasks;

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

    public static boolean markTasks(int index, boolean isDone) {
        if (index < 0 || index > tasks.size()) {
            return false;
        } else {
            Task curr = tasks.get(index);
            if (isDone) {
                curr.markDone();
            } else {
                curr.markUndone();
            }
            return true;
        }
    }

    public static ToDo parseToDo(String input) throws ZhongliException {
        String[] toDoArr = input.split("todo ", 2);
        if (toDoArr.length < 2) {
            throw new ZhongliException("Missing Description of ToDo");
        }
        return new ToDo(toDoArr[1]);
    }

    public static Deadline parseDeadline(String input) throws ZhongliException {
        String[] descriptionArr = input.split("deadline ", 2);
        if (descriptionArr.length < 2) {
            throw new ZhongliException("Missing Description of Deadline");
        }
        String[] deadline = descriptionArr[1].split("/by ", 2);
        if (deadline.length < 2) {
            throw new ZhongliException("Missing /by command");
        }
        return new Deadline(deadline[0], deadline[1]);
    }

    public static Event parseEvent(String input) throws ZhongliException {
        String[] eventArr = input.split("event ", 2);
        if (eventArr.length < 2) {
            throw new ZhongliException("Missing Description of Event");
        }

        String[] fromArr = eventArr[1].split("/from ", 2);
        if (fromArr.length < 2) {
            throw new ZhongliException("Missing /from command");
        }

        String[] toArr = fromArr[1].split("/to ", 2);
        if (toArr.length < 2) {
            throw new ZhongliException("Missing /to command");
        }

        String description = fromArr[0];
        String startTime = toArr[0];
        String endTime = toArr[1];
        return new Event(description, startTime, endTime);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        tasks = new ArrayList<>();
        displayWelcomeMessage();
        String userInput = input.nextLine();
        while (!userInput.equals("bye")) {
            printHorizontalLine();
            String[] userInputArray = userInput.split(" ");
            if (userInput.equals("list")) {
                listTasksArray();
            } else if (userInputArray[0].equals("mark")) {
                int index = Integer.parseInt(userInputArray[1]) - 1;
                boolean isMark = markTasks(index, true);
                if (!isMark) {
                    System.out.println("This index does not exist. Please try again");
                } else {
                    System.out.println("Nice! I've marked this task as done");
                    System.out.println("  " + tasks.get(index).toString() );
                }
            } else if (userInputArray[0].equals("unmark")) {
                int index = Integer.parseInt(userInputArray[1]) - 1;
                boolean isMark = markTasks(index, false);
                if (!isMark) {
                    System.out.println("This index does not exist. Please try again");
                } else {
                    System.out.println("OK, I've marked this task as not done yet");
                    System.out.println("  " + tasks.get(index).toString());
                }
            } else if (userInputArray[0].equalsIgnoreCase("todo")) {
                try {
                    ToDo newTask = parseToDo(userInput);
                    tasks.add(newTask);
                    displaySuccessfulAddedTask(newTask);
                } catch (ZhongliException e) {
                    System.out.println(e.getMessage());
                }
            } else if (userInputArray[0].equalsIgnoreCase("deadline")) {
                try {
                    Deadline newDeadline = parseDeadline(userInput);
                    tasks.add(newDeadline);
                    displaySuccessfulAddedTask(newDeadline);
                } catch (ZhongliException e) {
                    System.out.println(e.getMessage());
                }
            } else if (userInputArray[0].equalsIgnoreCase("event")) {
                try {
                    Event newEvent = parseEvent(userInput);
                    tasks.add(newEvent);
                    displaySuccessfulAddedTask(newEvent);
                } catch (ZhongliException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("The previous command is not a correct input.");
            }
            printHorizontalLine();
            userInput = input.nextLine();
        }
        displayGoodbyeMessage();
    }
}
