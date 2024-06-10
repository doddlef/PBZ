package org.example.pvz.box;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.PeaShooter;
import org.example.pvz.inter.Box;

import java.util.ArrayList;
import java.util.List;

public class CloudBox extends Box {
    private static final List<List<Image>> animations = new ArrayList<>();
    static {
        List<Image> frames = new ArrayList<>(5);
        for(int i = 0; i < 5; i++){
            frames.add(new Image(PeaShooter.class.
                    getResource("/org/example/images/cloud/cloud_"+i+".png").toString()));
        }
        animations.add(frames);
    }

    private int life = Const.FRAME_CONTINUE*10;

    public CloudBox(double x, double y) {
        super(animations, x, y, 180, 80);
    }

    @Override
    public void update() {
        super.update();
        this.life--;

        if(this.life <= 0){
            this.kill();
        }
    }

    @Override
    public void resetBounds() {
        this.setBounds(new Rectangle2D(getX(), getY()+25, getWidth(), getHeight()-25));
    }
}
