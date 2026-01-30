package Zhongli.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Task {
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

    abstract public String convertToText();

    public String getStringIsDone() {
        return this.getIsDone() ? "/mark" : "/unmark";
    }

    public String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }
}
