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
            } else {
                System.out.println("Added: " + userInput);
                tasks.add(new Task(userInput));
            }
            printHorizontalLine();
            userInput = input.nextLine();
        }
        displayGoodbyeMessage();
    }
}
