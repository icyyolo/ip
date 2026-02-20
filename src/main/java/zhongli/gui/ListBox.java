package zhongli.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import zhongli.task.Task;
import zhongli.tasklist.TaskList;
import zhongli.zhongliexception.ZhongliException;

/**
 * A visual component for displaying a list of tasks with a title header.
 * Each task is rendered as a formatted row within a container. Loads its layout from FXML
 * and populates the task display from a provided TaskList.
 *
 */
public class ListBox extends VBox {
    @FXML
    private Label listTitle;
    @FXML
    private VBox taskContainer;

    /**
     * Constructs a ListBox with the specified title and tasks.
     * Loads the list box layout from FXML, sets the title, and populates the task container
     * with visual representations of each task in the provided TaskList.
     *
     * @param title The title text to display at the top of the list box.
     * @param tasks The TaskList containing tasks to display in the list.
     */
    public ListBox(String title, TaskList tasks) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/ListBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert title != null : "title is null";
        assert tasks != null : "tasks is null";

        listTitle.setText(title);
        populateItems(tasks);
    }

    /**
     * Populates the task container by creating a visual row for each task in the provided list.
     * Each task row is positioned with its index number starting from 1.
     * Skips any tasks that fail to create their visual representation.
     *
     * @param tasks The TaskList whose tasks will be displayed.
     */
    private void populateItems(TaskList tasks) {
        taskContainer.getChildren().clear();

        assert tasks != null : "tasks is null";
        for (int i = 0; i < tasks.getSize(); i++) {
            try {
                Task currTask = tasks.getTask(i);
                HBox taskRow = currTask.createTaskRow(i + 1);
                taskContainer.getChildren().add(taskRow);
            } catch (ZhongliException e) {
                System.out.println(e.getMessage());
                continue;
            }
        }
    }

    /**
     * Sets the title text of the list box.
     *
     * @param title The new title text to display.
     */
    public void setTitle(String title) {
        assert title != null : "title is null";
        listTitle.setText(title);
    }
}
