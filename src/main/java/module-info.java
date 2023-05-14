module AA_Game {
    requires javafx.controls;
    requires javafx.fxml;


    exports view;
    opens view to javafx.fxml;
    exports view.UserMenu;
    opens view.UserMenu to javafx.fxml;
}