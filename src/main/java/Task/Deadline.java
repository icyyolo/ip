package Task;
import java.time.LocalDate;

public class Deadline extends Task {
    private LocalDate endTime;

    public Deadline(String description, LocalDate endTime) {
        super(description);
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + super.formatDate(endTime) + ")";
    }

    public static void getNormalDescription() {
        System.out.println("The correct call to description is : ");
    }

}
