package view;

import Controller.MainController;
import Controller.SettingController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML
    public ImageView logo;
    @Override
    public void start(Stage stage) throws Exception {
        mainPane = FXMLLoader.load(
                new URL(LoginMenu.class.getResource("/fxml/MainFXML.fxml").toExternalForm()));
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        setStyle(scene);
        stage.show();
    }

    @FXML
    public void initialize(){
        if (controller.isGuest()) {
            profileMenu.setVisible(false);
            profileMenu.setManaged(false);
        }
        setImage();
    }

    private void setImage(){
        if (controller.getCurrentUser().getSetting().isBlackAndWhite()){
            logo.setImage(
                    new Image(getClass().getResource("/pictures/logoBlackAndWhite.png").toExternalForm()));
        } else {
            logo.setImage(
                    new Image(getClass().getResource("/pictures/logo.png").toExternalForm()));
        }
    }
    private void setStyle(Scene scene){
        if (controller.getCurrentUser().getSetting().isBlackAndWhite()){
            scene.getStylesheets().add(
                    getClass().getResource("/CSS/BlackAndWhite/generalStyle.css").toExternalForm());
        } else {
            scene.getStylesheets().add(
                    getClass().getResource("/CSS/Normal/generalStyle.css").toExternalForm());
        }
    }

    public void newGame(MouseEvent mouseEvent) throws Exception {
        controller.runGame(true);
    }

    public void continueGame(MouseEvent mouseEvent) {
    }

    public void profileMenu(MouseEvent mouseEvent) throws Exception {
        controller.runProfile();
    }

    public void scoreBoard(MouseEvent mouseEvent) throws Exception {
        controller.runScoreboard();
    }

    public void setting(MouseEvent mouseEvent) throws Exception {
        controller.runSetting();
    }

    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void newGameDouble(MouseEvent mouseEvent) throws Exception {
        controller.runGame(false);
    }
}
