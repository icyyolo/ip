package zhongli.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import zhongli.task.Task;
import zhongli.tasklist.TaskList;
import zhongli.zhongliexception.ZhongliException;

/**
 * Represents a Mimic of User Interface class
 * This returns a String to output to the GUI
 *
 */
public class Gui {

    private final VBox dialogContainer;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    public Gui(VBox dialog) {
        this.dialogContainer = dialog;
    }

    public void addUserMessage(String input) {
        this.dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
    }

    public void clearChatbox() {
        this.dialogContainer.getChildren().clear();
    }

    public void addTaskList(TaskList taskList) {
        ObservableList<String> taskStrings = FXCollections.observableArrayList(taskList.getTaskStrings());
        this.dialogContainer.getChildren().add(
                DialogBox.getZhongliDialogWithList(
                        "something",
                        dukeImage,
                        "list",
                        taskStrings
                )
        );
    }

    public static String getWelcomeMessage() {
        StringBuilder welcomeMessage = new StringBuilder();
        welcomeMessage.append("Hello! I'm Zhongli\n")
                .append("What can I do for you?\n");
        return welcomeMessage.toString();
    }

    public static String getTasksArrayString(TaskList tasks) {
        return tasks.toString();
    }

}
