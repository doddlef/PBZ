package org.example.pvz.box;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.GameScene;
import org.example.pvz.bullet.FirePea;
import org.example.pvz.bullet.PeaBullet;
import org.example.pvz.inter.Box;
import org.example.pvz.inter.Bullet;
import org.example.pvz.inter.Plant;

import java.util.ArrayList;
import java.util.List;

public class TorchWood extends Box {
    private static final List<List<Image>> animations = new ArrayList<>();
    private static final Image cloud = new Image(TorchWood.class.
            getResource("/org/example/images/cloud/cloud_0.png").toString()
    , 86, 50, false, true);

    static {
        List<Image> frames = new ArrayList<>(9);
        for(int i = 0; i < 9; i++){
            frames.add(new Image(TorchWood.class.
                    getResource("/org/example/images/TorchWood/TorchWood_"+i+".png").toString()));
        }
        animations.add(frames);
    }

    private int hp = Const.TORCH_WOOD_HP;
    private int timeIndex = Const.TORCH_LIFE_INDEX;
    private int teamTag;
    private boolean onGround = false;

    public TorchWood(double x, double y, Plant parent) {
        super(animations, x, y, 73, 87);
        this.teamTag = parent.getTeamTag();
    }

    @Override
    public void update() {
        super.update();
        if(this.hp <= 0) this.kill();
        this.timeIndex--;
        if(this.timeIndex <= 0) {
            this.timeIndex = Const.TORCH_LIFE_INDEX;
            this.hp--;
        }
    }

    @Override
    public boolean collide(Bullet bullet) {
        if(bullet instanceof PeaBullet p && p.getTeamTag() == teamTag){
            p.kill();
            Bullet fireBullet = new FirePea(p.getX(), p.getY(), p.getParent());
            fireBullet.setToRight(p.isToRight());
            getGameScene().addBullet(fireBullet);
            return false;
        }
        if(bullet.getTeamTag() == teamTag){
            return false;
        }
        return true;
    }

    @Override
    public void paint() {
        super.paint();
        if(!onGround) getGameScene().getGraphicsContext().drawImage(cloud, getX()-5, getY()+60);
    }

    @Override
    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    @Override
    public void resetBounds() {
        this.setBounds(new Rectangle2D(this.getX(), this.getY()+25,
                getWidth(), getHeight()-25));
    }

    @Override
    public void setGameScene(GameScene gameScene) {
        super.setGameScene(gameScene);
        List<Box> collide = getGameScene().collideBox(new Rectangle2D(this.getX(),
                this.getY()+87, 86, 5));
        this.onGround = !collide.isEmpty();
    }
}
