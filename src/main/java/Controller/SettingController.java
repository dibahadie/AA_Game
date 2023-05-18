package Controller;

import Model.User;
import Model.UserSetting;
import view.MainMenu;
import view.SettingMenu;
import view.UserMenu.LoginMenu;

public class SettingController {
    private User currentUser;
    public SettingController(User user){
        this.currentUser = user;
    }

    public void run() throws Exception {
        SettingMenu settingMenu = new SettingMenu();
        SettingMenu.controller = this;
        settingMenu.start(LoginMenu.classStage);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void mainMenu(UserSetting newSetting) throws Exception {
        currentUser.setSetting(newSetting);
        System.out.println(currentUser.getSetting().getDifficulty());
        MainMenu mainMenu = new MainMenu();
        mainMenu.start(LoginMenu.classStage);
    }
}
