module org.myproject.m2m1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.myproject.m2m1 to javafx.fxml;
    exports org.myproject.m2m1;
}