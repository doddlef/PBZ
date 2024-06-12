package org.example.pvz.stick;

import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.inter.Status;

import java.util.ArrayList;
import java.util.List;

public class JalapenoExplode extends Status {
    private static final List<List<Image>> animations = new ArrayList<>();

    static {
        List<Image> frames = new ArrayList<>(8);
        for(int i = 0; i < 8; i++){
            frames.add(new Image(JalapenoExplode.class.
                    getResource("/org/example/images/JalapenoExplode/JalapenoExplode_"+i+".png").toString()));
        }
        animations.add(frames);
    }

    public JalapenoExplode(double x, double y) {
        super(animations, x, y, 755, 133, 8* Const.FRAME_CONTINUE-2);
    }
}
