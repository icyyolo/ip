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

    public static void displaySuccessfulAddedTask(Task task, ArrayList<Task> tasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task.toString());
        System.out.println("Now you have " + tasks.size() + " in the lists");
    }
}
