package view.GameMenus;

import Controller.GameController;
import Controller.ScoreBoardController;
import Model.Ball;
import Model.Game;
import Model.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
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
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import view.Animation.Rotation;
import view.MainMenu;
import view.ScoreBoardMenu;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static Model.UserSetting.MusicNumber.*;


public class GameMenuInitializer {
    private GameMenuSinglePlayer menu;

    public GameMenuInitializer(GameMenuSinglePlayer menu) {
        this.menu = menu;
    }

    public void initializeSinglePlayer(Pane pane) {
        initializeBalls(pane);
        initializeThrowingBalls(pane);
        initializeProgressBar(pane);
        initializePauseMenu(pane);
        initializeChangeMusicMenu(pane);
        initializePauseOptions();
        initializeEndGamePopup(pane);
        menu.score = initializeCirclePane(pane, 30, "0", "Your Score");
        menu.leftBalls = initializeCirclePane(pane, 52,
                Integer.toString(menu.throwingCircles.getChildren().size()), "Left Balls   ");
        Circle scoreBall = (Circle) menu.leftBalls.getChildren().get(0);
        scoreBall.setFill(Color.RED);
        setTimer(pane);
        playMusic();
        checkLoseStatus();
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

        double r = GameMenuSinglePlayer.controller.getRadius();

        menu.perimeterObjects.getChildren().add(menu.centerCircle);
        for (int i = 0; i < 5; i++) {
            int j = list.get(i);
            double tranX = menu.centerCircle.getTranslateX() + GameMenuSinglePlayer.controller.getBallX(j);
            double tranY = menu.centerCircle.getTranslateY() + GameMenuSinglePlayer.controller.getBallY(j);
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
        double r = GameMenuSinglePlayer.controller.getRadius();
        int ballNumber = GameMenuSinglePlayer.controller.getGame().getBallNumber() - 5;
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
            } else {
                setNodeVisibility(menu.pauseMenu, true);
                menu.isPauseMenuOpened = true;
            }
            menu.scene.getRoot().requestFocus();
        });
    }

    public void initializePauseOptions() {
        Button mute = new Button();
        if (GameMenuSinglePlayer.controller.getCurrentUser().getSetting().isMute())
            mute.setText("mute");
        else mute.setText("unmute");
        mute.setMinWidth(85);
        mute.setOnMouseClicked(e -> {
            GameMenuSinglePlayer.controller.changeMuteStatus();
            if (mute.getText().equals("mute")) mute.setText("unmute");
            else mute.setText("mute");
        });
        menu.mute = mute;
        menu.pauseMenu.getChildren().add(mute);

        Button changeMusic = new Button("changeMusic");
        changeMusic.setMinWidth(85);
        menu.pauseMenu.getChildren().add(changeMusic);
        menu.changeMusic = changeMusic;
        changeMusic.setOnMouseClicked(e -> setNodeVisibility(menu.musicOptions, !menu.musicOptions.isVisible()));

        Button restart = new Button("restart");
        restart.setMinWidth(85);
        menu.pauseMenu.getChildren().add(restart);
        menu.restart = restart;
        restart.setOnMouseClicked(e -> {
            GameMenuSinglePlayer gameMenuSinglePlayer = new GameMenuSinglePlayer();
            User currentUser = GameMenuSinglePlayer.controller.getCurrentUser();
            GameController gameController = new GameController(currentUser, gameMenuSinglePlayer, new Game(currentUser));
            try {
                gameController.run();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
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
        firstMusic.setOnMouseClicked(e -> GameMenuSinglePlayer.controller.changeMusic(NUMBER_1));
        secondMusic.setOnMouseClicked(e -> GameMenuSinglePlayer.controller.changeMusic(NUMBER_2));
        thirdMusic.setOnMouseClicked(e -> GameMenuSinglePlayer.controller.changeMusic(NUMBER_3));
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
                GameMenuSinglePlayer.controller.lose();
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
        menu.timerTimeLine = milliTimeLine;
    }

    public void playMusic() {
        MediaPlayer music = new MediaPlayer(
                new Media(GameMenuSinglePlayer.controller.getCurrentUser().getSetting().getMusicPath().getPath()));
        music.setCycleCount(MediaPlayer.INDEFINITE);
        music.setMute(GameMenuSinglePlayer.controller.getCurrentUser().getSetting().isMute());
        music.play();
        menu.music = music;
    }

    public void checkLoseStatus() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
            if (!GameMenuSinglePlayer.controller.hasLost) {
                for (Node node : menu.perimeterObjects.getChildren()) {
                    if (node instanceof Ball) {
                        for (Node node1 : menu.perimeterObjects.getChildren()) {
                            if (node1 instanceof Ball && !node1.equals(node)) {
                                Circle circle1 = ((Ball) node).getCircle();
                                Circle circle2 = ((Ball) node1).getCircle();
                                double dx = node.getTranslateX() - node1.getTranslateX();
                                double dy = node.getTranslateY() - node1.getTranslateY();
                                double d = Math.sqrt(dx * dx + dy * dy);
                                if (d < (circle1.getRadius() + circle2.getRadius())) {
                                    GameMenuSinglePlayer.controller.lose();
                                }
                            }
                        }
                    }
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        menu.losingTimeLine = timeline;
    }

    public void initializeEndGamePopup(Pane pane) {
        VBox popUpPane = new VBox();
        popUpPane.setAlignment(Pos.CENTER);
        popUpPane.setMinWidth(200);
        popUpPane.setMinHeight(200);
        Button mainMenu = new Button("Main Menu");
        Button scoreBoard = new Button("ScoreBoard");
        mainMenu.setPrefWidth(100);
        scoreBoard.setPrefWidth(100);
        Text text = new Text();
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(new Font(13));
        mainMenu.setOnMouseClicked(e -> {
            try {
                MainMenu.controller.run();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        scoreBoard.setOnMouseClicked(e -> {
            try {
                ScoreBoardMenu scoreBoardMenu = new ScoreBoardMenu();
                ScoreBoardMenu.controller = new
                        ScoreBoardController(GameMenuSinglePlayer.controller.getCurrentUser(), scoreBoardMenu);
                ScoreBoardMenu.controller.run();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        popUpPane.getChildren().addAll(text, scoreBoard, mainMenu);
        popUpPane.setSpacing(5);
        popUpPane.setStyle("-fx-background-color: white; -fx-border-color: black");
        popUpPane.setLayoutY(100);
        popUpPane.setLayoutX(200);
        setNodeVisibility(popUpPane, false);
        pane.getChildren().add(popUpPane);
        menu.endGamePopUp = popUpPane;
    }
}
