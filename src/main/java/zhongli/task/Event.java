package zhongli.task;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

/**
 * Represents an event task which is inherited from Task object.
 *
 */
public class Event extends Task {
    private LocalDate startTime;
    private LocalDate endTime;

    /**
     * Represents an event task. The task must have a start time, a description and an end time.
     *
     * @param description - Description of the task.
     * @param startTime - a LocalDate time object which represents the start time.
     * @param endTime - a LocalDate time object which represents the end time.
     */
    public Event(String description, LocalDate startTime, LocalDate endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + super.formatDate(startTime)
                + " to: " + super.formatDate(endTime) + ")";
    }

    @Override
    public String convertToText() {
        return "event " + super.getDescription()
                + "/from " + startTime.toString()
                + "/to " + endTime.toString()
                + super.getStringIsDone() + "\n";
    }

    @Override
    public HBox createTaskRow(int taskNumber) {
        return createTaskRowTemplate(
                "E",
                "badge-event",
                taskNumber,
                "From: " + startTime.toString() + " To: " + endTime.toString()
        );
    }
}
