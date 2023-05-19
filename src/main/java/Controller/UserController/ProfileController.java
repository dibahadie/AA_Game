package Controller.UserController;

import Controller.MainController;
import Model.Game;
import Model.User;
import Utils.Validation;
import view.MainMenu;
import view.Messages.LoginMessages;
import view.Messages.SignupMessage;
import view.UserMenu.AccountDeletionMenu;
import view.UserMenu.LoginMenu;
import view.UserMenu.ProfileMenu;

public class ProfileController {
    private User currentUser;
    private ProfileMenu menu;
    public ProfileController(User user, ProfileMenu menu){
        this.menu = menu;
        this.currentUser = user;
    }

    public void run() throws Exception {
        ProfileMenu.controller = this;
        AccountDeletionMenu.controller = this;
        menu.start(LoginMenu.classStage);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void mainMenu() throws Exception {
        String name = menu.username.getText();
        String pass = menu.password.getText();
        if (validateInputs(name, pass)) {
            saveSettings(name, pass);
            MainMenu.controller.run();
        }
    }

    public boolean validateInputs(String name, String pass){
        SignupMessage msg1, msg2;
        msg1 = Validation.usernameValidation(name);
        if (msg1.equals(SignupMessage.TAKEN_USERNAME)){
            if (name.equals(currentUser.getUsername())) msg1 = SignupMessage.SUCCESS;
        }
        msg2 = Validation.passwordValidation(name, pass);
        menu.printError(msg1, msg2);
        return msg1.equals(SignupMessage.SUCCESS) &&
                msg2.equals(SignupMessage.SUCCESS);
    }

    public void saveSettings(String username, String password){
        currentUser.setUsername(username);
        currentUser.setPassword(password);
    }

    public void logout() throws Exception {
        String name = menu.username.getText();
        String pass = menu.password.getText();
        if (validateInputs(name, pass)) {
            saveSettings(name, pass);
            LoginMenu loginMenu = new LoginMenu();
            loginMenu.start(LoginMenu.classStage);
        }
    }

    public void runDeleteAccount() throws Exception {
        AccountDeletionMenu accountDeletionMenu = new AccountDeletionMenu();
        accountDeletionMenu.start(LoginMenu.classStage);
    }

    public void deleteAccount() throws Exception {
        Game.getInstance().removeUser(currentUser.getUsername());
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.start(LoginMenu.classStage);
    }

    public void cancelDeletation() throws Exception {
        menu.start(LoginMenu.classStage);
    }
}
