package view;

import Controller.GameController;
import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.UserMenu.LoginMenu;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class GameMenu extends Application {
    public static GameController controller;
    public Circle centerCircle;
    public Pane pane;
    public StackPane perimeterCircles;
    public Group throwingCircles;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                new URL(LoginMenu.class.getResource("/fxml/GameFXML.fxml").toExternalForm()));
        loader.setController(this);
        pane = loader.load();

        initializeBalls(pane);
        initializeThrowingBalls(pane);

        Scene scene = new Scene(pane);
        addThrowBallEvent(scene);
        stage.setScene(scene);
        stage.show();
    }

    public void initializeBalls(Pane pane) {
        // TODO : can be set in the setting
        int ballNumber = controller.getGame().getBallNumber();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < ballNumber; i++) list.add(i);
        Collections.shuffle(list);

        centerCircle = new Circle();
        centerCircle.setCenterX(300);
        centerCircle.setCenterY(200);
        centerCircle.setStyle("-fx-background-color : #000000");
        centerCircle.setRadius(50);

        perimeterCircles = new StackPane();

        double r = controller.getRadius();

        perimeterCircles.getChildren().add(centerCircle);
        for (int i = 0; i < 5; i++) {
            int j = list.get(i);
            Circle circle = new Circle();
            circle.setTranslateX(centerCircle.getTranslateX() + controller.getBallX(j));
            circle.setTranslateY(centerCircle.getTranslateY() + controller.getBallY(j));
            circle.setRadius(r);
            perimeterCircles.getChildren().addAll(circle, createLine(circle));
        }

        perimeterCircles.setLayoutY(80);
        perimeterCircles.setLayoutX(250);

        RotateTransition transition = new RotateTransition(Duration.seconds(4), perimeterCircles);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setAutoReverse(false);
        transition.setAxis(Rotate.Z_AXIS);
        transition.setByAngle(360);
        transition.play();
        pane.getChildren().add(perimeterCircles);
    }

    private Line createLine(Circle circle) {
        Line line = new Line();
        line.setTranslateX(circle.getTranslateX() / 2);
        line.setTranslateY(circle.getTranslateY() / 2);
        line.setScaleY(100);
        double angle = Math.atan(line.getTranslateY() / line.getTranslateX());
        angle *= 180 / Math.PI;
        line.setRotate(90 + angle);
        return line;
    }

    private void initializeThrowingBalls(Pane pane) {
        throwingCircles = new Group();
        double distance = centerCircle.getCenterY() + 100;
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
            }
        });
    }

    private void throwBall() {
        if (throwingCircles.getChildren().isEmpty()) {
            return;
        }
        // TODO : handle when there are no balls left
        StackPane circle = (StackPane) throwingCircles.getChildren().get(0);
        double d = controller.getRadius() * 2 + 5;
        for (Node child : throwingCircles.getChildren()) {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.2), child);
            transition.setByY((-1) * d);
            transition.setCycleCount(1);
            transition.play();
        }
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.2), circle);
        transition.setByY((-1) * 60);
        transition.setCycleCount(1);
        transition.play();
        transition.setOnFinished(e -> {
            throwingCircles.getChildren().remove(circle);
            circle.getChildren().add(createLine((Circle) circle.getChildren().get(0)));
            setThrownBallCoordinate(circle);
        });
    }

    private void setThrownBallCoordinate(StackPane circle) {
        double rotationAngle = (90 - perimeterCircles.getRotate()) * Math.PI / 180;
        double secondAngle = (perimeterCircles.getRotate()) * Math.PI / 180;
        circle.setRotate(secondAngle);
        circle.setTranslateX(Math.cos(rotationAngle) * 100);
        circle.setTranslateY(Math.sin(rotationAngle) * 100);
        Line line = new Line();
        line.setTranslateX(Math.cos(rotationAngle) * 50);
        line.setTranslateY(Math.sin(rotationAngle) * 50);
        line.setScaleY(100);
        double angle = Math.atan(line.getTranslateY() / line.getTranslateX());
        angle *= 180 / Math.PI;
        line.setRotate(90 + angle);
        perimeterCircles.getChildren().add(line);
        perimeterCircles.getChildren().add(circle);
    }
}
