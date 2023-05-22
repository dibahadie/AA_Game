package view.UserMenu;

import Controller.UserController.ProfileController;
import Model.User;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.Messages.SignupMessage;

import java.net.URL;

public class ProfileMenu extends Application {
    public static ProfileController controller;
    @FXML
    public TextField username, password;
    @FXML
    public Text UsernameErrorPrompt, PasswordErrorPrompt;
    @FXML
    public Button backButton, logoutButton, deleteAccountButton, gameAvatars, chooseAvatar;
    @FXML
    public ImageView avatar;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                new URL(LoginMenu.class.getResource("/fxml/UserFXML/ProfileFXML.fxml").toExternalForm()));
        loader.setController(this);
        VBox box = loader.load();

        setInitialValues();
        setBackButtonEvent();
        setLogoutButtonEvent();
        setDeleteAccountButtonEvent();
        setGameAvatarsEvent();
        clearErrors();
        Scene scene = new Scene(box);
        stage.setScene(scene);
        stage.show();
    }

    private void setInitialValues(){
        User user = controller.getCurrentUser();
        username.setText(user.getUsername());
        password.setText(user.getPassword());
        avatar.setImage(new Image(user.getAvatarPath()));
    }

    private void setBackButtonEvent(){
        backButton.setOnMouseClicked(event -> {
            try {
                clearErrors();
                controller.mainMenu();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setLogoutButtonEvent(){
        logoutButton.setOnMouseClicked(event -> {
            try {
                controller.logout();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setDeleteAccountButtonEvent(){
        deleteAccountButton.setOnMouseClicked(event -> {
            try {
                controller.runDeleteAccount();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setGameAvatarsEvent(){
        gameAvatars.setOnMouseClicked(e -> {
            try {
                controller.runChangeAvatar();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void printError(SignupMessage userMsg, SignupMessage passMsg){
        Font font = new Font(10);
        if (userMsg != SignupMessage.SUCCESS){
            UsernameErrorPrompt.setText("* " + userMsg.getMessage());
            UsernameErrorPrompt.setFont(font);
        }
        if (passMsg != SignupMessage.SUCCESS){
            PasswordErrorPrompt.setText("* " + passMsg.getMessage());
            PasswordErrorPrompt.setFont(font);
        }
    }

    private void clearErrors(){
        Font font = new Font(0);
        UsernameErrorPrompt.setText("");
        PasswordErrorPrompt.setText("");
        UsernameErrorPrompt.setFont(font);
        PasswordErrorPrompt.setFont(font);
    }
}
