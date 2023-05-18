package Controller.UserController;

import Model.Game;
import Model.User;
import view.Messages.SignupMessage;
import view.UserMenu.SignupMenu;

public class SignupController {
    private SignupMenu menu;
    public SignupController(SignupMenu menu){
        this.menu = menu;
    }
    public SignupMessage usernameValidation(String username){
        if (!username.matches("\\S+")) return SignupMessage.INVALID_CHARACTER;
        if (Game.getInstance().doesUserExist(username)) return SignupMessage.TAKEN_USERNAME;
        return SignupMessage.SUCCESS;
    }
    public SignupMessage passwordValidation(String username, String password){
        if (!password.matches(".*[a-z].*")) return SignupMessage.NO_LOWERCASE;
        if (!password.matches(".*[A-Z].*")) return SignupMessage.NO_UPPERCASE;
        if (!password.matches(".*[0-9].*")) return SignupMessage.NO_NUMBER;
        if (!password.matches(".*[!@#$%^&*].*")) return SignupMessage.NO_SIGN;
        if (!password.matches(".{4}.*")) return SignupMessage.SHORT_PASSWORD;
        return SignupMessage.SUCCESS;
    }

    public SignupMessage passwordConfirmValidation(String pass, String confirm){
        if (pass.equals(confirm)) return SignupMessage.SUCCESS;
        else return SignupMessage.CONFIRM_NO_MATCH;
    }

    public void signup(String username, String password){
        User user = new User(username, password);
        Game.getInstance().addUser(user);
    }
}
