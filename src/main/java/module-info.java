module org.example.pvz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.pvz to javafx.fxml;
    exports org.example.pvz;
    exports org.example.pvz.inter;
    opens org.example.pvz.inter to javafx.fxml;
}