package Controller;

import Model.Game;
import Model.User;
import Model.UserSetting;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import view.Animation.EndGameAnimation;
import view.GameMenus.GameMenu;
import view.UserMenu.LoginMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {
    private final User currentUser;
    private final GameMenu menu;
    private final Game game;
    public boolean hasLost = false;
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
//        int allBalls = game.getBallNumber();
//        return (Math.PI * 100 / allBalls) * 0.8;
        return 10;
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
    public void changeMuteStatus (){
        currentUser.getSetting().changeMuteStatus();
        menu.music.setMute(currentUser.getSetting().isMute());
    }
    public void changeMusic(UserSetting.MusicNumber musicNumber){
        currentUser.getSetting().setMusicPath(musicNumber);
        menu.music.stop();
        menu.music = new MediaPlayer(new Media(musicNumber.getPath()));
        menu.music.setCycleCount(MediaPlayer.INDEFINITE);
        menu.music.setMute(GameMenu.controller.getCurrentUser().getSetting().isMute());
        menu.music.play();
    }

    public void updateScore(int phase){
        game.setScore(game.getScore() + phase);
        menu.updateScore(game.getScore());
    }

    public void lose(){
        if (!hasLost) {
            hasLost = true;
            EndGameAnimation animation = new EndGameAnimation(menu.blackCircle);
            animation.play();
            menu.openEndGamePopUp(false);
            endGame();
        }
    }

    public void win(){
        hasLost = false;
        EndGameAnimation animation = new EndGameAnimation(menu.blackCircle);
        animation.play();
        menu.openEndGamePopUp(true);
        endGame();
    }

    public void endGame(){
        hasLost = false;
        menu.losingTimeLine.stop();
        menu.timerTimeLine.stop();
    }

    public void setScore(int score, double time){
        if (currentUser.getHighScore() < score) {
            currentUser.setHighScore(score);
            currentUser.setRecordTime(time);
        }
    }
    public boolean hasLost(){
        return menu.throwingCirclesSingle.getChildren().size() != 0;
    }
}
