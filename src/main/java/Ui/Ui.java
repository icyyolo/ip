package Ui;
import Task.Task;

import java.util.ArrayList;

public class Ui {
    private static final String horizontalLine =
            "_____________________________________________________________________________________";


    public Ui(){
    }

    private void printHorizontalLine() {
        System.out.println(Ui.horizontalLine);
    }

    public void displayWelcomeMessage() {
        printHorizontalLine();
        System.out.println("Hello! I'm Zhongli");
        System.out.println("What can I do for you?");
        printHorizontalLine();
    }

    public void displayGoodbyeMessage() {
        printHorizontalLine();
        System.out.println("Bye. Hope to see you again soon!");
        printHorizontalLine();
    }

    public void displaySuccessfulAddedTask(Task task, ArrayList<Task> tasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task.toString());
        System.out.println("Now you have " + tasks.size() + " in the lists");
    }

    public void displaySuccessfulDeleteTask(Task task, ArrayList<Task> tasks) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task.toString());
        System.out.println("Now you have " + tasks.size() + " in the lists");
    }

    public void displayMarkTask(int index, String successMessage, ArrayList<Task> tasks) {
        System.out.println(successMessage);
        System.out.println("  " + tasks.get(index).toString());
    }

    public void displayWrongCommandErrorMessage(String input) {
        System.out.println("The previous command [" +input + "] is not a correct input.");
    }

    public void displayExceptionMessage(String exceptionMessage) {
        System.out.println(exceptionMessage);
    }

    public void listTasksArray(ArrayList<Task> tasks) {
        for (int i = 1; i <= tasks.size(); i++) {
            System.out.println(i + ". " + tasks.get(i-1).toString());
        }
    }
}
