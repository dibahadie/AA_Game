package Controller;

import Model.User;
import Model.UserSetting;
import Utils.Validation;
import view.MainMenu;
import view.Messages.SettingMessage;
import view.SettingMenu;
import view.UserMenu.LoginMenu;

public class SettingController {
    private User currentUser;
    private SettingMenu menu;
    public SettingController(User user, SettingMenu menu){
        this.currentUser = user;
        this.menu = menu;
    }

    public void run() throws Exception {
        SettingMenu.controller = this;
        menu.start(LoginMenu.classStage);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void mainMenu(String ballNumber, String difficulty, boolean mute,
                         boolean english, boolean BandW) throws Exception {
        if (validateInput(ballNumber)){
            UserSetting newSetting = new UserSetting(difficulty, Integer.parseInt(ballNumber),
                    mute, BandW, english);
            currentUser.setSetting(newSetting);
            MainMenu mainMenu = new MainMenu();
            mainMenu.start(LoginMenu.classStage);
        }
    }

    private boolean validateInput(String ballNumber){
        SettingMessage ballMsg = SettingMessage.SUCCESS;
        if (!Validation.isNumeric(ballNumber)) ballMsg = SettingMessage.INVALID_BALL_NUMBER;
        menu.printError(ballMsg);
        return ballMsg.equals(SettingMessage.SUCCESS);
    }
}
