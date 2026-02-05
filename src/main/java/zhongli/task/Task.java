package zhongli.task;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a generic task class that contains a description and a boolean
 * to check if the task is done or not.
 *
 */
public abstract class Task {
    private boolean isDone;
    private String description;

    /**
     * Represents a task.
     * The task require a description, and a isDone variable will be marked false.
     *
     * @param description - Description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Sets the isDone variable to true.
     *
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Sets the isDone variable to false.
     */
    public void markUndone() {
        this.isDone = false;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Checks if the string regex is in description.
     *
     */
    public boolean doesRegexMatchDescription(String regex) {
        return this.description.contains(regex);
    }

    @Override
    public String toString() {
        String checkbox = isDone ? "[X] " : "[ ] ";
        return checkbox + this.description;
    }

    /**
     * Returns the text to be inserted into the text file.
     *
     */
    public abstract String convertToText();

    /**
     * Returns /mark if isDone variable is true, else it will return /unmark.
     *
     */
    public String getStringIsDone() {
        return this.getIsDone() ? "/mark" : "/unmark";
    }

    /**
     * Returns the formated date into a String
     *
     * @param date - LocalDate object to be transformed into a string
     * @return dateString - with the format of MMM dd yyyy
     */
    public String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }

    public HBox createTaskRow(int taskNumber) {
        HBox taskRow = createTaskRowCheckBoxTemplate(this.getIsDone());
        Label taskLabel = createTaskRowLabel(taskNumber, this.getIsDone());
        taskRow.getChildren().addAll(taskLabel);
        return taskRow;
    }

    public Label createTaskRowLabel(int taskNumber, boolean isDone) {
        Label description = new Label(taskNumber + ") " + this.getDescription());
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

    private void updateTaskLabelStyle(Label label, boolean isCompleted) {
        if (isCompleted) {
            label.getStyleClass().add("task-completed");
        } else {
            label.getStyleClass().remove("task-completed");
        }
    }
}
