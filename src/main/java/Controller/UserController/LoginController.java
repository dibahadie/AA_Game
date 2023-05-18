package Controller.UserController;

import Controller.MainController;
import Model.Game;
import Model.User;
import view.MainMenu;
import view.Messages.LoginMessages;
import view.UserMenu.LoginMenu;

public class LoginController {
    private LoginMenu menu;
    public LoginController(LoginMenu menu){
        this.menu = menu;
    }

    public void login() throws Exception {
        String name = menu.username.getText();
        String pass = menu.password.getText();
        if (validateEntrance(name, pass)) {
            User user = Game.getInstance().getUser(name);
            MainController mainController = new MainController(user, false);
            MainMenu.controller = mainController;
            mainController.run();
        }
    }

    public LoginMessages usernameValidation(String username){
        if (username.equals("")) return LoginMessages.ENTER_USERNAME;
        if (!Game.getInstance().doesUserExist(username)) return LoginMessages.USER_NOT_FOUND;
        return LoginMessages.SUCCESS;
    }
    public LoginMessages passwordValidation(String username, String password){
        if (password.equals("")) return LoginMessages.ENTER_PASSWORD;
        if (!Game.getInstance().isPasswordValid(username, password)) return LoginMessages.PASSWORD_INCORRECT;
        return LoginMessages.SUCCESS;
    }

    public boolean validateEntrance(String name, String pass){
        LoginMessages msg1, msg2;
        msg1 = usernameValidation(name);
        msg2 = passwordValidation(name, pass);
        menu.printError(msg1, msg2);
        return msg1.equals(LoginMessages.SUCCESS) &&
                msg2.equals(LoginMessages.SUCCESS);
    }

    public void enterAsGuest() throws Exception {
        User user = Game.getInstance().getUser("default");
        MainController mainController = new MainController(user, true);
        MainMenu.controller = mainController;
        mainController.run();
    }
}
