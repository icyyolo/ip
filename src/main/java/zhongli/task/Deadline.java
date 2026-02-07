package zhongli.task;

import java.time.LocalDate;

import javafx.scene.layout.HBox;
import zhongli.gui.ListItem;

/**
 * Represents a deadline task which is inherited from Task object.
 *
 */
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
        assert endTime != null : "End time is null";
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

    @Override
    public HBox createTaskRow(int taskNumber) {
        ListItem listItem = new ListItem();
        assert taskNumber >= 0 : "Task number should not be less than 0";
        return listItem.createTaskRowTemplate(
                "D",
                "badge-deadline",
                taskNumber,
                "By: " + endTime.toString(),
                this.getIsDone(),
                this.getDescription()
        );
    }
}
