package zhongli.gui;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import zhongli.task.Task;
import zhongli.tasklist.TaskList;

/**
 * Manages the display of messages and dialogs in the graphical user interface chat container.
 * Provides methods to add user and application messages, display tasks and task lists,
 * and show error notifications with appropriate styling.
 *
 */
public class Dialogue {

    private final VBox dialogContainer;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private final Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/zhongli.jpg"));

    /**
     * Constructs a Dialogue object with the specified dialog container.
     * The container will hold all displayed messages and dialogs.
     *
     * @param dialog The VBox container where dialog messages will be displayed.
     */
    public Dialogue(VBox dialog) {
        assert dialog != null : "dialog is null";
        this.dialogContainer = dialog;
    }

    /**
     * Adds a user message to the dialog container styled as a user message.
     *
     * @param input The user's message text to display.
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
     * Displays a task list in the dialog container with a header message.
     *
     * @param taskList The TaskList containing tasks to display.
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
     * Displays a single task in the dialog container along with an accompanying message.
     *
     * @param task The Task to display.
     * @param message The message text to display alongside the task.
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
     * Displays an error message in the dialog container with error styling.
     *
     * @param message The error message text to display.
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
     * Displays a normal informational message in the dialog container.
     *
     * @param message The message text to display.
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
