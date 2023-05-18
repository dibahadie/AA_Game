package Controller;

import Model.User;
import view.MainMenu;
import view.SettingMenu;
import view.UserMenu.LoginMenu;

public class MainController {
    private final User currentUser;

    public MainController(User user){
        this.currentUser = user;
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

    public User getCurrentUser() {
        return currentUser;
    }
}
