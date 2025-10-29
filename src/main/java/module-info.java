module com.example.join_cafeteriafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.join_cafeteriafx to javafx.fxml;
    exports com.example.join_cafeteriafx;
}