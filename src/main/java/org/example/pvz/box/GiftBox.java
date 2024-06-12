package org.example.pvz.box;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.inter.Box;
import org.example.pvz.inter.Bullet;
import org.example.pvz.inter.GameEvent;
import org.example.pvz.inter.Plant;
import org.example.pvz.stick.DoomShroom;

import java.util.ArrayList;
import java.util.List;

public class GiftBox extends Box {
    private static final List<List<Image>> animations = new ArrayList<>();
    static {
        List<Image> frames = new ArrayList<>(1);
        for(int i = 0; i < 1; i++){
            frames.add(new Image(GiftBox.class.
                    getResource("/org/example/images/giftBox/GiftBox_"+i+".png").toString()));
        }
        animations.add(frames);
    }

    private int hp;
    private int countDown;
    private GameEvent event;
    private boolean onGround = false;

    public GiftBox(double x, double y, int hp, GameEvent event, int countDown) {
        super(animations, x, y, 60, 60);
        this.hp = hp;
        this.event = event;
        this.countDown = countDown;
    }

    public static GiftBox doomShroomBox(double x, double y, int hp, int countDown) {
        GameEvent event = (gameScene, sprite) -> {
            gameScene.addStatus(new DoomShroom(sprite.getX()-21, sprite.getY()-31));
        };
        return new GiftBox(x, y, hp, event, countDown);
    }

    @Override
    public void update() {
        super.update();
        if(getGameScene().collideBox(new Rectangle2D(getX(), getY()+60, getWidth(), 3)).isEmpty()) {
            this.setY(getY()+ Const.GRAVITY);
            this.onGround = false;
        } else {
            this.onGround = true;
        }

        this.countDown--;
        if(this.countDown == 0){
            this.kill();
            event.happen(getGameScene(), this);
        }
    }

    @Override
    public void collide(Plant plant, double oldX, double oldY) {
        if(onGround)
            super.collide(plant, oldX, oldY);
    }

    @Override
    public boolean collide(Bullet bullet) {
        this.hp -= bullet.getDamage();
        if(this.hp <= 0){
            this.kill();
            event.happen(getGameScene(), this);
        }
        return true;
    }
}
