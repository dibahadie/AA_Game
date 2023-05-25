package view.GameMenu;

import Model.Ball;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;
import view.Animation.Rotation;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static Model.UserSetting.MusicNumber.*;


public class GameMenuInitializer {
    private GameMenu menu;

    public GameMenuInitializer(GameMenu menu) {
        this.menu = menu;
    }

    public void initializeSinglePlayer(Pane pane) {
        initializeBalls(pane);
        initializeThrowingBalls(pane);
        initializeProgressBar(pane);
        initializePauseMenu(pane);
        initializeChangeMusicMenu(pane);
        initializePauseOptions();
        menu.score = initializeCirclePane(pane, 30, "0", "Your Score");
        menu.leftBalls = initializeCirclePane(pane, 52,
                Integer.toString(menu.throwingCircles.getChildren().size()), "Left Balls   ");
        Circle scoreBall = (Circle) menu.leftBalls.getChildren().get(0);
        scoreBall.setFill(Color.RED);
        setTimer(pane);
        playMusic();
    }

    public void initializeBalls(Pane pane) {
        ArrayList<Integer> list = menu.controller.getBallMap();

        menu.centerCircle = new Circle();
        menu.blackCircle = new Circle();
        menu.centerCircle.setCenterX(300);
        menu.blackCircle.setLayoutX(300);
        menu.centerCircle.setCenterY(200);
        menu.blackCircle.setLayoutY(170);
        menu.blackCircle.setStyle("-fx-background-color : #000000");
        menu.centerCircle.setRadius(50);
        menu.blackCircle.setRadius(50);
        menu.centerCircle.setVisible(false);

        menu.perimeterObjects = new StackPane();

        double r = GameMenu.controller.getRadius();

        menu.perimeterObjects.getChildren().add(menu.centerCircle);
        for (int i = 0; i < 5; i++) {
            int j = list.get(i);
            double tranX = menu.centerCircle.getTranslateX() + GameMenu.controller.getBallX(j);
            double tranY = menu.centerCircle.getTranslateY() + GameMenu.controller.getBallY(j);
            Ball ball = new Ball(r, "", tranX, tranY);
            menu.perimeterObjects.getChildren().addAll(ball, menu.createLine(ball));
        }
        menu.perimeterObjects.setLayoutY(120);
        menu.perimeterObjects.setLayoutX(250);
        Rotation.setFirstPhaseRotation(menu.perimeterObjects);
        pane.getChildren().add(menu.perimeterObjects);
        pane.getChildren().add(menu.blackCircle);
    }

    public void initializeProgressBar(Pane pane) {
        menu.progressBar = new ProgressBar();
        menu.progressBar.setLayoutY(10);
        menu.progressBar.setLayoutX(10);
        menu.progressBar.setOpacity(50);
        menu.progressBar.setProgress(0);
        menu.progressBar.setStyle("-fx-accent : #000000; -fx-box-border : black;");
        pane.getChildren().add(menu.progressBar);
    }

    public void initializeThrowingBalls(Pane pane) {
        menu.throwingCircles = new Group();
        double distance = menu.centerCircle.getCenterY() + 150;
        double r = GameMenu.controller.getRadius();
        int ballNumber = GameMenu.controller.getGame().getBallNumber() - 5;
        for (int i = 0; i < ballNumber; i++) {
            Ball ball = new Ball(r, Integer.toString(i + 1),
                    menu.centerCircle.getCenterX(), distance + (2 * r + 5) * i);
            menu.throwingCircles.getChildren().add(ball);
        }
        pane.getChildren().add(menu.throwingCircles);
    }

    public void initializePauseMenu(Pane pane) {
        Button pause = new Button();
        pause.setText("pause");
        pause.setLayoutX(510);
        pause.setLayoutY(8);
        pause.setMinWidth(85);
        VBox vBox = new VBox();
        vBox.setLayoutX(510);
        vBox.setLayoutY(38);
        vBox.setSpacing(5);
        vBox.setVisible(false);
        vBox.setManaged(false);
        menu.pauseMenu = vBox;
        menu.pauseMenuButton = pause;
        pane.getChildren().add(vBox);
        pane.getChildren().add(pause);
        pause.setOnMouseClicked(e -> {
            if (pause.getText().equals("pause")) pause.setText("resume");
            else pause.setText("pause");
            if (menu.isPauseMenuOpened) {
                setNodeVisibility(menu.pauseMenu, false);
                menu.isPauseMenuOpened = false;
                menu.scene.getRoot().requestFocus();
            } else {
                setNodeVisibility(menu.pauseMenu, true);
                menu.isPauseMenuOpened = true;
                menu.scene.getRoot().requestFocus();
            }
        });
    }

    public void initializePauseOptions() {
        Button mute = new Button();
        if (GameMenu.controller.getCurrentUser().getSetting().isMute())
            mute.setText("mute");
        else mute.setText("unmute");
        mute.setMinWidth(85);
        mute.setOnMouseClicked(e -> {
            GameMenu.controller.changeMuteStatus();
            if (mute.getText().equals("mute")) mute.setText("unmute");
            else mute.setText("mute");
        });
        menu.mute = mute;
        Button changeMusic = new Button("changeMusic");
        changeMusic.setMinWidth(85);
        menu.pauseMenu.getChildren().addAll(mute, changeMusic);
        menu.changeMusic = changeMusic;
        changeMusic.setOnMouseClicked(e -> setNodeVisibility(menu.musicOptions, !menu.musicOptions.isVisible()));
    }

    public void initializeChangeMusicMenu(Pane pane) {
        VBox vBox = new VBox();
        vBox.setLayoutX(405);
        vBox.setLayoutY(68);
        vBox.setSpacing(5);
        setNodeVisibility(vBox, false);
        Button firstMusic = new Button("firstMusic");
        firstMusic.setMinWidth(100);
        Button secondMusic = new Button("secondMusic");
        secondMusic.setMinWidth(100);
        Button thirdMusic = new Button("thirdMusic");
        thirdMusic.setMinWidth(100);
        vBox.getChildren().addAll(firstMusic, secondMusic, thirdMusic);
        pane.getChildren().add(vBox);
        menu.musicOptions = vBox;
        firstMusic.setOnMouseClicked(e -> GameMenu.controller.changeMusic(NUMBER_1));
        secondMusic.setOnMouseClicked(e -> GameMenu.controller.changeMusic(NUMBER_2));
        thirdMusic.setOnMouseClicked(e -> GameMenu.controller.changeMusic(NUMBER_3));
    }

    private void setNodeVisibility(Node node, boolean visibility) {
        node.setVisible(visibility);
        node.setManaged(visibility);
    }

    public StackPane initializeCirclePane(Pane pane, int layoutY, String ballInitial, String textStr) {
        StackPane stackPane = new StackPane();
        stackPane.setLayoutY(layoutY);
        stackPane.setLayoutX(0);
        Circle circle = new Circle();
        circle.setRadius(10);
        circle.setTranslateX(70);
        Text ballText = new Text(ballInitial);
        ballText.setTranslateX(70);
        ballText.setStyle("-fx-fill: white");
        Text text = new Text(textStr);
        text.setTranslateX(10);
        stackPane.getChildren().addAll(circle, text, ballText);
        pane.getChildren().add(stackPane);
        return stackPane;
    }

    public void initializeTimer(Pane pane) {
        StackPane timePane = new StackPane();
        timePane.setLayoutY(74);
        timePane.setLayoutX(0);
        Circle secondCircle = new Circle();
        Circle milliCircle = new Circle();
        secondCircle.setRadius(10);
        secondCircle.setTranslateX(60);
        milliCircle.setRadius(10);
        milliCircle.setTranslateX(85);
        Text ballTextSecond = new Text("99");
        ballTextSecond.setTranslateX(60);
        ballTextSecond.setStyle("-fx-fill: white");
        Text ballTextMilli = new Text("99");
        ballTextMilli.setTranslateX(85);
        ballTextMilli.setStyle("-fx-fill: white");
        Text dot = new Text(" : ");
        dot.setTranslateX(72);
        Text timeText = new Text("Time");
        timeText.setTranslateX(10);
        timePane.getChildren().addAll(secondCircle, milliCircle, ballTextSecond, ballTextMilli, dot, timeText);
        pane.getChildren().add(timePane);
        menu.timer = timePane;
    }

    public void setTimer(Pane pane) {
        initializeTimer(pane);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (GameMenu.controller.hasLost())
                    GameMenu.controller.lose();
            }
        };
        timer.schedule(timerTask, 99000);
        Text timerText = (Text) menu.timer.getChildren().get(2);
        Text milliTimerText = (Text) menu.timer.getChildren().get(3);
        Timeline milliTimeLine = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
            int time = Integer.parseInt(milliTimerText.getText());
            time--;
            milliTimerText.setText(Integer.toString(time));
        }));
        milliTimeLine.setCycleCount(100);
        milliTimeLine.setOnFinished(actionEvent -> {
            milliTimerText.setText("99");
            int time = Integer.parseInt(timerText.getText());
            time--;
            timerText.setText(Integer.toString(time));
            milliTimeLine.play();
        });
        milliTimeLine.play();
    }

    public void playMusic() {
        MediaPlayer music = new MediaPlayer(
                new Media(GameMenu.controller.getCurrentUser().getSetting().getMusicPath().getPath()));
        music.setCycleCount(MediaPlayer.INDEFINITE);
        music.setMute(GameMenu.controller.getCurrentUser().getSetting().isMute());
        music.play();
        menu.music = music;
    }
}
