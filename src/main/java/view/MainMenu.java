package view;

import Controller.MainController;
import Controller.SettingController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.UserMenu.LoginMenu;

import java.net.URL;

public class MainMenu extends Application {
    public Button newGame;
    public Button profileMenu;
    @FXML
    public BorderPane mainPane;
    public static MainController controller;
    @Override
    public void start(Stage stage) throws Exception {
        mainPane = FXMLLoader.load(
                new URL(LoginMenu.class.getResource("/fxml/MainFXML.fxml").toExternalForm()));
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize(){
        if (controller.isGuest()) {
            profileMenu.setVisible(false);
            profileMenu.setManaged(false);
        }
    }

    public void newGame(MouseEvent mouseEvent) {

    }

    public void continueGame(MouseEvent mouseEvent) {
    }

    public void profileMenu(MouseEvent mouseEvent) {
    }

    public void scoreBoard(MouseEvent mouseEvent) {
    }

    public void setting(MouseEvent mouseEvent) throws Exception {
        controller.runSetting();
    }

    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
