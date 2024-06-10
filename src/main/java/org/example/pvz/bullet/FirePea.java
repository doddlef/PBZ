package org.example.pvz.bullet;

import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.GameScene;
import org.example.pvz.inter.Bullet;
import org.example.pvz.inter.Plant;

import java.util.ArrayList;
import java.util.List;

public class FirePea extends Bullet {
    private static final List<List<Image>> animations = new ArrayList<>();
    static {
        animations.add(List.of(
                new Image(PeaBullet.class.
                        getResource("/org/example/images/fireball/Fireball_0.png")
                        .toString()),
                new Image(PeaBullet.class.
                        getResource("/org/example/images/fireball/Fireball_1.png")
                        .toString())));
    }

    public FirePea(double x, double y, Plant parent) {
        super(animations, x, y, 56, 34, parent);
        setToRight(parent.isToRight());
    }

    @Override
    public void reactOther(Plant other) {
        if(other != null && other.getBounds().intersects(this.getBounds())){
            other.takeDamage(getDamage());
            kill();
        }
    }

    @Override
    public int getDamage() {
        return Const.PEA_DAMAGE*2;
    }

    @Override
    public void update() {
        if(isToRight())
            this.setX(this.getX()+Const.PEA_SPEED*1.2);
        else
            this.setX(this.getX()-Const.PEA_SPEED*1.2);

        super.update();

        Plant other = getGameScene().getOtherPlant(getTeamTag());
        if(other != null && other.getBounds().intersects(this.getBounds())){
            other.takeDamage(Const.PEA_DAMAGE);
            other.beDizzy(Const.FIRE_PEA_DIZZY);
            if(isToRight()) other.beKnockUp(Const.FIRE_PEA_KNOCK, 0);
            else other.beKnockUp(-Const.FIRE_PEA_KNOCK, 0);
            kill();
        }
    }
}
