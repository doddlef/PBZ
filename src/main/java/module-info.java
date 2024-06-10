module org.example.pvz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.compiler;


    opens org.example.pvz to javafx.fxml;
    exports org.example.pvz;
    exports org.example.pvz.inter;
    opens org.example.pvz.inter to javafx.fxml;
}