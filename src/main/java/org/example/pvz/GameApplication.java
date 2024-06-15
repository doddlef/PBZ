package org.example.pvz;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.pvz.game.GameDirector;

public class GameApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GameDirector.initDirector(primaryStage);
        GameDirector.getInstance().pbz();
    }
}
