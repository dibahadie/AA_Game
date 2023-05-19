package Controller.UserController;

import Controller.MainController;
import Model.Game;
import Model.User;
import Utils.Validation;
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

    public boolean validateEntrance(String name, String pass){
        LoginMessages msg1, msg2;
        msg1 = Validation.usernameValidationLogin(name);
        msg2 = Validation.passwordValidationLogin(name, pass);
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

    public void run() throws Exception {
        this.menu.start(LoginMenu.classStage);
    }
}
