package zhongli.task;

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
}
