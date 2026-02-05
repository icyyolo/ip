package zhongli.task;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Represents a ToDo task which is inherited from Task object.
 *
 */
public class ToDo extends Task {

    /**
     * Represents a ToDo task. The task must have a description.
     *
     * @param description - Description of the task.
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String convertToText() {
        return "todo " + super.getDescription()
                + super.getStringIsDone() + "\n";
    }
    @Override
    public HBox createTaskRow(int taskNumber) {
        return createTaskRowTemplate(
                "T",
                "badge-todo",
                taskNumber,
                ""
        );
    }
}
