package Model;

import Controller.UserController.UserManager;
import view.GameMenu.GameMenu;

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
    public enum GameDifficulty{
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

    public enum MusicNumber{
        NUMBER_1(GameMenu.class.getResource("/music/AprilRain.mp3").toExternalForm()),
        NUMBER_2(GameMenu.class.getResource("/music/Piazzolla.mp3").toExternalForm()),
        NUMBER_3(GameMenu.class.getResource("/music/songOfGoldenDragon.mp3").toExternalForm());
        private final String path;
        MusicNumber(String path){
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
    public enum MapNumber{
        MAP1, MAP2, MAP3, RANDOM
    }
    private GameDifficulty difficulty = GameDifficulty.EASY;
    private int ballNumber = 20;
    private boolean mute = false;
    private boolean blackAndWhite = false;
    private boolean english = false;
    private MapNumber mapNumber = MapNumber.RANDOM;
    private MusicNumber musicPath = MusicNumber.NUMBER_1;

    public String getDifficulty() {
        return difficulty.name().toLowerCase();
    }

    public String getBallNumber() {
        return Integer.toString(ballNumber);
    }

    public void setBallNumber(int ballNumber) {
        this.ballNumber = ballNumber;
        UserManager.updateUsers();
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = GameDifficulty.valueOf(difficulty);
        UserManager.updateUsers();
    }

    public void setBlackAndWhite(boolean blackAndWhite) {
        this.blackAndWhite = blackAndWhite;
        UserManager.updateUsers();
    }

    public void changeLanguage(boolean english) {
        this.english = english;
        UserManager.updateUsers();
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
    public void changeMuteStatus(){
        mute = !mute;
        UserManager.updateUsers();
    }

    public MusicNumber getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(MusicNumber musicPath) {
        this.musicPath = musicPath;
    }
}
