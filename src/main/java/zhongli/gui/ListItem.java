package zhongli.gui;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Represents a tasks objects
 * This will be inside the ListBox object
 *
 */
public class ListItem {

    /**
     * Creates a complete task row template with all components.
     * The row includes a checkbox, task type badge, description, and optional date information.
     */
    public HBox createTaskRowTemplate(
            String taskTypeLabel,
            String taskTypeStyle,
            int taskNumber,
            String dateInfo,
            boolean isDone,
            String labelDescription) {
        HBox taskRow = createTaskRowCheckBoxTemplate(isDone);

        assert taskTypeLabel != null : "taskTypeLabel is null";
        assert taskTypeStyle != null : "taskTypeStyle is null";
        assert taskNumber >= 0 : "taskNumber should not be less than 0";
        assert dateInfo != null : "dateInfo is null";
        assert labelDescription != null : "labelDescription is null";

        VBox taskContent = createTaskContent(
                taskTypeLabel,
                taskTypeStyle,
                taskNumber,
                dateInfo,
                isDone,
                labelDescription);
        taskRow.getChildren().add(taskContent);
        return taskRow;
    }

    /**
     * Creates the task content section containing the type badge, description, and date.
     * The content is organized vertically with the description line (badge + text) on top
     * and optional date information below.
     *
     */
    public VBox createTaskContent(
            String taskTypeLabel,
            String taskTypeStyle,
            int taskNumber,
            String dateInfo,
            boolean isDone,
            String labelDescription) {
        VBox content = new VBox(4);
        content.setMaxWidth(Double.MAX_VALUE);

        // Create main description line with type badge
        HBox descriptionLine = new HBox(12);
        descriptionLine.setAlignment(Pos.CENTER_LEFT);

        // Task type badge
        Label typeBadge = new Label(taskTypeLabel);
        typeBadge.getStyleClass().addAll("task-type-badge", taskTypeStyle);

        // Task description
        Label description = createTaskRowLabel(taskNumber, isDone, labelDescription);

        descriptionLine.getChildren().addAll(typeBadge, description);
        content.getChildren().add(descriptionLine);

        // Add date information if present
        if (!dateInfo.isEmpty()) {
            Label dateLabel = new Label(dateInfo);
            dateLabel.getStyleClass().add("task-date");
            if (isDone) {
                dateLabel.getStyleClass().add("task-completed");
            }
            content.getChildren().add(dateLabel);
        }

        return content;
    }

    /**
     * Creates a label for the task description with optional task numbering.
     * The label supports text wrapping and applies completion styling if the task is done.
     *
     */
    public Label createTaskRowLabel(int taskNumber, boolean isDone, String labelDescription) {
        Label description;

        if (taskNumber > 0) {
            description = new Label(taskNumber + ") " + labelDescription);
        } else {
            description = new Label(labelDescription);
        }

        description.setWrapText(true);
        description.setMaxWidth(Double.MAX_VALUE);
        description.getStyleClass().add("task-description");

        if (isDone) {
            description.getStyleClass().add("task-completed");
        }
        return description;
    }

    /**
     * Creates the base task row container with a checkbox.
     * The checkbox reflects the completion state but is non-interactive.
     *
     */
    public HBox createTaskRowCheckBoxTemplate(boolean isDone) {
        HBox taskRow = new HBox();

        taskRow.setAlignment(Pos.CENTER_LEFT);
        taskRow.getStyleClass().add("task-row");

        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(isDone);
        checkBox.getStyleClass().add("task-checkbox");

        checkBox.setMouseTransparent(true);
        checkBox.setFocusTraversable(false);

        taskRow.getChildren().addAll(checkBox);

        return taskRow;
    }
}
