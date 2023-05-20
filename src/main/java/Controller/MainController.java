package Controller;

import Controller.UserController.ProfileController;
import Model.Game;
import Model.User;
import view.GameMenu;
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
        SettingMenu settingMenu = new SettingMenu();
        SettingController settingController = new SettingController(currentUser, settingMenu);
        settingController.run();
    }

    public void runProfile() throws Exception {
        ProfileMenu profileMenu = new ProfileMenu();
        ProfileController profileController = new ProfileController(currentUser, profileMenu);
        profileController.run();
    }

    public void runGame() throws Exception {
        GameMenu gameMenu = new GameMenu();
        GameController gameController = new GameController(currentUser, gameMenu, new Game(currentUser));
        gameController.run();
    }
    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isGuest() {
        return isGuest;
    }
}
