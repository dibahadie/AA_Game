package Model;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class Ball extends StackPane {
    private Text text;
    private Circle circle;
    public Ball(double radius, String text, double TransX, double TransY){
        this.text = new Text(text);
        this.circle = new Circle();
        this.circle.setRadius(radius);
        this.getChildren().addAll(this.text, this.circle);
        this.setTranslateX(TransX);
        this.setTranslateY(TransY);
        this.text.setBoundsType(TextBoundsType.VISUAL);
        this.text.setStyle("-fx-fill : white;");
        this.text.setFont(new Font(11));
    }

    public Circle getCircle(){
        return circle;
    }

    public Text getText() {
        return text;
    }
}
