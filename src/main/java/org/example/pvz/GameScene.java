package org.example.pvz;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.example.pvz.inter.Plant;
import org.example.pvz.inter.PlantController;

public class GameScene {
    public static final double CANVAS_WIDTH = 1000, CANVAS_HEIGHT = 600;

    private Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    private GraphicsContext pen = canvas.getGraphicsContext2D();
    private StackPane root = new StackPane();

    private Timeline updater = new Timeline(new KeyFrame(Duration.millis(Const.UPDATE_FRAME),
            event -> update()));
    private boolean running = false;
    private GameDirector gameDirector;
    private PlantController controllerA;
    private PlantController controllerB;

    private Plant plantA = new PeaShooter(20, 20);

    public GameScene(GameDirector gameDirector) {
        this.gameDirector = gameDirector;
        this.root.getChildren().add(canvas);
    }

    public void loadGame(){
        this.running = true;

        gameDirector.getStage().getScene().setOnKeyPressed(this::keyProcess);
        gameDirector.getStage().getScene().setOnKeyReleased(this::keyProcess);

        updater.setCycleCount(Animation.INDEFINITE);

        plantA.setGameScene(this);
        plantA.setTeamTag(1);
        controllerA = LocalPlantController.getLocalPlayerOne();
        controllerA.setPlant(plantA);

        resume();

        plantA.update();
        pen.setFill(Color.RED);
        plantA.paint();
    }

    private void update(){
        plantA.update();

        // paint
        pen.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        plantA.paint();
    }

    private void keyProcess(KeyEvent event){
        controllerA.reactKeyEvent(event);
//        controllerB.reactKeyEvent(event);
    }

    public void resume() {
        this.running = true;
        updater.play();
    }
    public void pause() {
        this.running = false;
        updater.pause();
    }
    public GraphicsContext getGraphicsContext(){
        return pen;
    }

    public boolean isRunning() {
        return running;
    }

    public StackPane getRoot() {
        return root;
    }
}
