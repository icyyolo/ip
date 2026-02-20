package zhongli.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import zhongli.parser.Parser;
import zhongli.task.Task;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;
import zhongli.zhongliexception.ZhongliException;

/**
 * Manages persistent storage of tasks. Handles reading tasks from and writing tasks to a text file,
 * with error handling for malformed task entries and IO operations.
 *
 */
public class TaskStorage extends Storage {

    public TaskStorage(String filePath) {
        super((filePath));
    }

    /**
     * Reads tasks from the storage file and parses them into Task objects.
     * Skips any lines that fail to parse and logs the error with the corresponding line number.
     *
     * @param ui The UI object used to display error messages.
     * @return An ArrayList of successfully parsed Task objects.
     * @throws FileNotFoundException If the storage file cannot be found or accessed.
     */
    private ArrayList<Task> getTasksFromFile(Ui ui) throws FileNotFoundException {
        assert ui != null : "ui is null";
        File file = super.readFile(filePath, ui);
        Scanner s = new Scanner(file);

        ArrayList<Task> tasks = new ArrayList<>();
        int lineNum = 0;

        while (s.hasNext()) {
            lineNum++;
            String curr = s.nextLine();

            try {
                Task task = Parser.parseTaskFromTextFile(curr);
                assert task != null : "Task parsed from text file is null";
                tasks.add(task);
            } catch (ZhongliException e) {
                System.out.println("Line Number " + lineNum + " has error: " + e.getMessage());
            }
        }

        return tasks;
    }

    /**
     * Reads tasks from the storage file and returns them as a TaskList object.
     * If the file cannot be found, an empty TaskList is returned and an error message is displayed.
     *
     * @param ui The UI object used to display error messages.
     * @return A TaskList containing all successfully loaded tasks.
     */
    public TaskList initializeTaskList(Ui ui) {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            tasks = this.getTasksFromFile(ui);
        } catch (FileNotFoundException e) {
            ui.displayExceptionMessage(e.getMessage());
        }

        return new TaskList(tasks);
    }

    /**
     * Writes all tasks from the provided TaskList to the storage file.
     * Overwrites the existing file content. Skips any tasks that fail to convert to text format.
     *
     * @param taskList The TaskList containing tasks to be persisted.
     * @throws IOException If an IO error occurs while writing to the file.
     */
    public void writeTaskListToFile(TaskList taskList) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, false);

        for (int i = 0; i < taskList.getSize(); i++) {
            try {
                Task task = taskList.getTask(i);
                assert task != null : "Task when writing to file is null";
                fileWriter.write(task.convertToText());
            } catch (ZhongliException e) {
                System.out.println(e.getMessage());
                continue;
            }
        }

        fileWriter.close();
    }
}
