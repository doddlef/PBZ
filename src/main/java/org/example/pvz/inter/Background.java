package org.example.pvz.inter;

import javafx.scene.image.Image;
import org.example.pvz.game.GameScene;

public class Background implements GameObject{
    private static Image image = new Image(Background.class.
            getResource("/org/example/images/background/Background_4.jpg").toString());
    private GameScene gameScene;

    public Background(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    @Override
    public void update() {}

    @Override
    public void paint() {
        gameScene.getGraphicsContext().drawImage(image, -200, 0);
    }

    @Override
    public void kill() {

    }
}
