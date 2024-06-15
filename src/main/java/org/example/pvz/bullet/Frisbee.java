package org.example.pvz.bullet;

import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.game.GameScene;
import org.example.pvz.inter.Box;
import org.example.pvz.inter.Bullet;
import org.example.pvz.inter.Plant;

import java.util.ArrayList;
import java.util.List;

public class Frisbee extends Bullet {
    private static final List<List<Image>> animations = new ArrayList<>();

    static {
        List<Image> frames = new ArrayList<>(4);
        for(int i = 0; i < 4; i++){
            frames.add(new Image(Frisbee.class.
                    getResource("/org/example/images/frisbee/frisbee_"+i+".png").toString()));
        }
        animations.add(frames);
    }

    private boolean flipped = false;
    private boolean damaged = false;
    private double xSpeed;

    public Frisbee(double x, double y, Plant parent) {
        super(animations, x, y, 60, 60, parent);
        this.xSpeed = Const.FRISBEE_SPEED * (this.isToRight()?1:-1);
    }

    @Override
    public void update() {
        this.setX(this.getX()+this.xSpeed);

        super.update();

        if(!this.flipped && (this.getX() <= 0 || this.getX()+getWidth() >= GameScene.CANVAS_WIDTH)) {
            flip();
        }

        if(getParent() != null && this.flipped &&
                getParent().getBounds().intersects(this.getBounds())) {
            getParent().setCurrentEnergy(getParent().getCurrentEnergy()+Const.FRISBEE_ENERGY_RECOVER);
            this.kill();
        }
    }

    @Override
    public void reactOther(Plant other) {
        if(other != null && !this.damaged && other.getBounds().intersects(this.getBounds())) {
            other.takeDamage(Const.FRISBEE_DAMAGE);
            other.beKnockUp(Const.FRISBEE_KNOCK_BACK*(this.isToRight()?1:-1), 0);
            this.damaged = true;
        }
    }

    @Override
    public void collideFirstBox(Box box) {
        if(this.flipped) {
            super.collideFirstBox(box);
        } else {
           flip();
        }
    }

    private void flip(){
        this.flipped = true;
        this.xSpeed = -this.xSpeed;
        this.damaged = false;
        this.setToRight(!this.isToRight());
    }

    @Override
    public int getDamage() {
        return Const.FRISBEE_DAMAGE;
    }
}
