package org.example.pvz.plant;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.pvz.Const;
import org.example.pvz.GameScene;
import org.example.pvz.bullet.FirePea;
import org.example.pvz.bullet.PeaBullet;
import org.example.pvz.inter.Bullet;
import org.example.pvz.inter.Plant;

import java.util.ArrayList;
import java.util.List;

public class PeaShooter extends Plant {
    private static final List<List<Image>> animations = new ArrayList<>();
    private static final double WIDTH = 70, HEIGHT = 70;

    private int attackCooldown = 0;
    private int ammoLeft = Const.PEA_SHOOTER_AMMO;
    private int ammoIndex = Const.PEA_SHOOTER_AMMO_SPEED;
    private int combo = 0;
    private int comboTime = 0;

    private double circleX, circleStep;

    static {
        List<Image> frames = new ArrayList<>(13);
        for(int i = 0; i < 13; i++){
            frames.add(new Image(PeaShooter.class.
                    getResource("/org/example/images/peashooter/Peashooter_"+i+".png").toString()));
        }
        animations.add(frames);
    }

    public PeaShooter(double x, double y) {
        super(animations, x, y, WIDTH, HEIGHT, Const.PEA_SHOOTER_HP);
    }

    @Override
    public void updateStatus() {
        super.updateStatus();
        if(this.attackCooldown > 0) this.attackCooldown--;
        if(this.ammoLeft < Const.PEA_SHOOTER_AMMO) {
            this.ammoIndex--;
            if(this.ammoIndex == 0) {
                this.ammoLeft++;
                this.ammoIndex = Const.PEA_SHOOTER_AMMO_SPEED;
            }
        }
        if(this.comboTime == 0) this.combo = 0;
        else this.comboTime--;
    }

    @Override
    public void paintStatus() {
        super.paintStatus();
        for(int i = 0; i < ammoLeft; i++){
            getGameScene().getGraphicsContext().save();
            getGameScene().getGraphicsContext().setFill(Color.GREEN);
            getGameScene().getGraphicsContext().fillRect(circleX+i*circleStep, 50, 20, 20);
            getGameScene().getGraphicsContext().restore();
        }
    }

    @Override
    public void attack() {
        if(this.attackCooldown <= 0 && this.isFree() && this.ammoLeft > 0){
            this.attackCooldown = Const.PEA_SHOOTER_ATTACK_COOLDOWN;
            Bullet bullet;
            if(this.combo == 2){
                bullet = new FirePea(this.getX()+this.getWidth(), this.getY()+5,this);
                combo = 0;
                comboTime = 0;
            } else {
                bullet = new PeaBullet(this.getX()+this.getWidth(), this.getY()+5,this);
                combo++;
                comboTime = Const.PEA_COMBO_TIME;
            }
            this.getGameScene().addBullet(bullet);
            this.ammoLeft--;
        }
    }

    @Override
    public void attackRelease() {

    }

    @Override
    public void respawn(int x, int y) {
        super.respawn(x, y);
        this.ammoLeft = Const.PEA_SHOOTER_AMMO;
    }

    @Override
    public void setTeamTag(int teamTag) {
        super.setTeamTag(teamTag);
        if(this.getTeamTag() == 1){
            circleX = 20;
            circleStep = 30;
        } else {
            circleX = GameScene.CANVAS_WIDTH - 30;
            circleStep = -30;
        }
    }
}
