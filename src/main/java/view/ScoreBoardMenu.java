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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
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
    @FXML
    public TableView rankings;
    @FXML
    public VBox ScoreBoardPain;
    @FXML
    public Button backButton;

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
//        TODO : different graphic for top 5
        ArrayList<User> ranking = AA.getUserRanking();
        ObservableList<User> userList = FXCollections.observableArrayList();
        for (int i = 0; i < ranking.size() && i < 10; i++) {
            User user = ranking.get(i);
            userList.add(user);
        }
        rankings.setItems(userList);
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
