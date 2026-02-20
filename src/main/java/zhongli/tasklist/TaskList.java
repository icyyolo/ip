package zhongli.tasklist;

import java.util.ArrayList;
import java.util.Objects;

import zhongli.task.Task;
import zhongli.zhongliexception.ZhongliException;

/**
 * Manages a collection of tasks stored in an ArrayList. Provides functionality to add, retrieve,
 * and delete tasks, as well as search for tasks matching a given pattern.
 *
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     *
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with the provided ArrayList of tasks.
     * If the provided ArrayList is null, an empty ArrayList is used instead.
     *
     * @param tasks The ArrayList of tasks to initialize the list with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = Objects.requireNonNullElseGet(tasks, ArrayList::new);
    }

    /**
     * Returns the number of tasks in task list.
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
     * Validates that the given index is within the valid range of the task list.
     * Throws an exception if the list is empty or the index is out of bounds.
     *
     * @param index The index to validate.
     * @throws ZhongliException If the list is empty or the index is outside the range [0, size).
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
        assert task != null : "Task in addTask is null";
        this.tasks.add(task);
    }

    /**
     * Returns the task at the specified index in the list.
     *
     * @param index - to retrieve the task from the array.
     * @return task object at the index in the array.
     * @throws ZhongliException - if the index is invalid.
     */
    public Task getTask(int index) throws ZhongliException {
        checkValidRange(index);
        assert index >= 0 : "Index should not be less than 0";
        return this.tasks.get(index);
    }

    /**
     * Removes the task at the specified index from the list.
     *
     * @param index - to delete the task from the array.
     * @throws ZhongliException - if the index is invalid.
     */
    public void deleteTask(int index) throws ZhongliException {
        checkValidRange(index);
        assert index >= 0 : "Index should not be less than 0";
        this.tasks.remove(index);
    }

    /**
     * Returns a new TaskList containing all tasks whose description contains the specified search pattern.
     *
     * @param regex The search pattern to match against task descriptions.
     * @return A new TaskList with all matching tasks.
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
