package zhongli.task;

import java.time.LocalDate;

import javafx.scene.layout.HBox;
import zhongli.gui.ListItem;

/**
 * Represents an event task which is inherited from Task object.
 *
 */
public class Event extends Task {
    private LocalDate startTime;
    private LocalDate endTime;

    private final String taskTypeLabel = "E";
    private final String taskTypeStyle = "badge-event";

    /**
     * Represents an event task. The task must have a start time, a description and an end time.
     *
     * @param description - Description of the task.
     * @param startTime - a LocalDate time object which represents the start time.
     * @param endTime - a LocalDate time object which represents the end time.
     */
    public Event(String description, LocalDate startTime, LocalDate endTime) {
        super(description);
        assert startTime != null : "Start time is null";
        assert endTime != null : "End time is null";
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

    public static String getHelpDescription() {
        return "Adds an event task to the task list";
    }

    @Override
    public HBox createTaskRow(int taskNumber) {
        ListItem listItem = new ListItem();
        assert taskNumber >= 0 : "Task number should not be less than 0";
        return listItem.createTaskRowTemplate(
                "E",
                "badge-event",
                taskNumber,
                "From: " + startTime.toString() + " To: " + endTime.toString(),
                this.getIsDone(),
                this.getDescription()
        );
    }
}
