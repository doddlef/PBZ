package org.example.pvz.bullet;

import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.inter.Bullet;
import org.example.pvz.inter.Plant;

import java.util.ArrayList;
import java.util.List;

public class StarBullet extends Bullet {
    private static final List<List<Image>> animations = new ArrayList<>();
    static {
        animations.add(List.of(
                new Image(StarBullet.class.
                        getResource("/org/example/images/StarBullet/StarBullet_0.png")
                        .toString(), 20, 20,
                        true, true)));
    }

    private double ySpeed;

    public StarBullet(double x, double y, Plant parent, double ySpeed) {
        super(animations, x, y, 20, 20, parent);
        this.ySpeed = ySpeed;
    }

    @Override
    public void update() {
        this.setY(this.getY()+this.ySpeed);
        if(isToRight()) {
            this.setX(this.getX()+ Const.STAR_BULLET_SPEED);
        } else {
            this.setX(this.getX()- Const.STAR_BULLET_SPEED);
        }
        super.update();
    }

    @Override
    public int getDamage() {
        return Const.STAR_BULLET_DAMAGE;
    }

    @Override
    public void reactOther(Plant other) {
        if(other != null && other.getBounds().intersects(this.getBounds())){
            getParent().makeDamage(other, getDamage());
            kill();
        }
    }
}
