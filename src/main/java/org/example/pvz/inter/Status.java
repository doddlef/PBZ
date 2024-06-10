package org.example.pvz.inter;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import org.example.pvz.Const;

import java.util.List;

public abstract class Status extends Sprite{
    private int life;

    public Status(List<List<Image>> animations, double x, double y,
                  double width, double height, int life) {
        super(animations, x, y, width, height);
        this.life = life;
        this.setBounds(Const.EMPTY_RECT);
    }

    @Override
    public void update() {
        if(life == 0) this.kill();
        life--;
    }

    @Override
    public Rectangle2D getBounds() {
        return Const.EMPTY_RECT;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
