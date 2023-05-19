package Controller.UserController;

import Model.Game;
import Model.User;
import Utils.Validation;
import view.Messages.SignupMessage;
import view.UserMenu.LoginMenu;
import view.UserMenu.SignupMenu;

public class SignupController {
    private SignupMenu menu;
    public SignupController(SignupMenu menu){
        this.menu = menu;
    }

    public void signup(String username, String password, String confirmation) throws Exception {
        if (validateInputs(username, password, confirmation)){
            User user = new User(username, password);
            Game.getInstance().addUser(user);
            LoginMenu loginMenu = new LoginMenu();
            loginMenu.start(SignupMenu.stage);
        }
    }

    public boolean validateInputs(String name, String pass, String confirm) {
        SignupMessage msg1, msg2, msg3;
        msg1 = Validation.usernameValidation(name);
        msg2 = Validation.passwordValidation(name, pass);
        msg3 = Validation.passwordConfirmValidation(pass, confirm);
        menu.printErrors(msg1, msg2, msg3);
        return msg1.equals(SignupMessage.SUCCESS) &&
                msg2.equals(SignupMessage.SUCCESS) &&
                msg3.equals(SignupMessage.SUCCESS);
    }
}
