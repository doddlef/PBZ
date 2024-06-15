package org.example.pvz.game;

import javafx.stage.Stage;
import org.example.pvz.inter.GameMap;
import org.example.pvz.inter.Plant;
import org.example.pvz.inter.PlantController;

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

    public void pbz(){
        if(this.stage == null) {
            throw new RuntimeException("stage is null");
        }
        instance.selectScene();
    }

    public void selectScene(){
        if(currentScene != null){ currentScene.quit();}
        SelectScene selectScene = new SelectScene(this);
        this.currentScene = selectScene;
        selectScene.loadScene(stage);
    }

    public void gameStart(short aim, GameMap gameMap, Plant plantA, PlantController controllerA,
                          Plant plantB, PlantController controllerB){
        if(currentScene != null){ currentScene.quit();}
        this.gameScene = new GameScene(this);
        this.gameScene.loadGame(aim, gameMap, plantA, controllerA, plantB, controllerB);
    }

    public void endScene(short pointA, short pointB){
        if(currentScene != null){ currentScene.quit();}
        EndScene endScene = new EndScene(this);
        this.currentScene = endScene;
        endScene.loadScene(pointA, pointB);
    }

    public Stage getStage() {
        return stage;
    }
}
