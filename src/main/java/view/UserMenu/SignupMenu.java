package view.UserMenu;

import Controller.UserController.SignupController;
import Model.User;
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
    private SignupController controller = new SignupController(this);
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
        controller = new SignupController(this);
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

    public void signupClicked(MouseEvent mouseEvent) throws Exception {
        removeErrors();
        String name = username.getText();
        String pass = password.getText();
        String confirm = passwordConfirmation.getText();
        controller.signup(name, pass, confirm);
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

    public void printErrors(SignupMessage userMsg, SignupMessage passMsg, SignupMessage confirmMsg){
        // TODO : fully check errors in the end
        Font font = new Font(10);
        if (userMsg != SignupMessage.SUCCESS){
            UsernameErrorPrompt.setText("* " + userMsg.getMessage());
            UsernameErrorPrompt.setFont(font);
        }
        if (passMsg != SignupMessage.SUCCESS){
            PasswordErrorPrompt.setText("* " + passMsg.getMessage());
            PasswordErrorPrompt.setFont(font);
        }
        if (confirmMsg != SignupMessage.SUCCESS){
            ConfirmationErrorPrompt.setText("* " + confirmMsg.getMessage());
            ConfirmationErrorPrompt.setFont(font);
        }
    }


}
