package view.UserMenu;

import Controller.UserController.LoginController;
import Controller.UserController.UserManager;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.Messages.LoginMessages;

import java.net.URL;

public class LoginMenu extends Application {
    private LoginController controller = new LoginController(this);
    @FXML
    public Text PasswordErrorPrompt, UsernameErrorPrompt;
    @FXML
    public TextField username, password;
    @FXML
    public BorderPane pane;
    public static Stage classStage;

    public static void main(String[] args) {
        UserManager.load();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        classStage = stage;
        pane = FXMLLoader.load(
                new URL(LoginMenu.class.getResource("/fxml/UserFXML/LoginFXML.fxml").toExternalForm()));
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("/CSS/Normal/generalStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void initialize() {
        removeErrors();
    }

    public void loginClicked(MouseEvent mouseEvent) throws Exception {
        removeErrors();
        controller.login();
    }

    public void signupClicked(MouseEvent mouseEvent) throws Exception {
        SignupMenu signupMenu = new SignupMenu();
        signupMenu.start(classStage);
    }

    public void printError(LoginMessages userMessage, LoginMessages passMessage) {
        Font font = new Font(10);
        if (userMessage != LoginMessages.SUCCESS){
            UsernameErrorPrompt.setText("* " + userMessage.getMessage());
            UsernameErrorPrompt.setFont(font);
        }
        if (passMessage != LoginMessages.SUCCESS){
            PasswordErrorPrompt.setText("* " + passMessage.getMessage());
            PasswordErrorPrompt.setFont(font);
        }
    }

    public void removeErrors() {
        Font font = new Font(0);
        UsernameErrorPrompt.setText("");
        PasswordErrorPrompt.setText("");
        UsernameErrorPrompt.setFont(font);
        PasswordErrorPrompt.setFont(font);
    }

    public void guestEntrance(MouseEvent mouseEvent) throws Exception {
        controller.enterAsGuest();
    }
}
