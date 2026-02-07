package zhongli.gui;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import zhongli.task.Task;
import zhongli.tasklist.TaskList;
import zhongli.ui.Ui;

/**
 * Represents a Mimic of User Interface class
 * This returns a String to output to the GUI
 *
 */
public class Dialogue {

    private final VBox dialogContainer;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Represents the dialogue, the messages of the GUI
     *
     */
    public Dialogue(VBox dialog) {
        assert dialog != null : "dialog is null";
        this.dialogContainer = dialog;
    }

    /**
     * Adds user message to the dialog container
     *
     */
    public void addUserMessage(String input) {
        assert input != null : "input is null";
        this.dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
    }

    public void clearChatbox() {
        this.dialogContainer.getChildren().clear();
    }

    /**
     * Adds a taskList to the dialogue with a message
     *
     */
    public void addTaskList(TaskList taskList) {
        assert taskList != null : "taskList is null";
        this.dialogContainer.getChildren().add(
                DialogBox.getZhongliDialogWithList(
                        "The following are your tasks:",
                        dukeImage,
                        "Tasks: ",
                        taskList
                )
        );
    }

    /**
     * Display the message
     * Followed by the task
     *
     */
    public void displayTask(Task task, String message) {
        assert task != null : "task is null";
        assert message != null : "message is null";
        this.dialogContainer.getChildren().add(
                DialogBox.getZhongliDialogWithTask(
                        message,
                        dukeImage,
                        task
                )
        );
    }

    /**
     * Display an error message with a red border
     *
     */
    public void displayError(String message) {
        assert message != null : "message is null";
        this.dialogContainer.getChildren().add(
                DialogBox.getErrorDialog(
                        message,
                        dukeImage
                )
        );
    }

    /**
     * Displays a normal message
     *
     */
    public void displayMessage(String message) {
        assert message != null : "message is null";
        this.dialogContainer.getChildren().add(
                DialogBox.getZhongliDialog(
                        message,
                        dukeImage
                )
        );
    }
    
}
