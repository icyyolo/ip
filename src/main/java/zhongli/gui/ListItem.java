package zhongli.gui;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Provides factory methods for creating visual representations of tasks in the graphical interface.
 * Constructs task row components including checkboxes, type badges, descriptions, and optional
 * date information with appropriate styling based on task completion state.
 *
 */
public class ListItem {

    /**
     * Creates a complete task row component with all visual elements.
     * Combines a checkbox, task type badge, description text, and optional date information
     * into a single formatted row. Applies completion styling if the task is marked as done.
     *
     * @param taskTypeLabel The label text for the task type badge (e.g., "TODO", "DEADLINE").
     * @param taskTypeStyle The CSS style class for the task type badge.
     * @param taskNumber The display number for the task (1-based indexing). Use 0 to omit numbering.
     * @param dateInfo The date or time information to display, or an empty string if not applicable.
     * @param isDone Boolean indicating whether the task is marked as complete.
     * @param labelDescription The task's description text.
     * @return An HBox containing the complete task row with all components.
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
     * Creates the content section of a task row containing the type badge, description, and optional date.
     * Organizes the task type badge and description on the first line, with optional date information
     * displayed below. Applies completion styling to all elements if the task is marked as done.
     *
     * @param taskTypeLabel The label text for the task type badge.
     * @param taskTypeStyle The CSS style class for the task type badge.
     * @param taskNumber The display number for the task. Use 0 to omit numbering.
     * @param dateInfo The date or time information to display, or an empty string if not applicable.
     * @param isDone Boolean indicating whether the task is marked as complete.
     * @param labelDescription The task's description text.
     * @return A VBox containing the organized task content elements.
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
     * Creates a label for displaying the task description with optional task numbering.
     * The label supports text wrapping and applies completion styling if the task is marked as done.
     * If taskNumber is greater than zero, the label is prefixed with the task number followed by ") ".
     *
     * @param taskNumber The display number for the task (1-based indexing). Use 0 or less to omit numbering.
     * @param isDone Boolean indicating whether the task is marked as complete.
     * @param labelDescription The task's description text.
     * @return A Label containing the formatted task description.
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
     * Creates the base task row container with a non-interactive checkbox.
     * The checkbox is configured to reflect the task's completion state but does not respond to user interaction.
     *
     * @param isDone Boolean indicating whether the checkbox should be selected (task marked as complete).
     * @return An HBox containing the checkbox and ready for additional content.
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
