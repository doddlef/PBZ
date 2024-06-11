package org.example.pvz.stick;

import javafx.scene.image.Image;
import org.example.pvz.inter.Status;

import java.util.ArrayList;
import java.util.List;

public class Explode extends Status {
    private static final List<List<Image>> animations = new ArrayList<>();

    static {
        List<Image> frames = new ArrayList<>(1);
        frames.add(new Image(Dizzy.class.
                getResource("/org/example/images/explode/explode.png").toString()));
        animations.add(frames);
    }

    public Explode(double x, double y) {
        super(animations, x, y, 100, 100, 7);
    }
}
