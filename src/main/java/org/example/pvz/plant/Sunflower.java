package org.example.pvz.plant;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.pvz.Const;
import org.example.pvz.GameScene;
import org.example.pvz.bullet.Jalapeno;
import org.example.pvz.bullet.SunBullet;
import org.example.pvz.inter.Bullet;
import org.example.pvz.inter.Plant;

import java.util.ArrayList;
import java.util.List;

public class Sunflower extends Plant {
    private static final List<List<Image>> animations = new ArrayList<>();

    static {
        List<Image> frames = new ArrayList<>(18);
        for(int i = 0; i < 18; i++){
            frames.add(new Image(PeaShooter.class.
                    getResource("/org/example/images/sunflower/SunFlower_"+i+".png").toString()));
        }
        animations.add(frames);
    }

    private int attackCooldown = 0;
    private int ammoLeft = Const.SUNFLOWER_AMMO;
    private int ammoIndex = Const.SUNFLOWER_AMMO_SPEED;

    private int readyAttack = 10;
    private boolean inReady = false;
    private int recoverIndex = Const.SUNFLOWER_RECOVER_SPEED;

    private double circleX, circleStep;

    public Sunflower(double x, double y) {
        super(animations, x, y, 70, 70, Const.SUNFLOWER_HP);
        this.setPrimaryCooldown(Const.SUNFLOWER_PRIMARY_CD);
        this.setUltimateEnergy(Const.SUNFLOWER_ULTIMATE_ENERGY);
    }

    @Override
    public void attack() {
        if(this.attackCooldown <= 0 && !this.inReady && this.ammoLeft > 0 && this.isFree()){
            this.readyAttack = 10;
            this.inReady = true;
        }
    }

    @Override
    public void attackRelease() {
        if(this.readyAttack > 0 && this.inReady){
            this.attackCooldown = Const.SUNFLOWER_ATTACK_COOLDOWN;
            if (this.readyAttack > Const.SUNFLOWER_MAX_READY)
                this.readyAttack = Const.SUNFLOWER_MAX_READY;
            Bullet bullet = new SunBullet(this.getX(), this.getY()-30, this,
                    this.readyAttack);
            getGameScene().addBullet(bullet);
            // fire
            this.ammoLeft--;
            this.inReady = false;
            this.readyAttack = 10;
        }
    }

    @Override
    public void updateStatus() {
        super.updateStatus();
        if(this.attackCooldown > 0) this.attackCooldown--;
        if(this.ammoLeft < Const.SUNFLOWER_AMMO) {
            this.ammoIndex--;
            if(this.ammoIndex == 0) {
                this.ammoLeft++;
                this.ammoIndex = Const.SUNFLOWER_AMMO_SPEED;
            }
        }
        if(this.inReady) {
            readyAttack++;
        }
        if(this.getCurrentHp() < this.getMaxHp()){
            this.recoverIndex--;
            if(this.recoverIndex == 0){
                this.setCurrentHp(this.getCurrentHp()+Const.SUNFLOWER_HP_RECOVER);
                this.recoverIndex = Const.SUNFLOWER_RECOVER_SPEED;
            }
        }
    }

    @Override
    public void paintStatus() {
        super.paintStatus();
        for(int i = 0; i < ammoLeft; i++){
            getGameScene().getGraphicsContext().save();
            getGameScene().getGraphicsContext().setFill(Color.ORANGE);
            getGameScene().getGraphicsContext().fillRect(circleX+i*circleStep, 55, 30, 30);
            getGameScene().getGraphicsContext().restore();
        }
    }

    @Override
    public void primary() {
        Plant other = getGameScene().getOtherPlant(getTeamTag());
        SunBullet bullet = new SunBullet(other.getX(), -35, this, 0, Const.GRAVITY/3);
        getGameScene().addBullet(bullet);
    }

    @Override
    public void ultimate() {
        Bullet bullet = new Jalapeno(this.getX(), this.getY()-40, this);
        getGameScene().addBullet(bullet);
    }

    @Override
    public void defend() {
        super.defend();
        this.attackRelease();
    }

    @Override
    public void respawn(int x, int y) {
        super.respawn(x, y);
        this.ammoLeft = Const.SUNFLOWER_AMMO;
        this.inReady = false;
        this.readyAttack = 0;
    }

    @Override
    public void setTeamTag(int teamTag) {
        super.setTeamTag(teamTag);
        if(this.getTeamTag() == 1){
            circleX = 20;
            circleStep = 40;
        } else {
            circleX = GameScene.CANVAS_WIDTH - 50;
            circleStep = -40;
        }
    }

    @Override
    public void beDizzy(int time) {
        super.beDizzy(time);
        if(this.readyAttack > 0) {
            attackRelease();
        }
    }

    @Override
    public double getPlantMaxSpeed() {
        if(inReady) return Const.PLANT_SPEED/2;
        return Const.PLANT_SPEED;
    }

    @Override
    public double getPlatAccelerate() {
        if(inReady) return Const.ACCELERATE_SPEED/2;
        return Const.ACCELERATE_SPEED;
    }

    @Override
    public double getJumpSpeed() {
        if(inReady) return Const.JUMP_SPEED/1.5;
        return Const.JUMP_SPEED;
    }
}
