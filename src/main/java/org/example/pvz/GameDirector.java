package org.example.pvz;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameDirector {
    private static GameDirector instance = new GameDirector();
    public static GameDirector getInstance() {return instance;}

    private Stage stage;
    private GameScene gameScene;

    private GameDirector() {}

    public static void initDirector(Stage stage){
        instance.stage = stage;
    }

    public static void gameStart(){
        instance.gameScene = new GameScene(instance);
        Scene scene = new Scene(instance.gameScene.getRoot());
        instance.stage.setScene(scene);
        instance.stage.show();
        instance.gameScene.loadGame();

    }

    public Stage getStage() {
        return stage;
    }
}
