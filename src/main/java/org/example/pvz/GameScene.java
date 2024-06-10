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
import org.example.pvz.inter.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
    private List<Box> boxes = new LinkedList<>();
    private List<Bullet> bullets = new LinkedList<>();

    private Plant plantA = new PeaShooter(300, 40);
    private Plant plantB = new PeaShooter(600, 40);

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

        plantB.setGameScene(this);
        plantB.setTeamTag(2);
        controllerB = LocalPlantController.getLocalPlayerTwo();
        controllerB.setPlant(plantB);

        Box platform = new Platform(300, 325, 400, 50);
        platform.setGameScene(this);
        boxes.add(platform);

        Box base = new Platform(100, 570, 800, 100);
        base.setGameScene(this);
        boxes.add(base);

        Box test = new Platform(300, 540, 50, 30);
        test.setGameScene(this);
        boxes.add(test);

        resume();

        pen.setFill(Color.RED);
    }

    private void update(){
        plantA.update();
        plantB.update();

        Iterator<Box> iterator = boxes.iterator();
        while(iterator.hasNext()){
            Box box = iterator.next();
            if(box.isAlive()){
                box.update();
            } else{
                iterator.remove();
            }
        }

        Iterator<Bullet> iterator2 = bullets.iterator();
        while(iterator2.hasNext()){
            Bullet bullet = iterator2.next();
//            updateBulletCollide(bullet);
            if(bullet.isAlive()){
                bullet.update();
            } else {
                iterator2.remove();
            }
        }

        // paint
        pen.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        plantA.paint();
        plantB.paint();
        boxes.forEach(Box::paint);
        bullets.forEach(Bullet::paint);
    }

    private void keyProcess(KeyEvent event){
        controllerA.reactKeyEvent(event);
        controllerB.reactKeyEvent(event);
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

    public List<Box> collideBox(Plant plant){
        List<Box> result = new LinkedList<>();
        for(Box box : boxes){
            if(box.getBounds().intersects(plant.getBounds())){
                result.add(box);
            }
        }
        return result;
    }

    private void updateBulletCollide(Bullet bullet){
        if(!bullet.isAlive()) return;
        if(bullet.getBounds().intersects(plantA.getBounds())) bullet.collide(plantA);
        if(bullet.getBounds().intersects(plantB.getBounds())) bullet.collide(plantB);

        for(Box box : boxes){
            if (box.getBounds().intersects(bullet.getBounds()))
                box.collide(bullet);
        }
    }

    public void addBullet(Bullet bullet){
        bullet.setGameScene(this);
        bullets.add(bullet);
    }

    public Plant getOtherPlant(int teamTag){
        if(teamTag == 1) return plantB;
        else if(teamTag == 2) return plantA;
    }
}
