package view.UserMenu;

import Controller.UserController.SignupController;
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
import view.Messages.LoginMessages;
import view.Messages.SignupMessage;

import java.net.URL;

public class SignupMenu extends Application {
    private SignupController controller = new SignupController();
    public static Stage stage;
    public TextField username;
    public TextField password;
    public TextField passwordConfirmation;
    private BorderPane pane;
    public Text UsernameErrorPrompt = new Text();
    public Text PasswordErrorPrompt = new Text();
    public Text ConfirmationErrorPrompt = new Text();
    @Override
    public void start(Stage stage) throws Exception {
        controller = new SignupController();
        SignupMenu.stage = stage;
        pane = FXMLLoader.load(
                new URL(LoginMenu.class.getResource("/fxml/SignupFXML.fxml").toExternalForm()));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize(){
        Font font = new Font(0);
        UsernameErrorPrompt.setText("");
        PasswordErrorPrompt.setText("");
        ConfirmationErrorPrompt.setText("");
        UsernameErrorPrompt.setFont(font);
        PasswordErrorPrompt.setFont(font);
        ConfirmationErrorPrompt.setFont(font);
    }

    public void signupClicked(MouseEvent mouseEvent) {
        removeErrors();
        String name = username.getText();
        String pass = password.getText();
        if (validateInputs()){
            controller.signup(name, pass);
        }
    }

    public void enterLoginMenu(MouseEvent mouseEvent) throws Exception {
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.start(SignupMenu.stage);
    }

    public void removeErrors(){
        Font font = new Font(0);
        UsernameErrorPrompt.setText("");
        PasswordErrorPrompt.setText("");
        ConfirmationErrorPrompt.setText("");
        ConfirmationErrorPrompt.setFont(font);
        UsernameErrorPrompt.setFont(font);
        PasswordErrorPrompt.setFont(font);
    }

    public void printError(String message, Text prompt){
        prompt.setText(message);
        Font font = new Font(10);
        prompt.setFont(font);
    }

    public boolean validateInputs() {
        String name = username.getText();
        String pass = password.getText();
        String confirm = passwordConfirmation.getText();
        SignupMessage msg1, msg2, msg3;
        msg1 = controller.usernameValidation(name);
        if (!msg1.equals(SignupMessage.SUCCESS)) printError(msg1.getMessage(), UsernameErrorPrompt);
        msg2 = controller.passwordValidation(name, pass);
        if (!msg2.equals(SignupMessage.SUCCESS)) printError(msg2.getMessage(), PasswordErrorPrompt);
        msg3 = controller.passwordConfirmValidation(pass, confirm);
        if (!msg3.equals(SignupMessage.SUCCESS)) printError(msg3.getMessage(), ConfirmationErrorPrompt);
        return msg1.equals(SignupMessage.SUCCESS) &&
                msg2.equals(SignupMessage.SUCCESS) &&
                msg3.equals(SignupMessage.SUCCESS);
    }
}
