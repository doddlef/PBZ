package org.example.pvz.stick;

import javafx.scene.image.Image;
import org.example.pvz.inter.Status;

import java.util.ArrayList;
import java.util.List;

public class PeaExplode extends Status {
    private static final List<List<javafx.scene.image.Image>> animations = new ArrayList<>();

    static {
        List<Image> frames = new ArrayList<>(1);
        for(int i = 0; i < 1; i++){
            frames.add(new Image(PeaExplode.class.
                    getResource("/org/example/images/pea/PeaNormalExplode_0.png").toString()));
        }
        animations.add(frames);
    }

    public PeaExplode(double x, double y) {
        super(animations, x, y, 52, 46, 5);
    }
}
