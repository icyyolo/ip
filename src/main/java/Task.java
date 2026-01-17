public class Task {
    private boolean isDone;
    private String description;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        String checkbox = isDone ? "[X]" : "[ ]";
        return checkbox + this.description;
    }
}
