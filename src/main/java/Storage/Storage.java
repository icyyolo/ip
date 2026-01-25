package Storage;

import java.io.*;
import Ui.Ui;
import TaskList.TaskList;
import Task.Task;
import ZhongliException.ZhongliException;

public class Storage {

    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public File readFile(String filePath, Ui ui) {
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
