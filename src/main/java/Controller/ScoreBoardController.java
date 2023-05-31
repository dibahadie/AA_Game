package Controller;

import Model.AA;
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
        AA.updateRanking();
        ScoreBoardMenu.controller = this;
        menu.start(LoginMenu.classStage);
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
