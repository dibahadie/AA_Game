package view.UserMenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class SignupMenu extends Application {

    private Stage classStage;
    private BorderPane pane;
    @Override
    public void start(Stage stage) throws Exception {
        classStage = stage;
        pane = FXMLLoader.load(
                new URL(LoginMenu.class.getResource("/fxml/SignupFXML.fxml").toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }


}
