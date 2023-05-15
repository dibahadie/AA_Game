package Controller.UserController;

import Model.Game;
import view.Messages.LoginMessages;

public class LoginController {
    public LoginMessages login(String username, String password){
        return LoginMessages.SUCCESS;
    }

    public LoginMessages usernameValidation(String username){
        if (username.equals("")) return LoginMessages.ENTER_USERNAME;
        if (!Game.getInstance().doesUserExist(username)) return LoginMessages.USER_NOT_FOUND;
        return LoginMessages.SUCCESS;
    }
    public LoginMessages passwordValidation(String username, String password){
        if (password.equals("")) return LoginMessages.ENTER_PASSWORD;
        System.out.println("hi");
        if (!Game.getInstance().isPasswordValid(username, password)) return LoginMessages.USER_NOT_FOUND;
        return LoginMessages.SUCCESS;
    }
}
