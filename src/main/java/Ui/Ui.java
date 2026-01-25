package Ui;

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
}
