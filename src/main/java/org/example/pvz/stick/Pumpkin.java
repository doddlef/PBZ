package org.example.pvz.stick;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.inter.Plant;
import org.example.pvz.inter.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Pumpkin extends Sprite {
    private static final List<List<Image>> animations = new ArrayList<>();

    static {
        List<Image> frames = new ArrayList<>(8);
        for(int i = 0; i < 8; i++){
            frames.add(new Image(Pumpkin.class.
                    getResource("/org/example/images/PumpkinHead/PumpkinHead/PumpkinHead_"+i+".png").toString()));
        }
        animations.add(frames);
        frames = List.of(new Image(Pumpkin.class.
                getResource("/org/example/images/PumpkinHead/PumpkinHead_cracked1/PumpkinHead_cracked1_0.png")
                .toString()));
        animations.add(frames);
        frames = List.of(new Image(Pumpkin.class.
                getResource("/org/example/images/PumpkinHead/PumpkinHead_cracked2/PumpkinHead_cracked2_0.png")
                .toString()));
        animations.add(frames);
    }

    public Pumpkin() {
        super(animations, 0, 0, 97, 67);
        this.setBounds(Const.EMPTY_RECT);
    }

    @Override
    public void update() {}

    @Override
    public Rectangle2D getBounds() {
        return Const.EMPTY_RECT;
    }

    public void defend(Plant plant, int status){
        this.setX(plant.getX()+plant.getWidth()/2-48);
        this.setY(plant.getY()+plant.getHeight()-67);
        if(status > 15){
            this.setAnimationIndex(0);
        } else if(status > 5)
            this.setAnimationIndex(1);
        else
            this.setAnimationIndex(2);
    }
}
