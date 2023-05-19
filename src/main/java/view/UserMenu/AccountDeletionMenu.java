package view.UserMenu;

import Controller.UserController.ProfileController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;

public class AccountDeletionMenu extends Application {
    public static ProfileController controller;
    @FXML
    public Button confirmationButton, cancelButton;
    @FXML
    public Text message;


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                new URL(LoginMenu.class.getResource("/fxml/UserFXML/AccountDeletionFXML.fxml").toExternalForm()));
        loader.setController(this);
        BorderPane pane = loader.load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void initialize(){
        Font font = new Font(17);
        message.setFont(font);
        setConfirmationButtonEvent();
        setCancelButtonEvent();
    }
    private void setConfirmationButtonEvent(){
        confirmationButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    controller.deleteAccount();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void setCancelButtonEvent(){
        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    controller.cancelDeletation();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
