module com.hoopwatcher {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens com.hoopwatcher to javafx.fxml;
    exports com.hoopwatcher;
    exports com.hoopwatcher.playercreator;
}
