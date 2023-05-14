package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.UserMenu.LoginMenu;

import java.net.URL;

public class MainMenu extends Application {
    private Stage classStage;
    private BorderPane pane;
    @Override
    public void start(Stage stage) throws Exception {
        classStage = stage;
        pane = FXMLLoader.load(
                new URL(LoginMenu.class.getResource("/fxml/MainFXML.fxml").toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

}
