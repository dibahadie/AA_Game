package Controller.UserController;

import Model.User;
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
        menu.start(LoginMenu.classStage);
    }
}
