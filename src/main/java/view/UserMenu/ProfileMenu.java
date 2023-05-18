package view.UserMenu;

import Controller.UserController.ProfileController;
import Model.User;
import Model.UserSetting;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;

public class ProfileMenu extends Application {
    public static ProfileController controller;
    @Override
    public void start(Stage stage) throws Exception {
        VBox box = FXMLLoader.load(
                new URL(LoginMenu.class.getResource("/fxml/ProfileFXML.fxml").toExternalForm()));
        Scene scene = new Scene(box);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize(){
        Font font = new Font(0);
    }

    public void mainMenu(MouseEvent mouseEvent) {
    }
}
