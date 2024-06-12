package org.example.pvz.bullet;

import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.inter.Box;
import org.example.pvz.inter.Bullet;
import org.example.pvz.inter.Plant;
import org.example.pvz.inter.Status;
import org.example.pvz.stick.JalapenoExplode;
import org.example.pvz.stick.ShroomBoom;

import java.util.ArrayList;
import java.util.List;

public class Jalapeno extends Bullet {
    private static final List<List<Image>> animations = new ArrayList<>();

    static {
        List<Image> frames = new ArrayList<>(8);
        for(int i = 0; i < 8; i++){
            frames.add(new Image(Jalapeno.class.
                    getResource("/org/example/images/Jalapeno/Jalapeno_"+i+".png").toString()));
        }
        animations.add(frames);
    }

    private double xSpeed, ySpeed;
    private int countDown = Const.JALAPENO_LIFE;
    private boolean fixed = false;

    public Jalapeno(double x, double y, Plant parent) {
        super(animations, x, y, 68, 89, parent);
        if(this.getParent().isToRight())
            this.xSpeed = Const.JALAPENO_SPEED;
        else
            this.xSpeed = -Const.JALAPENO_SPEED;
        this.ySpeed = -Const.JALAPENO_SPEED;
    }

    @Override
    public void update() {
        if(!fixed) {
            this.setX(this.getX() + this.xSpeed);
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
    public void collideFirstBox(Box box) {
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.fixed = true;
    }

    private void explode(){
        Status explode = new JalapenoExplode(this.getX()-343, this.getY()-44);
        Status boom = new ShroomBoom(this.getX()-107, this.getY()-235);

        Plant other = getGameScene().getOtherPlant(getTeamTag());

        if(other.getY() < getY()+getHeight()){
            double distance = Math.abs(this.getX()+this.getWidth()/2-other.getX()-other.getWidth()/2);

            int damage = (int) (Const.JALAPENO_DAMAGE * (400-distance)/400 + 10);
            if(damage < 10) damage = 10;

            int dizzy = (int) (Const.JALAPENO_DIZZY * (400-distance)/400 + 10);
            if(dizzy < 10) dizzy = 10;

            other.takeDamage(damage);
            other.beDizzy(dizzy);
        } else {
            other.takeDamage(10);
            other.beDizzy(10);
        }

        getGameScene().addStatus(explode);
        getGameScene().addStatus(boom);
        kill();
    }
}
