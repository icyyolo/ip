package zhongli.ui;

import zhongli.tasklist.TaskList;

/**
 * Represents the User Interface class.
 * It handles both the input and output via the command line for the user.
 *
 */
public class Ui {

    public static String getWelcomeMessage() {
        return "Hello! I'm Zhongli\n" + "What can I do for you?\n";
    }

    /**
     * Displays a farewell message, after you entered the bye command.
     *
     */
    public static String displayGoodbyeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns a message to display to GUI
     * about a successful addition of task to the task list
     *
     * @param tasks - the task list
     */
    public static String getSuccesfulAddedTask(TaskList tasks) {
        String res = "";
        res = res + "Got it. I've added this task:\n"
                + "Now you have " + tasks.getSize() + " in the list";
        return res;
    }

    /**
     * Returns a message to display to GUI
     * about a successful deletion of task from the task list
     *
     * @param tasks - the task list
     */
    public static String getSuccessfulDeleteTask(TaskList tasks) {
        return "Noted. I've removed this task:" + "\n"
                + "Now you have " + tasks.getSize() + " in the list";
    }


    public static String displayWrongCommandErrorMessage(String input) {
        return "The previous command [" + input + "] is not a correct input.";
    }

    public void displayExceptionMessage(String exceptionMessage) {
        System.out.println(exceptionMessage);
    }

}
