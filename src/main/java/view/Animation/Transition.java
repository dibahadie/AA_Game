package view.Animation;

import Model.Ball;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import view.GameMenus.GameMenuSinglePlayer;

public class Transition {
    private GameMenuSinglePlayer menu;
    public Transition(GameMenuSinglePlayer menu){
        this.menu = menu;
    }
    public void moveAllBallsUp(double d, Group throwingCircles){
        for (Node child : throwingCircles.getChildren()) {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.2), child);
            transition.setByY((-1) * d);
            transition.setCycleCount(1);
            transition.play();
        }
    }

    public void moveAllBallsHorizontal(double d, Group throwingCircles){
        for (Node child : throwingCircles.getChildren()) {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.01), child);
            transition.setByX(d);
            transition.setCycleCount(1);
            transition.play();
        }
    }

    public void throwBall(Ball circle){
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.2), circle);
        double x = menu.horizontalOffset;
        if (x > 150){
            transition.setByY(-350);

        } else {
            double offset = 150 - Math.sqrt(22500 - x*x);
            transition.setByY((-1) * 30 - offset);
        }
        transition.setCycleCount(1);
        transition.play();
        transition.setOnFinished(e -> {
            menu.throwingCircles.getChildren().remove(circle);
            if (x <= 150) {
                menu.setThrownBallCoordinate(circle);
            }
            else {
                menu.pane.getChildren().add(circle);
                GameMenuSinglePlayer.controller.lose();
            }
        });
    }

    public void scaleBallTransition(StackPane perimeterBalls, Circle centerCircle){
        for (Node ball : perimeterBalls.getChildren()){
            if (!(ball instanceof Line) && !(ball.equals(centerCircle))){
                addScaleTransitionToBall(ball);
            }
        }
    }

    public void addScaleTransitionToBall(Node node){
//        TODO : fix this
        ScaleTransition transition = new ScaleTransition(Duration.seconds(1), node);
        transition.setFromX(1);
        transition.setFromY(1);
        transition.setToX(1.2);
        transition.setToY(1.2);
        transition.setAutoReverse(false);
        transition.setCycleCount(1);
        transition.setOnFinished(event -> {
            if (transition.getFromX() == 1) {
                transition.setFromX(1);
                transition.setFromY(1);
                transition.setToX(0.833);
                transition.setToY(0.833);
            }else{
                transition.setFromX(1);
                transition.setFromY(1);
                transition.setToX(1.2);
                transition.setToY(1.2);
            }
            transition.play();
        });
        transition.play();
    }
    public void fadeTransition(StackPane perimeterCircles){
        FadeTransition transition = new FadeTransition();
        transition.setNode(perimeterCircles);
        transition.setDuration(Duration.seconds(1));
        transition.setCycleCount(1);
        transition.setFromValue(1);
        transition.setToValue(0);
        transition.setAutoReverse(false);
        transition.setOnFinished(e -> {
            if (transition.getFromValue() == 1){
                transition.setFromValue(0);
                transition.setToValue(1);
            } else{
                transition.setFromValue(1);
                transition.setToValue(0);
            }
            transition.play();
        });
        transition.play();
    }
}
