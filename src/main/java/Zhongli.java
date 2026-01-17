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
        ArrayList<String> tasks = new ArrayList<>();
        displayWelcomeMessage();
        String userInput = input.nextLine();
        while (!userInput.equals("bye")) {
            if (userInput.equals("list")) {
                printHorizontalLine();
                for (int i = 1; i <= tasks.size(); i++) {
                    System.out.println(i + ". " + tasks.get(i-1));
                }
                printHorizontalLine();
            } else {
                printHorizontalLine();
                System.out.println("Added: " + userInput);
                printHorizontalLine();
                tasks.add(userInput);
            }
            userInput = input.nextLine();
        }
        displayGoodbyeMessage();
    }
}
