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
import org.example.pvz.box.CloudBox;
import org.example.pvz.box.Platform;
import org.example.pvz.inter.*;
import org.example.pvz.plant.PeaShooter;
import org.example.pvz.plant.Sunflower;

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
    private List<Status> statuses = new LinkedList<>();

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

        Plant plantA = new PeaShooter(300, 40);
        Plant plantB = new Sunflower(600, 40);

        setPlantA(plantA);
        setPlantB(plantB);

        Box platform = new Platform(300, 375, 400, 20);
        addBox(platform);

        Box base = new Platform(100, 570, 800, 100);
        addBox(base);

        Box test = new Platform(300, 540, 50, 30);
        addBox(test);

        resume();

        pen.setFill(Color.RED);
    }

    private void update(){
        if(plantA.isAlive()) plantA.update();
        else whenPlantDead(plantA);
        if(plantB.isAlive()) plantB.update();
        else whenPlantDead(plantB);

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

        Iterator<Status> iterator3 = statuses.iterator();
        while(iterator3.hasNext()){
            Status status = iterator3.next();
            if(status.isAlive()){
                status.update();
            } else {
                iterator3.remove();
            }
        }

        // paint
        pen.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        plantA.paint();
        plantB.paint();

        boxes.forEach(Box::paint);
        bullets.forEach(Bullet::paint);
        statuses.forEach(Status::paint);
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

    public List<Box> collideBox(Sprite plant){
        List<Box> result = new LinkedList<>();
        for(Box box : boxes){
            if(box.getBounds().intersects(plant.getBounds())){
                result.add(box);
            }
        }
        return result;
    }

    public void setPlantA(Plant plant){
        this.plantA = plant;
        plantA.setGameScene(this);
        plantA.setTeamTag(1);
        controllerA = LocalPlantController.getLocalPlayerOne();
        controllerA.setPlant(plantA);

        plantA.respawn(300, 40);
    }

    public void setPlantB(Plant plant){
        this.plantB = plant;
        plantB.setGameScene(this);
        plantB.setTeamTag(2);
        controllerB = LocalPlantController.getLocalPlayerTwo();
        controllerB.setPlant(plantB);

        plantB.respawn(600, 40);
    }

    public void addBullet(Bullet bullet){
        bullet.setGameScene(this);
        bullets.add(bullet);
    }

    public void addBox(Box box){
        box.setGameScene(this);
        boxes.add(box);
    }

    public void addStatus(Status status){
        status.setGameScene(this);
        statuses.add(status);
    }

    public Plant getOtherPlant(int teamTag){
        if(teamTag == 1) return plantB;
        else if(teamTag == 2) return plantA;
        return null;
    }

    private void whenPlantDead(Plant plant){
        if(plant.getTeamTag() == 1){
            plant.respawn(300, 40);
            Box cloud = new CloudBox(275, 120);
            addBox(cloud);
        } else {
            plant.respawn(600, 40);
            Box cloud = new CloudBox(575, 120);
            addBox(cloud);
        }
    }
}
