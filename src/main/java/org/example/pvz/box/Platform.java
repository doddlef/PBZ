package org.example.pvz.box;

import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.inter.Box;

public class Platform extends Box {
    private Image image;

    public Platform(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.image = new Image(this.getClass().getResource(Const.PLATFORM_PATH).toExternalForm(),
                width, height, false, true);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void paint() {
        getGameScene().getGraphicsContext().drawImage(image, getX(), getY(), getWidth(), getHeight());
    }
}
