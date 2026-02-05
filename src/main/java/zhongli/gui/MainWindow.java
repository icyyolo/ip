package zhongli.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import zhongli.ui.Ui;
import zhongli.Zhongli;

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

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    private Zhongli zhongli;
    private Gui gui;

    public void setZhongli(Zhongli zhongli) {
        this.zhongli = zhongli;
    }

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        this.displayWelcomeMessage();
        this.gui = new Gui(dialogContainer);
    }

    @FXML
    private void displayWelcomeMessage() {
        dialogContainer.getChildren().addAll(
                DialogBox.getZhongliDialog(Ui.getWelcomeMessage(), dukeImage)
        );
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        gui.addUserMessage(input);
        zhongli.getGui(input, gui);
        userInput.clear();
    }
}
