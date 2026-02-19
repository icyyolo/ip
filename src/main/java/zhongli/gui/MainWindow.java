package zhongli.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import zhongli.Zhongli;
import zhongli.ui.Ui;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Image zhongliImage = new Image(this.getClass().getResourceAsStream("/images/zhongli.jpg"));

    private Zhongli zhongli;
    private Dialogue dialogue;

    public void setZhongli(Zhongli zhongli) {
        this.zhongli = zhongli;
    }

    /**
     * Initialize the dialog container
     * Display a welcome message
     *
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        this.displayWelcomeMessage();
        this.dialogue = new Dialogue(dialogContainer);
    }

    @FXML
    private void displayWelcomeMessage() {
        dialogContainer.getChildren().addAll(
                DialogBox.getZhongliDialog(Ui.getWelcomeMessage(), zhongliImage)
        );
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        dialogue.addUserMessage(input);
        zhongli.getGui(input, dialogue);
        userInput.clear();
    }
}
