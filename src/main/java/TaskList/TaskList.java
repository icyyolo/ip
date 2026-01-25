package TaskList;

import java.util.ArrayList;
import Task.Task;
import ZhongliException.ZhongliException;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int getSize() {
        return this.tasks.size();
    }

    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    public void checkValidRange(int index) throws ZhongliException  {
        if (isEmpty()) {
            throw new ZhongliException("The list is empty, please add some tasks before deleting");
        } else if (index < 0 || index >= tasks.size()) {
            throw new ZhongliException("This index does not exist. The range should be between 1 and " + getSize());
        }
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public Task getTask(int index) throws ZhongliException {
        checkValidRange(index);
        return this.tasks.get(index);
    }

    public void deleteTask(int index) throws ZhongliException {
        checkValidRange(index);
        this.tasks.remove(index);
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
