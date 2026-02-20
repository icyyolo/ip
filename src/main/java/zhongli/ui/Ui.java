package zhongli.ui;

import zhongli.tasklist.TaskList;

/**
 * Manages user interface messages for display in the graphical interface. Provides predefined
 * messages for user feedback including welcome/goodbye messages, task operation confirmations,
 * and error notifications.
 *
 */
public class Ui {

    public static String getWelcomeMessage() {
        return "Hello! I'm Zhongli\n" + "What can I do for you?\n";
    }

    /**
     * Returns the farewell message displayed when the user exits the application.
     *
     * @return The goodbye message string.
     */
    public static String displayGoodbyeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns a confirmation message after a task has been successfully added to the task list.
     * Includes the updated total number of tasks in the list.
     *
     * @param tasks The task list after the task addition.
     * @return The success message for task addition.
     */
    public static String getSuccesfulAddedTask(TaskList tasks) {
        String res = "";
        res = res + "Got it. I've added this task:\n"
                + "Now you have " + tasks.getSize() + " in the list";
        return res;
    }

    /**
     * Returns a confirmation message after a task has been successfully added to the task list.
     * Includes the updated total number of tasks in the list.
     *
     * @param tasks The task list after the task addition.
     * @return The success message for task addition.
     */
    public static String getSuccessfulDeleteTask(TaskList tasks) {
        return "Noted. I've removed this task:" + "\n"
                + "Now you have " + tasks.getSize() + " in the list";
    }

    /**
     * Returns an error message indicating that the provided input is not a valid command.
     *
     * @param input The invalid command input provided by the user.
     * @return The error message for invalid command input.
     */
    public static String displayWrongCommandErrorMessage(String input) {
        return "The previous command [" + input + "] is not a correct input.";
    }

    /**
     * Displays an exception error message to the standard output stream.
     *
     * @param exceptionMessage The exception message to be displayed.
     */
    public void displayExceptionMessage(String exceptionMessage) {
        System.out.println(exceptionMessage);
    }

}
