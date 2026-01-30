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
