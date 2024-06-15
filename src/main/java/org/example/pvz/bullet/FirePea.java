package org.example.pvz.bullet;

import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.inter.Bullet;
import org.example.pvz.inter.Plant;

import java.util.ArrayList;
import java.util.List;

public class FirePea extends Bullet {
    private static final List<List<Image>> animations = new ArrayList<>();
    static {
        animations.add(List.of(
                new Image(FirePea.class.
                        getResource("/org/example/images/fireball/Fireball_0.png")
                        .toString()),
                new Image(FirePea.class.
                        getResource("/org/example/images/fireball/Fireball_1.png")
                        .toString())));
    }

    private double speed;

    public FirePea(double x, double y, Plant parent) {
        super(animations, x, y, 56, 34, parent);
        this.speed = Const.PEA_SPEED * (this.isToRight()?1:-1);
    }

    @Override
    public void reactOther(Plant other) {
        if(other != null && other.getBounds().intersects(this.getBounds())){
            getParent().makeDamage(other, Const.PEA_DAMAGE*2);
            other.beDizzy(Const.FIRE_PEA_DIZZY);
            if(isToRight()) other.beKnockUp(Const.FIRE_PEA_KNOCK, 0);
            else other.beKnockUp(-Const.FIRE_PEA_KNOCK, 0);
            kill();
        }
    }

    @Override
    public int getDamage() {
        return Const.PEA_DAMAGE*2;
    }

    @Override
    public void update() {
        this.setX(this.getX()+speed);
        super.update();
    }
}
