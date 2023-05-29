package view.Animation;

import javafx.animation.Transition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import view.GameMenu.GameMenu;

public class EndGameAnimation extends Transition {
    private Circle circle;
    public EndGameAnimation(Circle circle){
        this.circle = circle;
        this.setCycleCount(1);
        this.setAutoReverse(false);
        this.setCycleDuration(Duration.seconds(0.5));
        if (GameMenu.controller.hasLost)
            circle.setStyle("-fx-fill: red");
        else circle.setStyle("-fx-fill: lightgreen");
    }
    @Override
    protected void interpolate(double v) {
        circle.setScaleX(1 + 7 * v);
        circle.setScaleY(1 + 7 * v);
    }
}
