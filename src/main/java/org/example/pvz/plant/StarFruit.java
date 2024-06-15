package org.example.pvz.plant;

import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.bullet.Frisbee;
import org.example.pvz.bullet.StarBullet;
import org.example.pvz.inter.Bullet;
import org.example.pvz.inter.Plant;

import java.util.ArrayList;
import java.util.List;

public class StarFruit extends Plant {
    private static final List<List<Image>> animations = new ArrayList<>();
    private static final double WIDTH = 77, HEIGHT = 70;

    private int attackCooldown = 0;
    private boolean doubleJumped = false;
    private short dashTime = 0;

    private double gravity = Const.GRAVITY;
    private double acceleration =  Const.ACCELERATE_SPEED;

    static {
        List<Image> frames = new ArrayList<>(13);
        for (int i = 0; i < 13; i++) {
            frames.add(new Image(StarFruit.class.
                    getResource("/org/example/images/StarFruit/StarFruit_" + i + ".png").toString()));
        }
        animations.add(frames);
    }

    public StarFruit() {
        super(animations, WIDTH, HEIGHT, Const.STAR_FRUIT_HP);
        this.setPrimaryCooldown(Const.STAR_FRUIT_PRIMARY_CD);
        this.setUltimateEnergy(Const.STAR_FRUIT_ULTIMATE_ENERGY);
        this.setCurrentEnergy(0);
    }

    @Override
    public void primary() {
        this.dashTime = Const.STAR_FRUIT_DASH_TIME;
        this.setY(this.getY()-1);
        this.gravity = 0;
        this.acceleration = 0;
        this.beInvincible(this.dashTime);
        if(this.isToRight()) {
            this.setXSpeed(Const.STAR_FRUIT_DASH_SPEED);
        } else {
            this.setXSpeed(-Const.STAR_FRUIT_DASH_SPEED);
        }
        this.attackCooldown = 0;
    }

    @Override
    public void ultimate() {
        Bullet bullet = new Frisbee(getX(), getY()+5, this);
        getGameScene().addBullet(bullet);
    }

    @Override
    public double getPlatAccelerate() {
        return acceleration;
    }

    @Override
    public double getGravity() {
        return gravity;
    }

    @Override
    public void updateStatus() {
        super.updateStatus();
        if(this.dashTime > 0) {
            this.dashTime--;
            if(this.dashTime == 0) {
                this.setXSpeed(0);
                this.acceleration = Const.ACCELERATE_SPEED;
                this.gravity = Const.GRAVITY;
            }
        }
        if(this.attackCooldown > 0) this.attackCooldown--;
    }

    @Override
    public void onGround() {
        super.onGround();
        this.doubleJumped = false;
    }

    @Override
    public void attack() {
        if(this.attackCooldown <= 0 && this.isFree()) {
            this.attackCooldown = Const.STAR_FRUIT_ATTACK_CD;
            for(short i = -1; i < 2; i++){
                Bullet bullet = new StarBullet(this.getX()+(this.isToRight()?getWidth():0),
                        this.getY()+20, this, i*Const.STAR_BULLET_Y_SPEED);
                getGameScene().addBullet(bullet);
            }
        }
    }

    @Override
    public void respawn(int x, int y) {
        super.respawn(x, y);
        this.attackCooldown = 0;
    }

    @Override
    public double getJumpSpeed() {
        return Const.STAR_FRUIT_JUMP_SPEED;
    }
}
