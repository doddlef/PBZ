package org.example.pvz.game;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.pvz.LocalPlantController;
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
    private MyScene currentScene;

    private GameDirector() {}

    public static void initDirector(Stage stage){
        instance.stage = stage;
    }

    public static void gameStart(){
        instance.gameScene = new GameScene(instance);
        Scene scene = new Scene(instance.gameScene.getRoot());
        instance.stage.setScene(scene);
        instance.stage.show();

        Plant plantA = new Sunflower();
        PlantController controllerA = LocalPlantController.getLocalPlayerOne();
        Plant plantB = new PeaShooter();
        PlantController controllerB = LocalPlantController.getLocalPlayerTwo();
        GameMap gameMap = new RoofTop();

        instance.gameScene.loadGame(gameMap, plantA, controllerA, plantB, controllerB);
    }

    public void selectScene(){
        if(currentScene != null){ currentScene.quit();}
        SelectScene selectScene = new SelectScene(this);
        this.currentScene = selectScene;
        selectScene.loadScene(stage);
    }

    public void gameStart(GameMap gameMap, Plant plantA, PlantController controllerA,
                          Plant plantB, PlantController controllerB){
        if(currentScene != null){ currentScene.quit();}
        this.gameScene = new GameScene(this);
        this.gameScene.loadGame(gameMap, plantA, controllerA, plantB, controllerB);
    }

    public void endScene(short pointA, short pointB){
        if(currentScene != null){ currentScene.quit();}
    }

    public Stage getStage() {
        return stage;
    }
}
