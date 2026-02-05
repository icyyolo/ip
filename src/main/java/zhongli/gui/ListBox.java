package zhongli.gui;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Represents a list box component for displaying tasks in a formatted list.
 */
public class ListBox extends VBox {
    @FXML
    private Label listTitle;
    @FXML
    private VBox taskContainer;

    public ListBox(String title, ObservableList<String> items) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/ListBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listTitle.setText(title);
        populateItems(items);
    }

    /**
     * Populates the task container with label items.
     */
    private void populateItems(ObservableList<String> items) {
        taskContainer.getChildren().clear();

        for (String item : items) {
            Label taskLabel = new Label(item);
            taskLabel.setWrapText(true);
            taskLabel.setMaxWidth(Double.MAX_VALUE);
            taskLabel.getStyleClass().add("task-item");
            taskContainer.getChildren().add(taskLabel);
        }
    }

    /**
     * Updates the list with new items.
     */
    public void updateList(ObservableList<String> items) {
        populateItems(items);
    }

    /**
     * Sets the title of the list.
     */
    public void setTitle(String title) {
        listTitle.setText(title);
    }
}
