package org.example.pvz.inter;

import javafx.scene.image.Image;
import org.example.pvz.GameScene;

public class GameMap implements GameObject{
    private GameScene gameScene;
    private Image image;
    private double x, y;

    public GameMap(GameScene gameScene, double x, double y, Image image) {
        this.gameScene = gameScene;
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public void update(){}

    @Override
    public void paint() {
        gameScene.getGraphicsContext().drawImage(image, x, y);
    }

    @Override
    public void kill() {

    }
}
