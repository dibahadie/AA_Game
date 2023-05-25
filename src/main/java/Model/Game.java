package Model;

public class Game {
    private int ballNumber = 20;
    private UserSetting.GameDifficulty difficulty;
    private double freezePause;
    private int score;
    public Game(User user){
        this.ballNumber = Integer.parseInt(user.getSetting().getBallNumber()) + 5;
        this.difficulty = UserSetting.GameDifficulty.valueOf(user.getSetting().getDifficulty().toUpperCase());
        this.freezePause = difficulty.iceModeTimer;
    }

    public int getBallNumber() {
        return ballNumber;
    }

    public UserSetting.GameDifficulty getDifficulty() {
        return difficulty;
    }

    public double getFreezePause() {
        return freezePause;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
