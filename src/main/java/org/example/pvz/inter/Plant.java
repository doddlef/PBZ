package org.example.pvz.inter;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.pvz.Const;
import org.example.pvz.GameScene;
import org.example.pvz.PeaShooter;

import javax.annotation.processing.ProcessingEnvironment;
import java.util.List;

public abstract class Plant extends Sprite{
    private boolean left, right;
    private int teamTag = 0;
    private boolean onGround = false;
    private boolean jumped = false;
    private boolean isFree = true;
    private double ySpeed = 0;
    private double xSpeed = 0;

    private int maxHp;
    private int currentHp;

    public Plant(List<List<Image>> animations, double x, double y, double width, double height, int maxHp) {
        super(animations, x, y, width, height);
        this.maxHp = maxHp;
        this.currentHp = this.maxHp;
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
            offGround();
            ySpeed = -Const.JUMP_SPEED;
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

    public void onGround(){
        onGround = true;
        jumped = false;
        ySpeed = 0;
    }

    public void offGround(){
        onGround = false;
    }

    @Override
    public void update() {
        // record original position
        double oldX = getX();
        double oldY = getY();

        // apply gravity
        if (!onGround) {
            ySpeed += Const.GRAVITY;
            if(ySpeed > Const.MAX_DROP) ySpeed = Const.MAX_DROP;
        }

        // horizontal move
        if(isFree){
            if (left && !right) {
                this.setToRight(false);
                this.xSpeed -= Const.ACCELERATE_SPEED;
            } else if (right && !left) {
                this.setToRight(true);
                this.xSpeed += Const.ACCELERATE_SPEED;
            } else if (this.xSpeed > 0){
                this.xSpeed -= Const.ACCELERATE_SPEED;
                if(this.xSpeed < 0) this.xSpeed = 0;
            } else {
                this.xSpeed += Const.ACCELERATE_SPEED;
                if(this.xSpeed > 0) this.xSpeed = 0;
            }
            if(this.xSpeed > Const.PLANT_SPEED) this.xSpeed = Const.PLANT_SPEED;
            if(this.xSpeed < -Const.PLANT_SPEED) this.xSpeed = -Const.PLANT_SPEED;
            setX(getX() + xSpeed);
        }

        // vertical move
        setY(getY() + ySpeed);
        super.update();

        // check horizontal status
        if(this.getX() < 0) this.setX(0);
        if(this.getX() + this.getWidth() > GameScene.CANVAS_WIDTH)
            this.setX(GameScene.CANVAS_WIDTH -this.getWidth());

        List<Box> collided = getGameScene().collideBox(this);
        if(collided.isEmpty()) offGround();
        else {
            for(Box box : collided){
                box.collide(this, oldX, oldY);
            }
        }
    }

    @Override
    public void paint() {
        super.paint();

        GraphicsContext pen = getGameScene().getGraphicsContext();
        if(getTeamTag() == 1) {
            pen.save();
            pen.setFill(Color.YELLOW);
            pen.fillRect(60, 0, 206, 27);
            pen.setFill(Color.RED);
            pen.fillRect( 63, 2, 200 * getCurrentHp()/ getMaxHp(), 25);
            pen.restore();
        } else {
            pen.save();
            pen.setFill(Color.YELLOW);
            pen.fillRect(GameScene.CANVAS_WIDTH-206, 0, 206, 27);
            pen.setFill(Color.RED);
            pen.fillRect(GameScene.CANVAS_WIDTH-2-200 * getCurrentHp()/ getMaxHp(),
                    2, 200 * getCurrentHp()/ getMaxHp(), 25);
        }
    }

    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public double getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public void takeDamage(int damage) {
        this.currentHp -= damage;
    }
}
