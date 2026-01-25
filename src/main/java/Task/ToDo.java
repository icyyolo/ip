package Task;

public class ToDo extends Task {
    public ToDo (String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String convertToText() {
        String isDone = super.getIsDone() ? " /mark" : " /unmark";
        return "todo " + super.getDescription() + isDone + "\n";
    }
}
