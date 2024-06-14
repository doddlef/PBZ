package org.example.pvz.bullet;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.inter.Bullet;
import org.example.pvz.inter.Plant;
import org.example.pvz.inter.Status;
import org.example.pvz.stick.CoffeeBeanStatus;

import java.util.ArrayList;
import java.util.List;

public class CoffeeBean extends Bullet {
    private static final List<List<Image>> animations = new ArrayList<>();

    static {
        List<Image> frames = new ArrayList<>(10);
        for(int i = 0; i < 10; i++){
            frames.add(new Image(CoffeeBean.class.
                    getResource("/org/example/images/CoffeeBean/CoffeeBean_"+i+".png").toString()));
        }
        animations.add(frames);
    }

    public CoffeeBean(double x, double y) {
        super(animations, x, y, 39, 97, null);
    }

    @Override
    public void update() {
        super.update();
        if(getGameScene().collideBox(new Rectangle2D(getX(), getY()+60, getWidth(), 3)).isEmpty()) {
            this.setY(getY()+ Const.GRAVITY);
        }
    }

    @Override
    public void reactOther(Plant other) {
        if(this.isAlive() && this.getBounds().intersects(other.getBounds())) {
            other.setCurrentEnergy(other.getCurrentEnergy()+ Const.COFFEE_BEAN_ENERGY);
            Status coffeeBean = new CoffeeBeanStatus(other.getX()+other.getWidth()/2-getWidth()/2,
                    other.getY()-20);
            getGameScene().addStatus(coffeeBean);
            this.kill();
        }
    }

    @Override
    public void resetBounds() {
        this.setBounds(new Rectangle2D(getX(), getY(), getWidth(), getWidth()));
    }
}
