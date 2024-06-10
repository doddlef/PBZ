package org.example.pvz.bullet;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import org.example.pvz.Const;
import org.example.pvz.inter.Box;
import org.example.pvz.inter.Bullet;
import org.example.pvz.inter.Plant;
import org.example.pvz.stick.PeaExplode;

import java.util.ArrayList;
import java.util.List;

public class PeaBullet extends Bullet {
    private static final List<List<Image>> animations = new ArrayList<>();
    private static AudioClip explodeSound;

    static {
        animations.add(List.of(new Image(PeaBullet.class.
                getResource("/org/example/images/pea/PeaNormal_0.png").toString(),
                30, 30, false, true)));
        explodeSound = new AudioClip(PeaBullet.class.
                getResource("/org/example/sound/bulletExplode.mp3").toString());
    }

    public PeaBullet(double x, double y, Plant parent) {
        super(animations, x, y, 30, 30, parent);
        setToRight(parent.isToRight());
    }

    @Override
    public void update() {
        if(isToRight())
            this.setX(this.getX()+Const.PEA_SPEED);
        else
            this.setX(this.getX()-Const.PEA_SPEED);

        super.update();
    }

    @Override
    public void collideBox(List<Box> collided) {
        if(!collided.isEmpty()){
            kill();
            addExplode();
        }
    }

    @Override
    public void reactOther(Plant other) {
        if(other != null && other.getBounds().intersects(this.getBounds())){
            other.takeDamage(getDamage());
            kill();
            addExplode();
        }
    }

    private void addExplode(){
        getGameScene().addStatus(new PeaExplode(this.getX()-11, this.getY()-8));
    }

    @Override
    public int getDamage() {
        return Const.PEA_DAMAGE;
    }
}
