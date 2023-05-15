package view.UserMenu;

import Controller.UserController.LoginController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.MainMenu;
import view.Messages.LoginMessages;

import java.net.URL;

public class LoginMenu extends Application {
    private static LoginController controller;
    public Text UsernameErrorPrompt = new Text();
    public Text PasswordErrorPrompt = new Text();
    public TextField username = new TextField();
    public TextField password = new TextField();
    public BorderPane pane;
    private static Stage classStage;
    public TextField passwordConfirmation;

    public static void main(String[] args){
        controller = new LoginController();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        classStage = stage;
        pane = FXMLLoader.load(
                new URL(LoginMenu.class.getResource("/fxml/LoginFXML.fxml").toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize(){
        removeErrors();
    }

    public void loginClicked(MouseEvent mouseEvent) throws Exception {
        removeErrors();
        String name = username.getText();
        String pass = password.getText();
        validateInputs();
//        if (controller.login(name, pass).equals(LoginMessages.SUCCESS)){
//            MainMenu mainMenu = new MainMenu();
//            mainMenu.start(classStage);
//        }
    }

    public void signupClicked(MouseEvent mouseEvent) throws Exception {
        SignupMenu signupMenu = new SignupMenu();
        signupMenu.start(classStage);
    }

    public void validateInputs(){
        String name = username.getText();
        String pass = password.getText();
        LoginMessages msg;
        msg = controller.usernameValidation(name);
        if (!msg.equals(LoginMessages.SUCCESS)) printError(msg.getMessage(), UsernameErrorPrompt);
        msg = controller.passwordValidation(name, pass);
        if (!msg.equals(LoginMessages.SUCCESS)) printError(msg.getMessage(), PasswordErrorPrompt);
    }


    public void printError(String message, Text prompt){
        prompt.setText(message);
        Font font = new Font(10);
        prompt.setFont(font);
    }

    public void removeErrors(){
        Font font = new Font(0);
        UsernameErrorPrompt.setText("");
        PasswordErrorPrompt.setText("");
        UsernameErrorPrompt.setFont(font);
        PasswordErrorPrompt.setFont(font);
    }
}
