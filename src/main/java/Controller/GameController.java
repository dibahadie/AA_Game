package Controller;

import Model.Game;
import Model.User;
import view.GameMenu;
import view.UserMenu.LoginMenu;

public class GameController {
    private final User currentUser;
    private final GameMenu menu;
    private final Game game;
    public GameController(User currentUser, GameMenu menu, Game game){
        this.menu = menu;
        this.currentUser = currentUser;
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void run() throws Exception {
        GameMenu.controller = this;
        menu.start(LoginMenu.classStage);
    }

    public double getBallX(int ballNumber){
        int allBalls = game.getBallNumber();
        double angle = Math.PI * 2 / allBalls * ballNumber;
        return Math.cos(angle) * 150;
    }
    public double getBallY(int ballNumber){
        int allBalls = game.getBallNumber();
        double angle = Math.PI * 2 / allBalls * ballNumber;
        return Math.sin(angle) * 150;
    }

    public double getRadius(){
        int allBalls = game.getBallNumber();
        return (Math.PI * 100 / allBalls) * 0.8;
    }
}
