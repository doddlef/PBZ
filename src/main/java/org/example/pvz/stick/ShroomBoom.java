package org.example.pvz.stick;

import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.inter.Status;

import java.util.ArrayList;
import java.util.List;

public class ShroomBoom extends Status {
    private static final List<List<Image>> animations = new ArrayList<>();

    static {
        List<Image> frames = new ArrayList<>(10);
        for(int i = 0; i < 10; i++){
            frames.add(new Image(ShroomBoom.class.
                    getResource("/org/example/images/DoomShroomBoom/DoomShroomBoom_"+i+".png").toString()));
        }
        animations.add(frames);
    }

    public ShroomBoom(double x, double y) {
        super(animations, x, y, 283, 324, 10 * Const.FRAME_CONTINUE-1);
    }
}
