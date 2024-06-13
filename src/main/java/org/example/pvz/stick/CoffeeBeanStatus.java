package org.example.pvz.stick;

import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.inter.Status;

import java.util.ArrayList;
import java.util.List;

public class CoffeeBeanStatus extends Status {
    private static final List<List<Image>> animations = new ArrayList<>();

    static {
        List<Image> frames = new ArrayList<>(8);
        for(int i = 9; i < 17; i++){
            frames.add(new Image(CoffeeBeanStatus.class.
                    getResource("/org/example/images/CoffeeBean/CoffeeBean_"+i+".png").toString()));
        }
        animations.add(frames);
    }

    public CoffeeBeanStatus(double x, double y) {
        super(animations, x, y, 39, 97, 8*Const.FRAME_CONTINUE-1);
    }
}
