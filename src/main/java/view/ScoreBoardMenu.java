package view;

import Controller.MainController;
import Controller.ScoreBoardController;
import Model.AA;
import Model.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import view.UserMenu.LoginMenu;

import java.net.URL;
import java.util.ArrayList;

public class ScoreBoardMenu extends Application {
    public static ScoreBoardController controller;
    public VBox ScoreBoardPain;
    public Button backButton;
    public VBox rankings;

    @Override
    public void start(Stage stage) throws Exception {
        ScoreBoardPain = FXMLLoader.load(
                new URL(LoginMenu.class.getResource("/fxml/ScoreboardFXML.fxml").toExternalForm()));
        Scene scene = new Scene(ScoreBoardPain);
        stage.setScene(scene);
        setStyle(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        addRankings();
        addBackButtonEvent();
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

    private void addRankings() {
        ArrayList<User> ranking = AA.getUserRanking();
        for (int i = 0; i < ranking.size() && i < 10; i++) {
            User user = ranking.get(i);
            HBox userRow = new HBox();
            userRow.setAlignment(Pos.CENTER);
            userRow.setSpacing(20);
            Text rank = new Text(Integer.toString(i+1));
            Text username = new Text(user.getUsername());
            Text highScore = new Text(Integer.toString(user.getHighScore()));
            Text recordTime = new Text(Integer.toString(user.getRecordTime()));
            if (i < 3){
                rank.setStyle("-fx-fill: green");
                username.setStyle("-fx-fill: green");
                highScore.setStyle("-fx-fill: green");
                recordTime.setStyle("-fx-fill: green");
            }
            userRow.getChildren().addAll(rank, username, highScore, recordTime);
            rankings.getChildren().add(userRow);
        }
    }

    private void addBackButtonEvent() {
        backButton.setOnMouseClicked(e -> {
            try {
                MainMenu.controller.run();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

}
