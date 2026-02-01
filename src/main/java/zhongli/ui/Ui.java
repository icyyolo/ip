package zhongli.ui;

import java.util.Scanner;

import zhongli.task.Task;
import zhongli.tasklist.TaskList;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents the User Interface class.
 * It handles both the input and output via the command line for the user.
 *
 */
public class Ui {
    private static final String horizontalLine =
            "_____________________________________________________________________________________";
    private Scanner scanner;

    /**
     * Initializes the scanner to read user input.
     *
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads and returns the user input from the command line.
     *
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints a horizontal line.
     *
     */
    public void printHorizontalLine() {
        System.out.println(Ui.horizontalLine);
    }

    /**
     * Displays a welcome message.
     *
     */
    public void displayWelcomeMessage() {
        printHorizontalLine();

        System.out.println("Hello! I'm Zhongli");
        System.out.println("What can I do for you?");

        printHorizontalLine();
    }

    public static String getWelcomeMessage() {
        StringBuilder welcomeMessage = new StringBuilder();
        welcomeMessage.append("Hello! I'm Zhongli\n")
                .append("What can I do for you?\n");
        return welcomeMessage.toString();
    }

    /**
     * Displays a farewell message, after you entered the bye command.
     *
     */
    public void displayGoodbyeMessage() {
        printHorizontalLine();

        System.out.println("Bye. Hope to see you again soon!");

        printHorizontalLine();
    }

    /**
     * Displays a successful addition of task to the task list
     *
     * @param task - task that is added to the task list
     * @param tasks - the task list
     */
    public void displaySuccessfulAddedTask(Task task, TaskList tasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task.toString());
        System.out.println("Now you have " + tasks.getSize() + " in the lists");
    }

    public static String getSuccesfulAddedTask(Task task, TaskList tasks) {
        String res = "";
        res = res + "Got it. I've added this task:\n"
                + "  " + task.toString() + "\n"
                + "Now you have " + tasks.getSize() + " in the list";
        return res;
    }

    /**
     * Displays a successful deletion of task from the task list
     *
     * @param task - task that is added to the task list
     * @param tasks - the task list
     */
    public void displaySuccessfulDeleteTask(Task task, TaskList tasks) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task.toString());
        System.out.println("Now you have " + tasks.getSize() + " in the lists");
    }

    public static String getSuccessfulDeleteTask(Task task, TaskList tasks) {
        return "Noted. I've removed this task:" + "\n"
                + "  " + task.toString() + "\n"
                + "Now you have " + tasks.getSize() + " in the list";
    }

    /**
     * Displays the output from marking/unmarking a task
     *
     */
    public void displayMarkTask(int index, String successMessage, TaskList tasks) {
        try {
            Task task = tasks.getTask(index);
            System.out.println(successMessage);
            System.out.println("  " + task.toString());
        } catch (ZhongliException e) {
            System.out.println(e.getMessage());
        }

    }

    public void displayWrongCommandErrorMessage(String input) {
        System.out.println("The previous command [" + input + "] is not a correct input.");
    }

    public void displayExceptionMessage(String exceptionMessage) {
        System.out.println(exceptionMessage);
    }

    public void listTasksArray(TaskList tasks) {
        System.out.println(tasks.toString());
    }

    /**
     * Diplays the matched task from the task list
     *
     * @param matchedTask - Formatted matched task string
     * @param regex - Original phrase the user is searching for
     */
    public void displayFindMessage(String matchedTask, String regex) {
        if (matchedTask.isEmpty()) {
            System.out.println("Phrase: '" + regex + "' has no matches in task list.");
        } else {
            System.out.println("Here are the matching tasks in your lists:");
            System.out.println(matchedTask);
        }
    }
}
