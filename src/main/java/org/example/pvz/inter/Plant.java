package org.example.pvz.inter;

import javafx.scene.image.Image;
import org.example.pvz.Const;

import java.util.List;

public abstract class Plant extends Sprite{
    private boolean left, right;
    private int teamTag = 0;
    private boolean onGround = false;
    private boolean jumped = false;
    private double ySpeed = 0;

    public Plant(List<List<Image>> animations, double x, double y, double width, double height) {
        super(animations, x, y, width, height);
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void jump(){
        if(!jumped){
            jumped = true;
            ySpeed -= Const.JUMP_SPEED;
        }
    }

    public void jumpRelease(){

    }

    public void defend(){

    }

    public void defendRelease(){

    }

    public int getTeamTag() {
        return teamTag;
    }

    public void setTeamTag(int teamTag) {
        this.teamTag = teamTag;
    }

    public abstract void attack();

    public abstract void attackRelease();

    @Override
    public void update() {
        // apply gravity
        if (!onGround) {
            ySpeed += Const.GRAVITY;
        }

        // vertical move
        setY(getY() + ySpeed);

        // horizontal move
        if (left && !right) {
            this.setToRight(false);
            this.setX(this.getX() - Const.PLANT_SPEED);
        } else if (right && !left) {
            this.setToRight(true);
            this.setX(this.getX() + Const.PLANT_SPEED);
        }

        // check vertical status
        if (getY() + getHeight() > 500) {
            onGround = true;
            jumped = false;
            ySpeed = 0;
        } else {
            onGround = false;
        }
    }
}
