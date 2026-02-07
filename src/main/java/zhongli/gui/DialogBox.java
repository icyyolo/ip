package zhongli.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import zhongli.task.Task;
import zhongli.tasklist.TaskList;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;
    @FXML
    private VBox contentBox;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert img != null : "img is null";
        assert text != null : "text is null";

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        assert img != null : "img is null";
        assert text != null : "text is null";

        DialogBox db = new DialogBox(text, img);
        db.contentBox.getStyleClass().add("user-message-bubble");
        return db;
    }

    public static DialogBox getZhongliDialog(String text, Image img) {
        assert img != null : "img is null";
        assert text != null : "text is null";

        DialogBox db = new DialogBox(text, img);
        db.contentBox.getStyleClass().add("zhongli-message-bubble");
        db.flip();
        return db;
    }

    /**
     * Adds a listBox into the contentBox
     * The list box will display the tasks list.
     *
     */
    public void addListBox(TaskList tasks, String listTitle) {
        assert tasks != null : "img is null";
        assert listTitle != null : "text is null";

        ListBox listBox = new ListBox(listTitle, tasks);
        contentBox.getChildren().add(listBox);
    }

    public static DialogBox getZhongliDialogWithList(String text, Image img, String listTitle, TaskList tasks) {
        assert text != null : "img is null";
        assert img != null : "text is null";
        assert tasks != null : "img is null";
        assert listTitle != null : "text is null";

        DialogBox db = new DialogBox(text, img);
        db.contentBox.getStyleClass().add("zhongli-message-bubble");
        db.addListBox(tasks, listTitle);
        db.flip();
        return db;
    }

    /**
     * Adds a task into the contentBox
     *
     */
    public void addTask(Task task) {
        assert task != null : "task is null";

        HBox taskGui = task.createTaskRow(0);
        contentBox.getChildren().add(taskGui);
    }

    public static DialogBox getZhongliDialogWithTask(String text, Image img, Task task) {
        assert text != null : "img is null";
        assert img != null : "text is null";
        assert task != null : "task is null";

        DialogBox db = new DialogBox(text, img);
        db.contentBox.getStyleClass().add("zhongli-message-bubble");
        db.addTask(task);
        db.flip();
        return db;
    }

    public static DialogBox getErrorDialog(String text, Image img) {
        assert text != null : "img is null";
        assert img != null : "text is null";

        var db = new DialogBox(text, img);
        db.contentBox.getStyleClass().add("error-message-bubble");
        db.dialog.getStyleClass().add("error-message-text");
        db.flip();
        return db;
    }
}

