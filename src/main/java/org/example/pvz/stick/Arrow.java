package org.example.pvz.stick;

import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.GameScene;
import org.example.pvz.inter.Status;

import java.util.ArrayList;
import java.util.List;

public class Arrow extends Status {
    private static final List<List<Image>> animations = new ArrayList<>();

    static {
        List<Image> frames = new ArrayList<>(2);
        for(int i = 0; i < 2; i++){
            frames.add(new Image(Arrow.class.
                    getResource("/org/example/images/arrow/arrow_"+i+".png").toString(),
                    200, 150, true, true));
        }
        animations.add(frames);
    }

    public Arrow(boolean toRight) {
        super(animations, GameScene.CANVAS_WIDTH/2-100, 100, 200, 150, Const.ROOF_TOP_WARNING);
        setToRight(toRight);
    }
}
