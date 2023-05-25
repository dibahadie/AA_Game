package view.Animation;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import view.GameMenu.GameMenu;

import java.util.Random;

public class Rotation {
    public static void setFreezeRotation(double freezePause, StackPane perimeterCircles, int phase){
        RotateTransition transition = new RotateTransition(Duration.seconds(8), perimeterCircles);
        int cycleCount = (int) (freezePause/8);
        transition.setCycleCount(cycleCount);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setAutoReverse(false);
        transition.setAxis(Rotate.Z_AXIS);
        transition.setByAngle(360);
        transition.setOnFinished(event -> {
            switch (phase){
                case 1 :
                    Rotation.setFirstPhaseRotation(perimeterCircles);
                case 2:
                case 3:
                case 4:
                    Rotation.setSecondPhaseRotation(perimeterCircles);
            }
        });
        transition.play();
    }

    public static void setFirstPhaseRotation(StackPane perimeterCircles) {
        RotateTransition transition = new RotateTransition(Duration.seconds(4), perimeterCircles);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setAutoReverse(false);
        transition.setAxis(Rotate.Z_AXIS);
        transition.setByAngle(360);
        transition.play();
    }

    public static void setSecondPhaseRotation(StackPane perimeterCircles){
        RotateTransition transition = new RotateTransition(Duration.seconds(4), perimeterCircles);
        transition.setCycleCount(1);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setAutoReverse(false);
        transition.setAxis(Rotate.Z_AXIS);
        transition.setByAngle(360);
        transition.play();
        Random random = new Random();
        transition.setOnFinished(event -> {
            transition.stop();
            transition.setCycleCount(random.nextInt(3));
            transition.setByAngle(transition.getByAngle() * (-1));
            transition.play();
        });
        transition.play();
    }
}
