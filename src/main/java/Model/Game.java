package Model;

public class Game {
    private int ballNumber = 20;
    private UserSetting.GameDifficulty difficulty;
    public Game(User user){
        this.ballNumber = Integer.parseInt(user.getSetting().getBallNumber()) + 5;
        this.difficulty = UserSetting.GameDifficulty.valueOf(user.getSetting().getDifficulty().toUpperCase());
    }

    public int getBallNumber() {
        return ballNumber;
    }

    public UserSetting.GameDifficulty getDifficulty() {
        return difficulty;
    }
}
