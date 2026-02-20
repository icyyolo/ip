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
 * Represents a dialog box displayed in the chat interface. Consists of a speaker's profile image
 * and a content box containing text messages, task lists, individual tasks, or error messages.
 * Supports different visual styles for user messages, Zhongli responses, and error notifications.
 *
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;
    @FXML
    private VBox contentBox;

    /**
     * Constructs a DialogBox with the specified text message and speaker image.
     * Loads the dialog box layout from FXML and applies the given text and image.
     *
     * @param text The message text to display in the dialog box.
     * @param img The profile image representing the speaker.
     */
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
     * Reverses the layout of the dialog box by flipping the order of child nodes.
     * This positions the image on the left and the text on the right, typically used
     * for responses from the assistant.
     *
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog box styled as a user message with the image on the right
     * and text on the left.
     *
     * @param text The user's message text.
     * @param img The user's profile image.
     * @return A DialogBox styled as a user message.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        assert img != null : "img is null";
        assert text != null : "text is null";

        DialogBox db = new DialogBox(text, img);
        db.contentBox.getStyleClass().add("user-message-bubble");
        return db;
    }

    /**
     * Creates a dialog box styled as a Zhongli response with the image on the left
     * and text on the right.
     *
     * @param text Zhongli's response text.
     * @param img Zhongli's profile image.
     * @return A DialogBox styled as a Zhongli message.
     */
    public static DialogBox getZhongliDialog(String text, Image img) {
        assert img != null : "img is null";
        assert text != null : "text is null";

        DialogBox db = new DialogBox(text, img);
        db.contentBox.getStyleClass().add("zhongli-message-bubble");
        db.flip();
        return db;
    }

    /**
     * Adds a task list display to the dialog box's content. The list box will be rendered
     * below the message text with the specified title.
     *
     * @param tasks The TaskList containing tasks to display.
     * @param listTitle The title to display above the task list.
     */
    public void addListBox(TaskList tasks, String listTitle) {
        assert tasks != null : "img is null";
        assert listTitle != null : "text is null";

        ListBox listBox = new ListBox(listTitle, tasks);
        contentBox.getChildren().add(listBox);
    }

    /**
     * Creates a dialog box styled as a Zhongli response containing both a message
     * and a task list.
     *
     * @param text Zhongli's response text.
     * @param img Zhongli's profile image.
     * @param listTitle The title to display above the task list.
     * @param tasks The TaskList containing tasks to display.
     * @return A DialogBox styled as a Zhongli message with an embedded task list.
     */
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
     * Adds a single task display to the dialog box's content. The task will be rendered
     * below the message text with its visual representation.
     *
     * @param task The Task to display.
     */
    public void addTask(Task task) {
        assert task != null : "task is null";

        HBox taskGui = task.createTaskRow(0);
        contentBox.getChildren().add(taskGui);
    }

    /**
     * Creates a dialog box styled as a Zhongli response containing both a message
     * and a single task display.
     *
     * @param text Zhongli's response text.
     * @param img Zhongli's profile image.
     * @param task The Task to display.
     * @return A DialogBox styled as a Zhongli message with an embedded task display.
     */
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

    /**
     * Creates a dialog box styled as an error message with visual indicators
     * for error state (image on left, text on right).
     *
     * @param text The error message text.
     * @param img The speaker's profile image.
     * @return A DialogBox styled as an error message.
     */
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

