package zhongli.tasklist;

import java.util.ArrayList;
import java.util.Objects;

import zhongli.task.Task;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents an ArrayList of tasks.
 *
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = Objects.requireNonNullElseGet(tasks, ArrayList::new);
    }

    /**
     * Returns the size of the task list.
     *
     */
    public int getSize() {
        return this.tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     */
    public boolean isEmpty() {
        return this.tasks.isEmpty();
    }

    /**
     * Checks if the index given is within the array size.
     *
     * @param index - the index the user want to access.
     * @throws ZhongliException - if the index is not within the range, or there is no items in the array.
     *
     */
    public void checkValidRange(int index) throws ZhongliException {
        if (isEmpty()) {
            throw new ZhongliException("The list is empty, please add some tasks before deleting");
        } else if (index < 0 || index >= tasks.size()) {
            throw new ZhongliException("This index does not exist. The range should be between 1 and " + getSize());
        }
    }

    /**
     * Add the task into the task list.
     *
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Get the task at the index in the array.
     *
     * @param index - to retrieve the task from the array.
     * @return task object at the index in the array.
     * @throws ZhongliException - if the index is invalid.
     */
    public Task getTask(int index) throws ZhongliException {
        checkValidRange(index);
        return this.tasks.get(index);
    }

    /**
     * Delete the task at the index in the array.
     *
     * @param index - to delete the task from the array.
     * @throws ZhongliException - if the index is invalid.
     */
    public void deleteTask(int index) throws ZhongliException {
        checkValidRange(index);
        this.tasks.remove(index);
    }

    /**
     * Returns all tasks whose description contains the String regex
     *
     */
    public TaskList getMatchingTask(String regex) {
        TaskList res = new TaskList();

        int i = 1;
        for (int a = 0; a < tasks.size(); a++) {
            boolean matchRegex = tasks.get(a).doesRegexMatchDescription(regex);

            if (matchRegex) {
                res.addTask(tasks.get(a));
            }

        }

        return res;
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
