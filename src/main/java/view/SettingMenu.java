package view;

import Controller.SettingController;
import Model.User;
import Model.UserSetting;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.UserMenu.LoginMenu;

import java.net.URL;

public class SettingMenu extends Application {
    @FXML
    public ChoiceBox gameDifficulty = new ChoiceBox<>();
    public static SettingController controller;
    @Override
    public void start(Stage stage) throws Exception {
        VBox box = FXMLLoader.load(
                new URL(LoginMenu.class.getResource("/fxml/SettingFXML.fxml").toExternalForm()));
        Scene scene = new Scene(box);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize(){
        UserSetting setting = controller.getCurrentUser().getSetting();
        gameDifficulty.setValue(setting.getDifficulty());
    }

    public void mainMenu(MouseEvent mouseEvent) throws Exception {
        UserSetting newSetting = new UserSetting();
        newSetting.setDifficulty(((String) gameDifficulty.getValue()).toUpperCase());
        controller.mainMenu(newSetting);
    }
}
