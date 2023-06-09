package view.GameMenus;

import Controller.GameController;
import Model.Ball;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.Animation.Rotation;
import view.Animation.Transition;
import view.UserMenu.LoginMenu;


import java.net.URL;

public class GameMenu extends Application {
    public Scene scene;
    public static GameController controller;
    private static Transition transition;
    public Circle centerCircle, blackCircle;
    public Pane pane;
    public StackPane perimeterObjects, score, leftBalls, timer;
    public Group throwingCirclesSingle, throwingCirclesDouble;
    public ProgressBar progressBar;
    public Button pauseMenuButton, mute, changeMusic, restart, exit;
    public VBox pauseMenu, musicOptions, endGamePopUp;
    public MediaPlayer music;
    public Timeline losingTimeLine, timerTimeLine;
    public int phase = 1;
    public double horizontalOffset = 0;
    boolean isPauseMenuOpened = false;
    boolean multiplePlayer = false;
    int turn = 1;
    public GameMenu(boolean single){
        this.multiplePlayer = !single;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                new URL(LoginMenu.class.getResource("/fxml/GameFXML.fxml").toExternalForm()));
        loader.setController(this);
        pane = loader.load();

        GameMenuInitializer initializer = new GameMenuInitializer(this);
        if (!multiplePlayer) initializer.initializeSinglePlayer(pane);
        else initializer.initializeMultiplePlayer(pane);
        transition = new Transition(this);

        scene = new Scene(pane);
        setStyle(scene);
        addKeyEvents(scene);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.show();
    }

    private void setStyle(Scene scene) {
        if (controller.getCurrentUser().getSetting().isBlackAndWhite()) {
            scene.getStylesheets().add(
                    getClass().getResource("/CSS/BlackAndWhite/generalStyle.css").toExternalForm());
        } else {
            scene.getStylesheets().add(
                    getClass().getResource("/CSS/Normal/generalStyle.css").toExternalForm());
        }
    }


    private void updateProgressBar(KeyEvent e) {
        if (e.getCode() == KeyCode.SPACE || e.getCode() == KeyCode.ENTER) {
            progressBar.setProgress(progressBar.getProgress() + 0.1);
        }
        scene.getRoot().requestFocus();
    }

    private void addKeyEvents(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                throwBall(true);
                updatePhase();
            }
            else if(e.getCode() == KeyCode.ENTER){
                throwBall(false);
                updatePhase();
            }
            else if (e.getCode() == KeyCode.LEFT) {
                if (phase == 4){
                    transition.moveAllBallsHorizontal(-1, throwingCirclesSingle);
                    horizontalOffset--;
                }
            } else if (e.getCode() == KeyCode.RIGHT) {
                if (phase == 4) {
                    transition.moveAllBallsHorizontal(1, throwingCirclesSingle);
                    horizontalOffset++;
                }
            }
            else if (e.getCode() == KeyCode.TAB) {
                if (Math.abs(1 - progressBar.getProgress()) < 0.000001d) {
                    progressBar.setProgress(0);
                    Rotation.setFreezeRotation(controller.getGame().getFreezePause(), perimeterObjects, phase, this);
                }
            }
            updateProgressBar(e);
            scene.getRoot().requestFocus();
        });
    }

    private void throwBall(boolean single) {
        if (single && throwingCirclesSingle.getChildren().size() == 0) return;
        if (!single && throwingCirclesDouble.getChildren().size() == 0) return;
        Ball circle;
        if (single) circle = (Ball) throwingCirclesSingle.getChildren().get(0);
        else circle = (Ball) throwingCirclesDouble.getChildren().get(0);
        Group allBalls = single ? throwingCirclesSingle : throwingCirclesDouble;
        transition.moveAllBalls(controller.getRadius() * 2 + 5, allBalls, single);
        transition.throwBall(circle, single);
        controller.updateScore(phase);
        updateLeftBalls();
        playThroughBallSound();
        if (single && throwingCirclesSingle.getChildren().size() == 1) {
            controller.win();
        }
        else if (throwingCirclesSingle.getChildren().size() == 1 && throwingCirclesDouble.getChildren().size() == 1) {
            controller.win();
        }
    }

    public void playThroughBallSound() {
        MediaPlayer mediaPlayer = new MediaPlayer(
                new Media(getClass().getResource("/soundEffects/ballTap.wav").toExternalForm()));
        mediaPlayer.play();
    }

    public void setThrownBallCoordinate(StackPane circle, boolean single) {
        double x = horizontalOffset;
        double offset = 150 - Math.sqrt(22500 - x*x);
        double offsetAngle = Math.acos((150 - offset)/150);
        double rotationAngle = (90 - perimeterObjects.getRotate()) * Math.PI / 180;
        rotationAngle -= offsetAngle;
        if (!single) rotationAngle += Math.PI;
        circle.setTranslateX(Math.cos(rotationAngle) * 150);
        circle.setTranslateY(Math.sin(rotationAngle) * 150);
        perimeterObjects.getChildren().add(createLine(rotationAngle));
        perimeterObjects.getChildren().add(circle);
    }

    public Line createLine(double rotationAngle) {
        Line line = new Line();
        line.setTranslateX(Math.cos(rotationAngle) * 75);
        line.setTranslateY(Math.sin(rotationAngle) * 75);
        line.setScaleY(150);
        double angle = Math.atan(line.getTranslateY() / line.getTranslateX());
        angle *= 180 / Math.PI;
        line.setRotate(90 + angle);
        return line;
    }

    public Line createLine(Ball ball) {
        Line line = new Line();
        line.setTranslateX(ball.getTranslateX() / 2);
        line.setTranslateY(ball.getTranslateY() / 2);
        line.setScaleY(150);
        double angle = Math.atan(line.getTranslateY() / line.getTranslateX());
        angle *= 180 / Math.PI;
        line.setRotate(90 + angle);
        return line;
    }

    public void updatePhase() {
        int allBalls = controller.getGame().getBallNumber() - 5;
        int thrownBalls = allBalls - throwingCirclesSingle.getChildren().size() + 1;
        if (4 * thrownBalls >= allBalls && 4 * thrownBalls <= 2 * allBalls) {
            if (phase != 2) Rotation.setSecondPhaseRotation(perimeterObjects);
            transition.scaleBallTransition(perimeterObjects, centerCircle);
            phase = 2;
        } else if (4 * thrownBalls >= 2 * allBalls && 4 * thrownBalls <= 3 * allBalls && phase != 3) {
            transition.fadeTransition(perimeterObjects);
            phase = 3;
        } else if (4 * thrownBalls >= 3 * allBalls && phase != 4) {
            phase = 4;
        }
    }

    public void updateScore(int n) {
        ((Text) score.getChildren().get(2)).setText(Integer.toString(n));
    }

    public void updateLeftBalls() {
        int left = throwingCirclesSingle.getChildren().size() - 1;
        int all = controller.getGame().getBallNumber() - 5;
        ((Text) leftBalls.getChildren().get(2)).setText(Integer.toString(left));
        Circle scoreBall = (Circle) leftBalls.getChildren().get(0);
        if (left <= 5) {
            scoreBall.setFill(Color.LIGHTGREEN);
        } else if (left * 2 <= all) {
            scoreBall.setFill(Color.ORANGE);
        } else {
            scoreBall.setFill(Color.RED);
        }
    }

    public void openEndGamePopUp(boolean status) {
        String scoreStr = ((Text) score.getChildren().get(2)).getText();
        String timeSecond = ((Text) timer.getChildren().get(2)).getText();
        String timeMillisecond = ((Text) timer.getChildren().get(3)).getText();
        double time = Integer.parseInt(timeSecond) + Integer.parseInt(timeMillisecond) * 0.01;
        int score = Integer.parseInt(scoreStr);
        controller.setScore(score, time);
        if (status) {
            ((Text) endGamePopUp.getChildren().get(0)).setText("You won!\n" + "Your score is " + scoreStr +
                    "\nYour time is " + time);
        } else {
            ((Text) endGamePopUp.getChildren().get(0)).setText("You lost!\n");
        }
        endGamePopUp.setVisible(true);
        endGamePopUp.setManaged(true);
    }
}
