package view.Animation;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import view.GameMenu;

public class Transition {
    private GameMenu menu;
    public Transition(GameMenu menu){
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

    public void throwBall(StackPane circle){
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.2), circle);
        transition.setByY((-1) * 30);
        transition.setCycleCount(1);
        transition.play();
        transition.setOnFinished(e -> {
            menu.throwingCircles.getChildren().remove(circle);
            circle.getChildren().add(menu.createLine((Circle) circle.getChildren().get(0)));
            menu.setThrownBallCoordinate(circle);
        });
    }

    public void scaleBallTransition(StackPane perimeterBalls, Circle centerCircle){
        for (Node ball : perimeterBalls.getChildren()){
            if (!(ball instanceof Line) && !(ball.equals(centerCircle))){
                ScaleTransition transition = new ScaleTransition(Duration.seconds(1), ball);
                transition.setFromX(1);
                transition.setFromY(1);
                transition.setToX(1.1);
                transition.setToY(1.1);
                transition.setAutoReverse(false);
                transition.setCycleCount(1);
                transition.setOnFinished(event -> {
                    if (transition.getFromX() == 1) {
                        transition.setFromX(1.1);
                        transition.setFromY(1.1);
                        transition.setToX(1);
                        transition.setToY(1);
                    }else{
                        transition.setFromX(1);
                        transition.setFromY(1);
                        transition.setToX(1.1);
                        transition.setToY(1.1);
                    }
                    transition.play();
                });
                transition.play();
            }
        }
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
