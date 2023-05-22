module AA_Game {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    exports view;
    opens view to javafx.fxml;

    exports view.Messages;
    opens view.Messages to javafx.fxml;

    exports view.UserMenu;
    opens view.UserMenu to javafx.fxml;

    exports Controller;
    opens Controller to javafx.fxml;

    exports Model;
    opens Model to javafx.fxml, com.google.gson;

    opens Controller.UserController to com.google.gson;

}