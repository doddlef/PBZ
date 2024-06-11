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
        if(oldY+1 > this.getBounds().getMinY()+this.getBounds().getHeight()){
            plant.setY(this.getBounds().getMinY()+this.getBounds().getHeight());
            plant.setYSpeed(0);
        }
        if(oldY+ plant.getHeight()-1 < this.getBounds().getMinY()){
            plant.setY(this.getBounds().getMinY() - plant.getHeight()+1);
            plant.onGround();
        }
        if(oldX + plant.getWidth()-1 < this.getBounds().getMinX()){
            plant.setX(this.getBounds().getMinX() - plant.getWidth());
        }
        if(oldX+1 > this.getBounds().getMinX()+this.getBounds().getWidth()){
            plant.setX(this.getBounds().getMinX()+this.getBounds().getWidth());
        }
    }

    public boolean collide(Bullet bullet){
        return true;
    }

    public void takeDamage(int damage){}
}
