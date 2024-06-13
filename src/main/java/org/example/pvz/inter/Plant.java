package org.example.pvz.inter;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.example.pvz.Const;
import org.example.pvz.GameScene;
import org.example.pvz.stick.Dizzy;
import org.example.pvz.stick.Pumpkin;

import java.util.List;

public abstract class Plant extends Sprite{
    private boolean left, right;
    private int teamTag = 0;
    private boolean onGround = false;
    private boolean jumped = false;
    private double ySpeed = 0;
    private double xSpeed = 0;

    private int maxHp;
    private int currentHp;
    private int currentShield;
    private int shieldIndex = Const.SHIELD_RECOVER;
    private boolean isDefended = false;
    private boolean shieldBroken = false;
    private int dizzy = 0;
    private int invincible = 0;

    private int primaryCooldown = 0;
    private int primaryCountDown = 0;
    private int ultimateEnergy = 0;
    private int currentEnergy = 0;

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
        if(!jumped && this.isFree()){
            jumped = true;
            offGround();
            ySpeed = -getJumpSpeed();
        }
    }

    public void jumpRelease(){

    }

    public void defend(){
        if(this.currentShield > 0 && !this.shieldBroken && this.dizzy==0){
            this.isDefended = true;
        }
    }

    public void defendRelease(){
        this.isDefended = false;
    }

    public void primaryPress(){
        if(primaryCountDown == 0 && this.isFree()){
            primaryCountDown = primaryCooldown;
            primary();
        }
    }

    public void primaryRelease(){}

    public void ultimatePress(){
        if(currentEnergy >= ultimateEnergy && this.isFree()){
            currentEnergy = 0;
            ultimate();
        }
    }

    public void ultimate(){}

    public void primary(){}

    public int getTeamTag() {
        return teamTag;
    }

    public void setTeamTag(int teamTag) {
        this.teamTag = teamTag;
    }

    public void attack(){}

    public void attackRelease(){}

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
            this.shieldIndex--;
            if(this.shieldIndex <= 0){
                this.currentShield++;
                this.shieldIndex = Const.SHIELD_RECOVER;
            }
        }
        if(this.invincible > 0) this.invincible--;
        if(this.currentShield == Const.MAX_SHIELD){
            this.shieldBroken = false;
        }
        if(this.getY() > GameScene.CANVAS_HEIGHT || this.currentHp <= 0) {
            this.kill();
        }
        if(this.dizzy > 0) this.dizzy--;
        if(this.primaryCountDown >0) this.primaryCountDown--;
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
        if (left && !right && isFree()) {
            this.setToRight(false);
            this.xSpeed -= getPlatAccelerate();
            if(this.xSpeed < -getPlantMaxSpeed()) this.xSpeed = -getPlantMaxSpeed();
        } else if (right && !left && isFree()) {
            this.setToRight(true);
            this.xSpeed += getPlatAccelerate();
            if(this.xSpeed > getPlantMaxSpeed()) this.xSpeed = getPlantMaxSpeed();
        } else if (this.xSpeed > 0){
            this.xSpeed -= getPlatAccelerate();
            if(this.xSpeed < 0) this.xSpeed = 0;
        } else {
            this.xSpeed += getPlatAccelerate();
            if(this.xSpeed > 0) this.xSpeed = 0;
        }
        setX(getX() + xSpeed);

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
        if(this.invincible <= 0 || this.invincible%8 < 4) super.paint();

        GraphicsContext pen = getGameScene().getGraphicsContext();
        if(getTeamTag() == 1) {
            pen.save();
            pen.setFill(Color.YELLOW);
            pen.fillRect(0, 0, 406, 27);
            pen.setFill(Color.RED);
            pen.fillRect( 3, 2, 400 * getCurrentHp()/ getMaxHp(), 25);

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

        paintStatus();
    }

    public void paintStatus(){
    }

    public double getPlantMaxSpeed(){
        return Const.PLANT_SPEED;
    }

    public double getPlatAccelerate(){
        return Const.ACCELERATE_SPEED;
    }

    public double getJumpSpeed(){
        return Const.JUMP_SPEED;
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
        if(this.invincible > 0){
            this.currentEnergy += damage;
        } else if(this.isDefended && this.currentShield > 0){
            if(this.currentShield < damage) damage = this.currentShield;
            this.currentShield -= damage;

            this.currentEnergy += damage;
        } else {
            this.currentHp -= damage;
        }

        if(this.currentShield <= 0) {
            this.isDefended = false;
            this.shieldBroken = true;
//            this.beDizzy(Const.SHIELD_BREAK_DIZZY);
        }

    }

    public void makeDamage(Plant other, int damage) {
        other.takeDamage(damage);
        this.currentEnergy += 2*damage;
    }

    public void respawn(int x, int y){
        if(this.teamTag == 1) this.setToRight(true);
        else this.setToRight(false);
        this.currentHp = this.maxHp;
        this.currentShield = Const.MAX_SHIELD;
        this.setX(x);
        this.setY(y);
        this.setXSpeed(0);
        this.setYSpeed(0);
        this.dizzy = 0;
        this.shieldBroken = false;
        this.setAlive(true);
        this.jumped = true;
        this.primaryCountDown = 0;

        this.invincible = Const.RESPAWN_INVINCIBLE;
    }

    @Override
    public void setGameScene(GameScene gameScene) {
        super.setGameScene(gameScene);
        this.pumpkin.setGameScene(gameScene);
    }

    public boolean isFree() {
        return !isDefended && dizzy==0;
    }

//    public void setFree(boolean free) {
//        isFree = free;
//    }

    public void beDizzy(int time){
        if(this.isDefended || this.invincible > 0) return;
        if(this.dizzy == 0 && time > 0)
            getGameScene().addStatus(new Dizzy(this));
        if(time > this.dizzy) this.dizzy = time;
    }

    public int getDizzy() {
        return dizzy;
    }

    public void beKnockUp(double x, double y){
        if(this.invincible > 0) return;
        this.setXSpeed(x);
        this.setYSpeed(y);
    }

    public int getPrimaryCountDown() {
        return primaryCountDown;
    }

    public void setPrimaryCountDown(int primaryCountDown) {
        this.primaryCountDown = primaryCountDown;
    }

    public int getPrimaryCooldown() {
        return primaryCooldown;
    }

    public void setPrimaryCooldown(int primaryCooldown) {
        this.primaryCooldown = primaryCooldown;
        this.primaryCountDown = 0;
    }

    public int getUltimateEnergy() {
        return ultimateEnergy;
    }

    public void setUltimateEnergy(int ultimateEnergy) {
        this.ultimateEnergy = ultimateEnergy;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }
}
