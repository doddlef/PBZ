package org.example.pvz.bullet;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.inter.Box;
import org.example.pvz.inter.Bullet;
import org.example.pvz.inter.Plant;
import org.example.pvz.plant.PeaShooter;

import java.util.ArrayList;
import java.util.List;

public class SunBullet extends Bullet {
    private static final List<List<Image>> animations = new ArrayList<>();

    static {
        List<Image> frames = new ArrayList<>(22);
        for(int i = 0; i < 22; i++){
            frames.add(new Image(PeaShooter.class.
                    getResource("/org/example/images/Sun/Sun_"+i+".png").toString()));
        }
        animations.add(frames);
    }

    private double xSpeed, ySpeed;
    private int countDown = Const.SUN_COUNT_DOWN;
    private boolean fixed = false;

    public SunBullet(double x, double y, Plant parent, double xSpeed) {
        super(animations, x, y, 70, 70, parent);
        if(this.getParent().isToRight())
            this.xSpeed = xSpeed;
        else
            this.xSpeed = -xSpeed;
        this.ySpeed = -xSpeed;
    }

    @Override
    public void update() {
        if(!fixed){
            this.setX(this.getX() + xSpeed);
            this.ySpeed += Const.GRAVITY;
            this.setY(this.getY() + ySpeed);
        }
        this.countDown--;
        if(this.countDown == 0){
            explode();
        }
        super.update();
    }

    @Override
    public void collideBox(List<Box> collided) {
        if(!collided.isEmpty()){
            this.xSpeed = 0;
            this.ySpeed = 0;
            this.fixed = true;
        }
    }

    @Override
    public void reactOther(Plant other) {
        if(other != null && other.getBounds().intersects(this.getBounds())){
            explode();
        }
    }

    private void explode(){
        kill();
    }

    @Override
    public void resetBounds() {
        this.setBounds(new Rectangle2D(this.getX()+10, this.getY()+10, 50, 50));
    }
}
