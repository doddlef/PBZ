package org.example.pvz;

import javafx.scene.image.Image;
import org.example.pvz.inter.Plant;
import org.example.pvz.inter.Sprite;

import java.util.ArrayList;
import java.util.List;

public class PeaShooter extends Plant {
    private static final List<List<Image>> animations = new ArrayList<>();
    private static final double WIDTH = 70, HEIGHT = 70;

    static {
        List<Image> frames = new ArrayList<>(13);
        for(int i = 0; i < 13; i++){
            frames.add(new Image(PeaShooter.class.
                    getResource("/org/example/images/peashooter/Peashooter_"+i+".png").toString()));
        }
        animations.add(frames);
    }

    public PeaShooter(double x, double y) {
        super(animations, x, y, WIDTH, HEIGHT);
    }

    @Override
    public void attack() {

    }

    @Override
    public void attackRelease() {

    }
}
