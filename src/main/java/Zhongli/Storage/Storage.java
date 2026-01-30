package Zhongli.Storage;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import Zhongli.Ui.Ui;
import Zhongli.TaskList.TaskList;
import Zhongli.Task.Task;
import Zhongli.ZhongliException.ZhongliException;
import Zhongli.Parser.Parser;

public class Storage {

    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Read the file from the filePath.
     * If the file does not exists, it will create a new file.
     *
     * @param filePath - the path to the file.
     * @param ui - UI object to display error messages to the user.
     * @return File specified by the filePath String.
     */
    private File readFile(String filePath, Ui ui) {
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                boolean isSuccessful = file.createNewFile();
            } catch (IOException e) {
                ui.displayExceptionMessage("Unable to find file");
            } catch (SecurityException e) {
                ui.displayExceptionMessage(e.getMessage());
            }
        }

        return file;
    }

    /**
     * Read the file given by filePath and transform each line in the file into a valid task object.
     * The task objects will be stored in an array list.
     * If the line is not valid, it will display the line number that has an error, along with the error message.
     *
     * @param ui - UI object to display error messages / invalid lines in the text file to the user.
     * @return ArrayList of Task read from the file.
     * @throws FileNotFoundException - If the file does not exist.
     */
    private ArrayList<Task> getTasksFromFile(Ui ui) throws FileNotFoundException {
        File file = readFile(filePath, ui);
        Scanner s = new Scanner(file);

        ArrayList<Task> tasks = new ArrayList<>();
        int lineNum = 0;

        while (s.hasNext()) {
            lineNum++;
            String curr = s.nextLine();

            try {
                Task task = Parser.parseTaskFromTextFile(curr);
                tasks.add(task);
            } catch (ZhongliException e) {
                System.out.println("Line Number " + lineNum + " has error: " + e.getMessage());
            }
        }

        return tasks;
    }

    /**
     * Formats the file from the given filePath and transform it into a TaskList object.
     *
     * @param ui - UI to display any error message if any.
     * @return TaskList object filled with tasks read from the file.
     */
    public TaskList initializeTaskList(Ui ui) {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            File textFile = this.readFile(filePath, ui);
            tasks = this.getTasksFromFile(ui);
        } catch (FileNotFoundException e) {
            ui.displayExceptionMessage(e.getMessage());
        }

        return new TaskList(tasks);
    }

    /**
     * Write the tasks in taskList to the file.
     *
     * @param taskList - TaskList object with all the tasks objects.
     * @throws IOException - If there is error with IO operation, like writing to the file.
     */
    public void writeTaskListToFile(TaskList taskList) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, false);

        for (int i = 0; i < taskList.getSize(); i++) {
            try {
                Task task = taskList.getTask(i);
                fileWriter.write(task.convertToText());
            } catch (ZhongliException e) {
                System.out.println(e.getMessage());
                continue;
            }
        }

        fileWriter.close();
    }
}
