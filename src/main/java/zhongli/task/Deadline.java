package zhongli.task;
import java.time.LocalDate;

public class Deadline extends Task {
    private LocalDate endTime;

    /**
     * Represents a deadline task. The task must have a description and an end time.
     *
     * @param description - Description of the task.
     * @param endTime - LocalDate time object which represents the end time.
     */
    public Deadline(String description, LocalDate endTime) {
        super(description);
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + super.formatDate(endTime) + ")";
    }

    @Override
    public String convertToText() {
        return "deadline " + super.getDescription()
                + "/by " + endTime.toString()
                + super.getStringIsDone() + "\n";
    }

    public static void getNormalDescription() {
        System.out.println("The correct call to description is : ");
    }

}
