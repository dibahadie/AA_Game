package Controller;

import Model.Game;
import Model.User;
import view.GameMenu;
import view.UserMenu.LoginMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public ArrayList<Integer> getBallMap(){
        int ballNumber = getGame().getBallNumber();
        switch (currentUser.getSetting().getMapNumber()){
            case "random":
                ArrayList<Integer> list = new ArrayList<>();
                for (int i = 0; i < ballNumber; i++) list.add(i);
                Collections.shuffle(list);
                return new ArrayList<>(List.of(list.get(0), list.get(1), list.get(2),
                list.get(3), list.get(4)));
            case "map1":
                return new ArrayList<>(List.of(0, 1, 2, 3, 4));
            case "map2":
                return new ArrayList<>(List.of(0, 1, ballNumber/2, ballNumber/3, ballNumber/4));
            case "map3":
                return new ArrayList<>(List.of(ballNumber/5, ballNumber/ 5 * 2, ballNumber / 5 * 3,
                        ballNumber/ 5 * 4, ballNumber - 1));
        }
        return null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
