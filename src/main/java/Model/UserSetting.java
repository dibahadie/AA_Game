package Model;

public class UserSetting {
    public UserSetting(){

    }
    public UserSetting(String difficulty, int ballNumber, boolean mute, boolean blackAndWhite, boolean english,
                       String mapNumber){
        this.difficulty = GameDifficulty.valueOf(difficulty.toUpperCase());
        this.ballNumber = ballNumber;
        this.english = english;
        this.blackAndWhite = blackAndWhite;
        this.mute = mute;
        this.mapNumber = MapNumber.valueOf(mapNumber.toUpperCase());
    }
    enum GameDifficulty{
        EASY(6, 1.2, 7),
        NORMAL(3, 1.5, 5),
        HARD(2, 1.8, 3);
        public double rotationSpeed, windSpeed, iceModeTimer;
        GameDifficulty(double rotationSpeed, double windSpeed, double iceModeTimer){
            this.iceModeTimer = iceModeTimer;
            this.rotationSpeed = rotationSpeed;
            this.windSpeed = windSpeed;
        }
    }

    enum MapNumber{
        MAP1, MAP2, MAP3, RANDOM
    }
    private GameDifficulty difficulty = GameDifficulty.EASY;
    private int ballNumber = 20;
    private boolean mute = false;
    private boolean blackAndWhite = false;
    private boolean english = false;
    private MapNumber mapNumber = MapNumber.RANDOM;


    public String getDifficulty() {
        return difficulty.name().toLowerCase();
    }

    public String getBallNumber() {
        return Integer.toString(ballNumber);
    }

    public void setBallNumber(int ballNumber) {
        this.ballNumber = ballNumber;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = GameDifficulty.valueOf(difficulty);
    }

    public void setBlackAndWhite(boolean blackAndWhite) {
        this.blackAndWhite = blackAndWhite;
    }

    public void setEnglish(boolean english) {
        this.english = english;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public boolean isEnglish() {
        return english;
    }

    public boolean isBlackAndWhite() {
        return blackAndWhite;
    }

    public boolean isMute() {
        return mute;
    }

    public String getMapNumber() {
        return mapNumber.name().toLowerCase();
    }
}
