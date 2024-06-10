package org.example.pvz.inter;

import javafx.scene.image.Image;

import java.util.List;

public class Box extends Sprite{
    public Box(List<List<Image>> animations, double x, double y, double width, double height) {
        super(animations, x, y, width, height);
    }

    public Box(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void update() {
        super.update();
    }

    public void collide(Plant plant, double oldX, double oldY) {
        if(oldY+1 > this.getY()+this.getHeight()){
            plant.setY(this.getY()+this.getHeight());
            plant.setYSpeed(0);
        }
        if(oldY+ plant.getHeight() < this.getY()){
            plant.setY(this.getY() - plant.getHeight()+1);
            plant.onGround();
        }
        if(oldX + plant.getWidth()-1 < this.getX()){
            plant.setX(this.getX() - plant.getWidth());
        }
        if(oldX+1 > this.getX()+this.getWidth()){
            plant.setX(this.getX()+this.getWidth());
        }
    }

    public void collide(Bullet bullet){
        bullet.kill();
    }

}
