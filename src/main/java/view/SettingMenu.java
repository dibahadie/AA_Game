package view;

import Controller.SettingController;
import Model.User;
import Model.UserSetting;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    @FXML
    public ChoiceBox gameDifficulty, mapNumber;
    @FXML
    public TextField ballNumber;
    @FXML
    public CheckBox blackAndWhite, english, mute;
    @FXML
    public Text BallNumberPrompt;
    @FXML
    public Button backButton;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                new URL(LoginMenu.class.getResource("/fxml/SettingFXML.fxml").toExternalForm()));
        loader.setController(this);
        VBox box = loader.load();
        Scene scene = new Scene(box);
        stage.setScene(scene);
        setOnChangeStyle(scene);
        setStyle(scene);
        stage.show();
    }

    @FXML
    public void initialize(){
        Font font = new Font(0);
        BallNumberPrompt.setFont(font);
        BallNumberPrompt.setText("");

        setInitialValues();
        setBackButtonEvent();
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

    private void setBackButtonEvent(){
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    controller.mainMenu(ballNumber.getText(), gameDifficulty.getValue().toString(),
                            mute.isSelected(), english.isSelected(), blackAndWhite.isSelected(),
                            mapNumber.getValue().toString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void setOnChangeStyle(Scene scene){
        blackAndWhite.setOnMouseClicked(e -> {
            controller.getCurrentUser().getSetting().setBlackAndWhite(blackAndWhite.isSelected());
            setStyle(scene);
        });
    }

    private void setInitialValues(){
        UserSetting setting = controller.getCurrentUser().getSetting();
        mapNumber.setValue(setting.getMapNumber());
        gameDifficulty.setValue(setting.getDifficulty());
        ballNumber.setText(setting.getBallNumber());
        mute.setSelected(setting.isMute());
        blackAndWhite.setSelected(setting.isBlackAndWhite());
        english.setSelected(setting.isEnglish());
    }

    public void printError(SettingMessage ballMsg){
        Font font = new Font(10);
        BallNumberPrompt.setText("* " + ballMsg.getMessage());
        System.out.println(ballMsg);
        BallNumberPrompt.setFont(font);
    }
}
