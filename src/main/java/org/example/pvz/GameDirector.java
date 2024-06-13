package org.example.pvz;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.pvz.inter.GameMap;
import org.example.pvz.inter.Plant;
import org.example.pvz.inter.PlantController;
import org.example.pvz.map.RoofTop;
import org.example.pvz.plant.PeaShooter;
import org.example.pvz.plant.Sunflower;

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

        Plant plantA = new PeaShooter(300, 40);
        PlantController controllerA = LocalPlantController.getLocalPlayerOne();
        Plant plantB = new Sunflower(600, 40);
        PlantController controllerB = LocalPlantController.getLocalPlayerTwo();
        GameMap gameMap = new RoofTop();

        instance.gameScene.loadGame(gameMap, plantA, controllerA, plantB, controllerB);

    }

    public Stage getStage() {
        return stage;
    }
}
