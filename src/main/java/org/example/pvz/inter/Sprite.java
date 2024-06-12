package org.example.pvz.inter;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.GameScene;

import java.util.ArrayList;
import java.util.List;

public abstract class Sprite implements GameObject{
    private double x, y;
    private double width, height;
    private boolean alive = true;
    private boolean toRight = true;
    private GameScene gameScene;
    private Rectangle2D bounds;

    private List<List<Image>> animations = new ArrayList<>();
    private int animationIndex = 0;
    private int frameIndex = 0;
    private int frameTime = 0;

    public Sprite() {
    }

    public Sprite(List<List<Image>> animations, double x, double y, double width, double height) {
        this.animations = animations;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle2D(x, y, width, height);
    }

    public Sprite(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle2D(x, y, width, height);
    }

    @Override
    public void kill() {
        this.alive = false;
    }

    @Override
    public void paint() {
        frameTime++;
        if(frameTime >= Const.FRAME_CONTINUE) {
            frameIndex++;
            frameTime = 0;
        }

        if(frameIndex >= animations.get(animationIndex).size()){
            frameIndex = 0;
        }

        gameScene.getGraphicsContext().fillRect(this.bounds.getMinX(), this.bounds.getMinY(),
                this.bounds.getWidth(), this.bounds.getHeight());
        if(this.toRight){
            gameScene.getGraphicsContext().drawImage(animations.get(animationIndex).get(frameIndex),
                    x, y, width, height);
        } else{
            gameScene.getGraphicsContext().drawImage(animations.get(animationIndex).get(frameIndex),
                    x+width, y, -width, height);
        }
    }

    @Override
    public void update() {
        if(this.x + width < 0 || this.x >= GameScene.CANVAS_WIDTH
                || this.y + height < 0 || this.y >= GameScene.CANVAS_HEIGHT){
            this.alive = false;
        }
        this.resetBounds();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getFrameIndex() {
        return frameIndex;
    }

    public void setFrameIndex(int frameIndex) {
        this.frameIndex = frameIndex;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public void setAnimationIndex(int animationIndex) {
        this.animationIndex = animationIndex;
    }

    public Rectangle2D getBounds(){
        return bounds;
    }

    public void resetBounds(){
        this.bounds = new Rectangle2D(x, y, width, height);
    }

    public boolean isToRight() {
        return toRight;
    }

    public void setToRight(boolean toRight) {
        this.toRight = toRight;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    public void setBounds(Rectangle2D bounds) {
        this.bounds = bounds;
    }
}
