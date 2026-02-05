package zhongli.gui;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ListItem {

    public HBox createTaskRowTemplate(
            String taskTypeLabel,
            String taskTypeStyle,
            int taskNumber,
            String dateInfo,
            boolean isDone,
            String labelDescription) {
        HBox taskRow = createTaskRowCheckBoxTemplate(isDone);
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

    public Label createTaskRowLabel(int taskNumber, boolean isDone, String labelDescription) {
        Label description = new Label(taskNumber + ") " + labelDescription);
        description.setWrapText(true);
        description.setMaxWidth(Double.MAX_VALUE);
        description.getStyleClass().add("task-description");
        if (isDone) {
            description.getStyleClass().add("task-completed");
        }
        return description;
    }

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
