package view;

import Controller.SettingController;
import Model.User;
import Model.UserSetting;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.Messages.SettingMessage;
import view.UserMenu.LoginMenu;

import java.net.URL;

public class SettingMenu extends Application {
    public static SettingController controller;
    public ChoiceBox gameDifficulty = new ChoiceBox<>();
    public TextField ballNumber = new TextField();
    public Text BallNumberPrompt = new Text("");
    public CheckBox mute;
    public CheckBox blackAndWhite;
    public CheckBox english;

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
        Font font = new Font(0);
        BallNumberPrompt.setFont(font);
        BallNumberPrompt.setText("");

        UserSetting setting = controller.getCurrentUser().getSetting();

        gameDifficulty.setValue(setting.getDifficulty());
        ballNumber.setText(setting.getBallNumber());
        mute.setSelected(setting.isMute());
        blackAndWhite.setSelected(setting.isBlackAndWhite());
        english.setSelected(setting.isEnglish());
    }

    public void mainMenu(MouseEvent mouseEvent) throws Exception {
        controller.mainMenu(ballNumber.getText(), gameDifficulty.getValue().toString(),
                mute.isSelected(), english.isSelected(), blackAndWhite.isSelected());
    }

    public void printError(SettingMessage ballMsg){
        // TODO : fix error message issue
        Font font = new Font(10);
        BallNumberPrompt.setText(ballMsg.getMessage());
        System.out.println(ballMsg);
        BallNumberPrompt.setFont(font);
    }
}
