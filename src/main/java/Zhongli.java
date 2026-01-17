import java.util.Scanner;

public class Zhongli {

    public static void printHorizontalLine() {
        String horizontalLine = "_____________________________________________________________________________________";
        System.out.println(horizontalLine);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        printHorizontalLine();
        System.out.println("Hello! I'm Zhongli");
        System.out.println("What can I do for you?");
        printHorizontalLine();
        String userInput = input.nextLine();
        while (!userInput.equals("bye")) {
            printHorizontalLine();
            System.out.println(userInput);
            printHorizontalLine();
            userInput = input.nextLine();
        }
        printHorizontalLine();
        System.out.println("Bye. Hope to see you again soon!");
        printHorizontalLine();
    }
}
