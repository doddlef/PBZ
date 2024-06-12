package org.example.pvz.inter;

import javafx.scene.image.Image;
import org.example.pvz.GameScene;

public class GameBackground implements GameObject{
    public static GameBackground roofTop(){
        return new GameBackground(-200, 0, new Image(Background.class.
                getResource("/org/example/images/background/Background_4.jpg").toString()));
    }

    private GameScene gameScene;
    private Image image;
    private double x, y;

    public GameBackground(double x, double y, Image image) {
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
    public void kill() {}

    public GameScene getGameScene() {
        return gameScene;
    }

    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }
}
