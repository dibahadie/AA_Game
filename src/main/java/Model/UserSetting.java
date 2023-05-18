package Model;

public class UserSetting {
    enum GameDifficulty{
        EASY,
        NORMAL,
        HARD
    }
    private GameDifficulty difficulty = GameDifficulty.EASY;
    private int ballNumber = 20;

    public String getDifficulty() {
        return difficulty.name().toLowerCase();
    }

    public int getBallNumber() {
        return ballNumber;
    }

    public void setBallNumber(int ballNumber) {
        this.ballNumber = ballNumber;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = GameDifficulty.valueOf(difficulty);
    }
}
