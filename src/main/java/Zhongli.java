import java.util.*;

public class Zhongli {

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

    public static  void displayGoodbyeMessage() {
        printHorizontalLine();
        System.out.println("Bye. Hope to see you again soon!");
        printHorizontalLine();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        displayWelcomeMessage();
        String userInput = input.nextLine();
        while (!userInput.equals("bye")) {
            printHorizontalLine();
            String[] userInputArray = userInput.split(" ");
            if (userInput.equals("list")) {
                for (int i = 1; i <= tasks.size(); i++) {
                    System.out.println(i + ". " + tasks.get(i-1).toString());
                }
            } else if (userInputArray[0].equals("mark")) {
                int index = Integer.parseInt(userInputArray[1]) - 1;
                if (index < 0 || index > tasks.size()) {
                    System.out.println("This index does not exist. Please try again");
                } else {
                    System.out.println("Nice! I've marked this task as done");
                    Task curr = tasks.get(index);
                    curr.markDone();
                    System.out.println("  " + curr.toString());
                }
            } else if (userInputArray[0].equals("unmark")) {
                int index = Integer.parseInt(userInputArray[1]) - 1;
                if (index < 0 || index > tasks.size()) {
                    System.out.println("This index does not exist. Please try again");
                } else {
                    System.out.println("OK, I've marked this task as not done yet");
                    Task curr = tasks.get(index);
                    curr.markUndone();
                    System.out.println("  " + curr.toString());
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
