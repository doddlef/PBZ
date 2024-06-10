package org.example.pvz;

import javafx.scene.image.Image;
import org.example.pvz.inter.Bullet;
import org.example.pvz.inter.Plant;

import java.util.ArrayList;
import java.util.List;

public class PeaBullet extends Bullet {
    private static final List<List<Image>> animations = new ArrayList<>();
    static {
        animations.add(List.of(new Image(PeaBullet.class.
                getResource("/org/example/images/pea/PeaNormal_0.png").toString(),
                30, 30, false, true)));
    }

    public PeaBullet(double x, double y, Plant parent, boolean toRight) {
        super(animations, x, y, 30, 30, parent);
        setToRight(toRight);
    }

    @Override
    public void collide(Plant plant) {
        if(plant.getTeamTag() != this.getTeamTag()){
            plant.takeDamage(Const.PEA_DAMAGE);
            kill();
        }
    }

    @Override
    public void update() {
        if(isToRight())
            this.setX(this.getX()+Const.PEA_SPEED);
        else
            this.setX(this.getX()-Const.PEA_SPEED);
        if(this.getX()+this.getWidth() < 0) this.kill();
        if(this.getX() > GameScene.CANVAS_WIDTH) this.kill();

        super.update();

        Plant other = getGameScene().getOtherPlant(getTeamTag());
        if(other != null && other.getBounds().intersects(this.getBounds())){
            other.takeDamage(Const.PEA_DAMAGE);
            kill();
        }
    }

    @Override
    public int getDamage() {
        return Const.PEA_DAMAGE;
    }
}
