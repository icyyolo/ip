package Zhongli.Ui;
import Zhongli.Task.Task;
import Zhongli.TaskList.TaskList;
import Zhongli.ZhongliException.ZhongliException;

import java.util.Scanner;

public class Ui {
    private static final String horizontalLine =
            "_____________________________________________________________________________________";
    private Scanner scanner;

    public Ui(){
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void printHorizontalLine() {
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

    public void displaySuccessfulAddedTask(Task task, TaskList tasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task.toString());
        System.out.println("Now you have " + tasks.getSize() + " in the lists");
    }

    public void displaySuccessfulDeleteTask(Task task, TaskList tasks) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task.toString());
        System.out.println("Now you have " + tasks.getSize() + " in the lists");
    }

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
        System.out.println("The previous command [" +input + "] is not a correct input.");
    }

    public void displayExceptionMessage(String exceptionMessage) {
        System.out.println(exceptionMessage);
    }

    public void listTasksArray(TaskList tasks) {
        System.out.println(tasks.toString());
    }
}
