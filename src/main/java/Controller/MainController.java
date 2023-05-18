package Controller;

import Controller.UserController.ProfileController;
import Model.User;
import view.MainMenu;
import view.SettingMenu;
import view.UserMenu.LoginMenu;
import view.UserMenu.ProfileMenu;

public class MainController {
    private final User currentUser;
    private boolean isGuest;

    public MainController(User user, boolean isGuest){
        this.currentUser = user;
        this.isGuest = isGuest;
    }

    public void run() throws Exception {
        MainMenu mainMenu = new MainMenu();
        mainMenu.start(LoginMenu.classStage);
    }

    public void runSetting() throws Exception {
        SettingMenu menu = new SettingMenu();
        SettingController settingController = new SettingController(currentUser, menu);
        settingController.run();
    }

    public void runProfile() throws Exception {
        ProfileMenu menu = new ProfileMenu();
        ProfileController profileController = new ProfileController(currentUser, menu);
        profileController.run();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isGuest() {
        return isGuest;
    }
}
