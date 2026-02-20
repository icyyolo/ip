package zhongli;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import zhongli.gui.MainWindow;

/**
 * The JavaFX application launcher for Zhongli. Initializes the GUI by loading the main window
 * from FXML and setting up the primary stage with the application logic controller.
 *
 */
public class Main extends Application {

    private final Zhongli zhongli = new Zhongli();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));

            AnchorPane ap = fxmlLoader.load();
            assert ap != null : "Anchor plane should not be null";

            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Zhongli");

            stage.setMinHeight(300);
            stage.setMinWidth(400);

            fxmlLoader.<MainWindow>getController().setZhongli(zhongli);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
