package view.GameMenu;

import Controller.GameController;
import Model.Ball;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
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
import javafx.util.Duration;
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
    public Group throwingCircles;
    public ProgressBar progressBar;
    public Button pauseMenuButton, mute, changeMusic;
    public VBox pauseMenu, musicOptions;
    public MediaPlayer music;
    int phase = 1;
    boolean isPauseMenuOpened = false;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                new URL(LoginMenu.class.getResource("/fxml/GameFXML.fxml").toExternalForm()));
        loader.setController(this);
        pane = loader.load();

        GameMenuInitializer initializer = new GameMenuInitializer(this);
        initializer.initializeSinglePlayer(pane);
        transition = new Transition(this);

        scene = new Scene(pane);
        scene.getRoot().requestFocus();
        setStyle(scene);
        addThrowBallEvent(scene);
        stage.setScene(scene);
        stage.show();
    }

    private void setStyle(Scene scene){
        if (controller.getCurrentUser().getSetting().isBlackAndWhite()){
            scene.getStylesheets().add(
                    getClass().getResource("/CSS/BlackAndWhite/generalStyle.css").toExternalForm());
        } else {
            scene.getStylesheets().add(
                    getClass().getResource("/CSS/Normal/generalStyle.css").toExternalForm());
        }
    }



    private void updateProgressBar(KeyEvent e) {
        if (e.getCode() == KeyCode.SPACE) {
            progressBar.setProgress(progressBar.getProgress() + 0.1);
        }
        if (e.getCode() == KeyCode.TAB) {
            if (Math.abs(1 - progressBar.getProgress()) < 0.000001d) {
                progressBar.setProgress(0);
                Rotation.setFreezeRotation(controller.getGame().getFreezePause(), perimeterObjects, phase);
            }
        }
        scene.getRoot().requestFocus();
    }

    private void addThrowBallEvent(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                throwBall();
                updatePhase();
            }
            updateProgressBar(e);
            scene.getRoot().requestFocus();
        });
    }

    private void throwBall() {
        if (throwingCircles.getChildren().isEmpty()) {
            return;
        }
        // TODO : handle when there are no balls left
        Ball circle = (Ball) throwingCircles.getChildren().get(0);
        transition.moveAllBallsUp(controller.getRadius() * 2 + 5, throwingCircles);
        transition.throwBall(circle);
        controller.updateScore(phase);
        updateLeftBalls();
        playThroughBallSound();
    }

    public void playThroughBallSound(){
        MediaPlayer mediaPlayer = new MediaPlayer(
                new Media(getClass().getResource("/soundEffects/ballTap.wav").toExternalForm()));
        mediaPlayer.play();
    }

    public void setThrownBallCoordinate(StackPane circle) {
        double rotationAngle = (90 - perimeterObjects.getRotate()) * Math.PI / 180;
        circle.setTranslateX(Math.cos(rotationAngle) * 150);
        circle.setTranslateY(Math.sin(rotationAngle) * 150);
        perimeterObjects.getChildren().add(createLine(rotationAngle));
        perimeterObjects.getChildren().add(circle);
    }

    public Line createLine(double rotationAngle){
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
        int thrownBalls = allBalls - throwingCircles.getChildren().size() + 1;
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

    public void updateScore(int n){
        ((Text) score.getChildren().get(2)).setText(Integer.toString(n));
    }
    public void updateLeftBalls(){
        int left = throwingCircles.getChildren().size() - 1;
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

    public void checkLoseStatus(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
            for (Node node : perimeterObjects.getChildren()){

            }
        }));
    }
}
