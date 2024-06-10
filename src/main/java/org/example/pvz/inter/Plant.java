package org.example.pvz.inter;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.pvz.Const;
import org.example.pvz.GameScene;
import org.example.pvz.stick.Pumpkin;

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
    private int currentShield;
    private boolean isDefended = false;
    private boolean shieldBroken = false;

    private Pumpkin pumpkin = new Pumpkin();

    public Plant(List<List<Image>> animations, double x, double y, double width, double height, int maxHp) {
        super(animations, x, y, width, height);
        this.maxHp = maxHp;
        this.currentHp = this.maxHp;
        this.currentShield = Const.MAX_SHIELD;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void jump(){
        if(!jumped && isFree){
            jumped = true;
            offGround();
            ySpeed = -Const.JUMP_SPEED;
        }
    }

    public void jumpRelease(){

    }

    public void defend(){
        if(this.currentShield > 0 && !this.shieldBroken){
            this.isDefended = true;
            this.isFree = false;
        }
    }

    public void defendRelease(){
        this.isDefended = false;
        this.isFree = true;
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

    public void updateStatus(){
        if(!this.isDefended && currentShield < Const.MAX_SHIELD){
            this.currentShield++;
        }
        if(this.currentShield == Const.MAX_SHIELD){
            this.shieldBroken = false;
        }
        if(this.getY() > GameScene.CANVAS_HEIGHT || this.currentHp <= 0) {
            this.kill();
        }
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

        resetBounds();

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

        resetBounds();

        updateStatus();
    }

    @Override
    public void paint() {
        super.paint();

        GraphicsContext pen = getGameScene().getGraphicsContext();
        if(getTeamTag() == 1) {
            pen.save();
            pen.setFill(Color.YELLOW);
            pen.fillRect(0, 0, 406, 27);
            pen.setFill(Color.RED);
            pen.fillRect( 3, 2, 400 * getCurrentHp()/ getMaxHp(), 25);
            pen.restore();

            pen.save();
            pen.setFill(Color.YELLOW);
            pen.fillRect(0, 27, 106, 16);
            if(shieldBroken) pen.setFill(Color.ORANGE);
            else pen.setFill(Color.BLUE);
            pen.fillRect(3, 29, 100 * currentShield/Const.MAX_SHIELD, 14);
            pen.restore();
        } else {
            pen.save();
            pen.setFill(Color.YELLOW);
            pen.fillRect(GameScene.CANVAS_WIDTH-406, 0, 406, 27);
            pen.setFill(Color.RED);
            pen.fillRect(GameScene.CANVAS_WIDTH-3-400 * getCurrentHp()/ getMaxHp(),
                    2, 400 * getCurrentHp()/ getMaxHp(), 25);

            pen.save();
            pen.setFill(Color.YELLOW);
            pen.fillRect(GameScene.CANVAS_WIDTH-106, 27, 106, 16);
            if(shieldBroken) pen.setFill(Color.ORANGE);
            else pen.setFill(Color.BLUE);
            pen.fillRect(GameScene.CANVAS_WIDTH-3-100 * currentShield/Const.MAX_SHIELD,
                    29, 100 * currentShield/Const.MAX_SHIELD, 14);
            pen.restore();
        }

        if(isDefended && this.currentShield > 0){
            this.pumpkin.defend(this, this.currentShield);
            this.pumpkin.paint();
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
        if(this.isDefended && this.currentShield > 0)
            this.currentShield -= damage;
        else
            this.currentHp -= damage;

        if(this.currentShield <= 0) {
            this.isDefended = false;
            this.shieldBroken = true;
        }
    }

    public void respawn(int x, int y){
        this.currentHp = this.maxHp;
        this.currentShield = Const.MAX_SHIELD;
        this.setX(x);
        this.setY(y);
        this.setAlive(true);
        this.jumped = true;
    }

    @Override
    public void setGameScene(GameScene gameScene) {
        super.setGameScene(gameScene);
        this.pumpkin.setGameScene(gameScene);
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
