package org.example.pvz;

import javafx.application.Application;
import javafx.stage.Stage;

public class GameApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GameDirector.initDirector(primaryStage);
        GameDirector.gameStart();
    }
}
