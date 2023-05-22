package view;

import Controller.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import view.Animation.Rotation;
import view.Animation.Transition;
import view.UserMenu.LoginMenu;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class GameMenu extends Application {
    public static GameController controller;
    private static Transition transition;
    public Circle centerCircle, blackCircle;
    public Pane pane;
    public StackPane perimeterObjects;
    public Group throwingCircles;
    public ProgressBar progressBar;
    int phase = 1;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                new URL(LoginMenu.class.getResource("/fxml/GameFXML.fxml").toExternalForm()));
        loader.setController(this);
        pane = loader.load();

        transition = new Transition(this);
        initializeBalls(pane);
        initializeThrowingBalls(pane);
        initializeProgressBar(pane);

        Scene scene = new Scene(pane);
        addThrowBallEvent(scene);
        stage.setScene(scene);
        stage.show();
    }

    public void initializeBalls(Pane pane) {
        // TODO : can be set in the setting
       ArrayList<Integer> list = controller.getBallMap();

        centerCircle = new Circle();
        blackCircle = new Circle();
        centerCircle.setCenterX(300);
        blackCircle.setLayoutX(300);
        centerCircle.setCenterY(200);
        blackCircle.setLayoutY(170);
        blackCircle.setStyle("-fx-background-color : #000000");
        centerCircle.setRadius(50);
        blackCircle.setRadius(50);
        centerCircle.setVisible(false);

        perimeterObjects = new StackPane();

        double r = controller.getRadius();

        perimeterObjects.getChildren().add(centerCircle);
        for (int i = 0; i < 5; i++) {
            int j = list.get(i);
            Circle circle = new Circle();
            circle.setTranslateX(centerCircle.getTranslateX() + controller.getBallX(j));
            circle.setTranslateY(centerCircle.getTranslateY() + controller.getBallY(j));
            circle.setRadius(r);
            perimeterObjects.getChildren().addAll(circle, createLine(circle));
        }
        perimeterObjects.setLayoutY(120);
        perimeterObjects.setLayoutX(250);
        Rotation.setFirstPhaseRotation(perimeterObjects);
        pane.getChildren().add(perimeterObjects);
        pane.getChildren().add(blackCircle);
    }

    public Line createLine(Circle circle) {
        Line line = new Line();
        line.setTranslateX(circle.getTranslateX() / 2);
        line.setTranslateY(circle.getTranslateY() / 2);
        line.setScaleY(150);
        double angle = Math.atan(line.getTranslateY() / line.getTranslateX());
        angle *= 180 / Math.PI;
        line.setRotate(90 + angle);
        return line;
    }

    private void initializeProgressBar(Pane pane) {
        progressBar = new ProgressBar();
        progressBar.setLayoutY(10);
        progressBar.setLayoutX(10);
        progressBar.setOpacity(50);
        progressBar.setProgress(0);
        progressBar.setStyle("-fx-accent : #000000; -fx-box-border : black;");
        pane.getChildren().add(progressBar);
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
    }

    private void initializeThrowingBalls(Pane pane) {
        throwingCircles = new Group();
        double distance = centerCircle.getCenterY() + 150;
        double r = controller.getRadius();
        int ballNumber = controller.getGame().getBallNumber() - 5;
        for (int i = 0; i < ballNumber; i++) {
            Circle circle = new Circle();
            circle.setCenterX(centerCircle.getCenterX());
            circle.setCenterY(distance + (2 * r + 5) * i);
            circle.setRadius(r);

            Text text = new Text(Integer.toString(i + 1));
            text.setBoundsType(TextBoundsType.VISUAL);
            text.setStyle("-fx-fill : white;");
            text.setFont(new Font(11));

            StackPane stack = new StackPane();
            stack.getChildren().addAll(circle, text);
            stack.setLayoutX(centerCircle.getCenterX());
            stack.setLayoutY(distance + (2 * r + 5) * i);

            throwingCircles.getChildren().add(stack);
        }
        pane.getChildren().add(throwingCircles);
    }

    private void addThrowBallEvent(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                throwBall();
                updatePhase();
            }
            updateProgressBar(e);
        });
    }

    private void throwBall() {
        if (throwingCircles.getChildren().isEmpty()) {
            return;
        }
        // TODO : handle when there are no balls left
        StackPane circle = (StackPane) throwingCircles.getChildren().get(0);
        transition.moveAllBallsUp(controller.getRadius() * 2 + 5, throwingCircles);
        transition.throwBall(circle);
    }

    public void setThrownBallCoordinate(StackPane circle) {
        double rotationAngle = (90 - perimeterObjects.getRotate()) * Math.PI / 180;
        double secondAngle = (perimeterObjects.getRotate()) * Math.PI / 180;
        circle.setRotate(secondAngle);
        circle.setTranslateX(Math.cos(rotationAngle) * 150);
        circle.setTranslateY(Math.sin(rotationAngle) * 150);
        Line line = new Line();
        line.setTranslateX(Math.cos(rotationAngle) * 75);
        line.setTranslateY(Math.sin(rotationAngle) * 75);
        line.setScaleY(150);
        double angle = Math.atan(line.getTranslateY() / line.getTranslateX());
        angle *= 180 / Math.PI;
        line.setRotate(90 + angle);
        perimeterObjects.getChildren().add(line);
        perimeterObjects.getChildren().add(circle);
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
}
