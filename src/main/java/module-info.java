module AA_Game {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    exports view;
    opens view to javafx.fxml;
    exports view.UserMenu;
    opens view.UserMenu to javafx.fxml;
    opens Controller.UserController to com.google.gson;
}