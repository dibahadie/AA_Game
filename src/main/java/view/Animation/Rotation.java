package view.Animation;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import view.GameMenus.GameMenu;

import java.util.Random;

public class Rotation {
    private static RotateTransition currentTransition;

    public static void pauseRotation(){
        currentTransition.pause();
    }

    public static void resumeRotation(){
        currentTransition.play();
    }
    public static void setFreezeRotation(double freezePause, StackPane perimeterCircles, int phase, GameMenu menu){
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
                    menu.scene.getRoot().requestFocus();
                case 2:
                case 3:
                case 4:
                    Rotation.setSecondPhaseRotation(perimeterCircles);
                    menu.scene.getRoot().requestFocus();
            }
        });
        transition.play();
        currentTransition.stop();
        currentTransition = transition;
        menu.scene.getRoot().requestFocus();
    }

    public static void setFirstPhaseRotation(StackPane perimeterCircles) {
        RotateTransition transition = new RotateTransition(Duration.seconds(4), perimeterCircles);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setAutoReverse(false);
        transition.setAxis(Rotate.Z_AXIS);
        transition.setByAngle(360);
        transition.play();
        currentTransition = transition;
    }

    public static void setSecondPhaseRotation(StackPane perimeterCircles){
        RotateTransition transition = new RotateTransition(Duration.seconds(4), perimeterCircles);
        transition.setCycleCount(1);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.setAutoReverse(false);
        transition.setAxis(Rotate.Z_AXIS);
        transition.setByAngle(360);
        Random random = new Random();
        transition.setOnFinished(event -> {
            transition.stop();
            transition.setCycleCount(random.nextInt(3));
            transition.setByAngle(transition.getByAngle() * (-1));
            transition.play();
        });
        transition.play();
        currentTransition.stop();
        currentTransition = transition;
    }
}
