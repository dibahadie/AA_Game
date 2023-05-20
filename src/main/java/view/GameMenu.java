package view;

import Controller.GameController;
import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
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
    @FXML
    public Circle centerCircle, invisibleCircle;
    public Pane pane;
    public Group perimeterCircles, throwingCircles;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                new URL(LoginMenu.class.getResource("/fxml/GameFXML.fxml").toExternalForm()));
        loader.setController(this);
        pane = loader.load();

        initializeBalls(pane);
        initializeThrowingBalls(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void initializeBalls(Pane pane) {
        // TODO : can be set in the setting
        int ballNumber = controller.getGame().getBallNumber();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < ballNumber; i++) list.add(i);
        Collections.shuffle(list);

        centerCircle.setCenterX(300);
        centerCircle.setCenterY(200);
        invisibleCircle.setCenterX(300);
        invisibleCircle.setCenterY(200);

        perimeterCircles = new Group();

        double r = controller.getRadius();
        invisibleCircle.setVisible(false);
        invisibleCircle.setRadius(100 - r);

        for (int i = 0; i < 5; i++) {
            int j = list.get(i);
            Circle circle = new Circle();
            circle.setCenterX(centerCircle.getCenterX() + controller.getBallX(j));
            circle.setCenterY(centerCircle.getCenterY() + controller.getBallY(j));
            circle.setRadius(r);
            circle.setVisible(true);
            Line line = new Line(centerCircle.getCenterX(), centerCircle.getCenterY(),
                    circle.getCenterX(), circle.getCenterY());
            line.setVisible(true);
            addRotationMotion(line);
            addRotationMotion(circle);
            perimeterCircles.getChildren().addAll(circle, line);
        }

        pane.getChildren().add(perimeterCircles);
    }

    private void addRotationMotion(Shape shape) {
        Rotate rotate = new Rotate(360, centerCircle.getCenterX(), centerCircle.getCenterY());
        rotate.setAxis(Rotate.Z_AXIS);
        shape.getTransforms().add(rotate);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(4), new KeyValue(rotate.angleProperty(), 360)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.play();
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

            Text text = new Text(Integer.toString(i+1));
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


}
