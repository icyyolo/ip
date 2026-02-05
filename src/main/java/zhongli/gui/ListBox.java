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

        listTitle.setText(title);
        populateItems(tasks);
    }

    /**
     * Populates the task container with label items.
     */
    private void populateItems(TaskList tasks) {
        taskContainer.getChildren().clear();

        for (int i = 0; i < tasks.getSize(); i++) {
            try {
                Task currTask = tasks.getTask(i);
                HBox taskRow = createTaskRow(currTask);
                taskContainer.getChildren().add(taskRow);
            } catch (ZhongliException e) {
                System.out.println(e.getMessage());
                continue;
            }
        }
    }

    private HBox createTaskRow(Task task) {
        HBox taskRow = new HBox();

        taskRow.setAlignment(Pos.CENTER_LEFT);
        taskRow.getStyleClass().add("task-row");

        boolean isCompleted = task.getIsDone();

        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(isCompleted);
        checkBox.getStyleClass().add("task-checkbox");

        checkBox.setMouseTransparent(true);
        checkBox.setFocusTraversable(false);

        Label taskLabel = new Label(task.getDescription());
        taskLabel.setWrapText(true);
        taskLabel.setMaxWidth(Double.MAX_VALUE);
        taskLabel.getStyleClass().add("task-label");

        updateTaskLabelStyle(taskLabel, isCompleted);

        taskRow.getChildren().addAll(checkBox, taskLabel);

        return taskRow;
    }

    private void updateTaskLabelStyle(Label label, boolean isCompleted) {
        if (isCompleted) {
            label.getStyleClass().add("task-completed");
        } else {
            label.getStyleClass().remove("task-completed");
        }
    }

    /**
     * Sets the title of the list.
     */
    public void setTitle(String title) {
        listTitle.setText(title);
    }
}
