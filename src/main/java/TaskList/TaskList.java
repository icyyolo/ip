package TaskList;

import java.util.ArrayList;
import Task.Task;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int getSize() {
        return tasks.size();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 1; i <= tasks.size(); i++) {
            res.append(i)
                    .append(". ")
                    .append(tasks.get(i - 1).toString())
                    .append("\n");
        }
        return res.toString();
    }
}
