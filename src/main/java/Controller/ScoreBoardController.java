package Controller;

import Model.User;
import view.ScoreBoardMenu;
import view.UserMenu.LoginMenu;

public class ScoreBoardController {
    private ScoreBoardMenu menu;
    private User currentUser;
    public ScoreBoardController(User user, ScoreBoardMenu menu){
        this.currentUser = user;
        this.menu = menu;
    }
    public void run() throws Exception {
        ScoreBoardMenu.controller = this;
        menu.start(LoginMenu.classStage);
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
