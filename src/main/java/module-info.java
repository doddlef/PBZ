module org.example.pvz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.compiler;
    requires javafx.media;


    opens org.example.pvz to javafx.fxml;
    exports org.example.pvz;
    exports org.example.pvz.inter;
    opens org.example.pvz.inter to javafx.fxml;
    exports org.example.pvz.box;
    opens org.example.pvz.box to javafx.fxml;
    exports org.example.pvz.bullet;
    opens org.example.pvz.bullet to javafx.fxml;
    exports org.example.pvz.plant;
    opens org.example.pvz.plant to javafx.fxml;
}