package view.UserMenu;

import Controller.UserController.ProfileController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SelectAvatarMenu extends Application {
    public static ProfileController controller;

    @FXML
    public ImageView AVATAR_1, AVATAR_2, AVATAR_3, AVATAR_4, AVATAR_5, AVATAR_6, AVATAR_7, AVATAR_8;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                new URL(LoginMenu.class.getResource("/fxml/UserFXML/selectAvatar.fxml").toExternalForm()));
        loader.setController(this);
        BorderPane pane = loader.load();

        ArrayList<ImageView> avatars = new ArrayList<>(List.of(AVATAR_1, AVATAR_2, AVATAR_3, AVATAR_4, AVATAR_5,
                AVATAR_6, AVATAR_7, AVATAR_8));

        for (ImageView avatar : avatars) {
            avatar.setOnMouseClicked(e -> {
                controller.setAvatar(avatar.getImage().getUrl());
                try {
                    controller.run();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        }

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
