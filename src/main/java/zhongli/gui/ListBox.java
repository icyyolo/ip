package zhongli.gui;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import zhongli.task.Task;
import zhongli.tasklist.TaskList;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents a list box component for displaying tasks in a formatted list.
 */
public class ListBox extends VBox {
    @FXML
    private Label listTitle;
    @FXML
    private VBox taskContainer;

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
     * Populates the task container with label items.
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
     * Sets the title of the list.
     */
    public void setTitle(String title) {
        assert title != null : "title is null";
        listTitle.setText(title);
    }
}
