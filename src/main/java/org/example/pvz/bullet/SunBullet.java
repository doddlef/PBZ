package org.example.pvz.bullet;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.inter.Box;
import org.example.pvz.inter.Bullet;
import org.example.pvz.inter.Plant;
import org.example.pvz.inter.Status;
import org.example.pvz.stick.Explode;

import java.util.ArrayList;
import java.util.List;

public class SunBullet extends Bullet {
    private static final List<List<Image>> animations = new ArrayList<>();

    static {
        List<Image> frames = new ArrayList<>(22);
        for(int i = 0; i < 22; i++){
            frames.add(new Image(SunBullet.class.
                    getResource("/org/example/images/Sun/Sun_"+i+".png").toString()));
        }
        animations.add(frames);
    }

    private double acceleration;
    private double xSpeed, ySpeed;
    private int countDown = Const.SUN_COUNT_DOWN;
    private boolean fixed = false;

    public SunBullet(double x, double y, Plant parent, double xSpeed, double acceleration) {
        super(animations, x, y, 70, 70, parent);
        if(this.getParent().isToRight())
            this.xSpeed = xSpeed;
        else
            this.xSpeed = -xSpeed;
        this.ySpeed = -xSpeed;
        this.acceleration = acceleration;
    }


    public SunBullet(double x, double y, Plant parent, double xSpeed) {
        this(x, y, parent, xSpeed,  Const.GRAVITY);
    }

    @Override
    public void update() {
        if(!fixed){
            this.setX(this.getX() + xSpeed);
            this.ySpeed += acceleration;
            this.setY(this.getY() + ySpeed);
        }
        this.countDown--;
        if(this.countDown == 0){
            explode();
        }
        super.update();
    }

    @Override
    public void collideFirstBox(Box box) {
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.fixed = true;
    }

    @Override
    public void reactOther(Plant other) {
        if(other != null && other.getBounds().intersects(this.getBounds())){
            explode();
        }
    }

    private void explode(){
        Status explode = new Explode(this.getX()-15, this.getY()-30);
        getGameScene().addStatus(explode);

        Plant other = getGameScene().getOtherPlant(getTeamTag());
        Rectangle2D bounds = new Rectangle2D(
                this.getX()-15, this.getY()-15, 100, 100
        );
        if(bounds.intersects(other.getBounds())){
            double xDistance =
                    this.getX()+this.getWidth()/2-other.getX()-other.getWidth()/2;
            if(!fixed) {
                getParent().makeDamage(other, Const.SUN_DAMAGE);
                other.beDizzy(Const.SUN_DIZZY);
                other.beKnockUp(Const.SUN_KNOCK*(xDistance>0?-1:1),
                        -Const.SUN_KNOCK);
            } else {
                getParent().makeDamage(other, Const.SUN_DAMAGE/2);
                other.beDizzy(Const.SUN_DIZZY/2 );
                other.beKnockUp(Const.SUN_KNOCK*(xDistance>0?-1:1)/2,
                        -Const.SUN_KNOCK/3);
            }
        }

        List<Box> boxes = getGameScene().collideBox(getBounds());
        for(Box box : boxes){
            box.takeDamage(Const.SUN_DAMAGE);
        }

        kill();
    }

    @Override
    public void resetBounds() {
        this.setBounds(new Rectangle2D(this.getX()+10, this.getY()+10, 50, 50));
    }
}
