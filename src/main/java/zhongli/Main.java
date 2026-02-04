package zhongli;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import zhongli.gui.MainWindow;

/**
 * Represents a main class
 *
 */
public class Main extends Application {

    private final Zhongli zhongli = new Zhongli();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(300);
            stage.setMinWidth(400);
            fxmlLoader.<MainWindow>getController().setZhongli(zhongli);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
