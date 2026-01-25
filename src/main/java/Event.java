import Task.Task;
import java.time.LocalDate;

public class Event extends Task {
    private LocalDate startTime;
    private LocalDate endTime;

    public Event(String description, LocalDate startTime, LocalDate endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + super.formatDate(startTime)
                + " to: " + super.formatDate(endTime) +")";
    }
}
