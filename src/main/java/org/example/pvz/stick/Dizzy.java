package org.example.pvz.stick;

import javafx.scene.image.Image;
import org.example.pvz.inter.Plant;
import org.example.pvz.inter.Status;

import java.util.ArrayList;
import java.util.List;

public class Dizzy extends Status {
    private static final List<List<Image>> animations = new ArrayList<>();

    static {
        List<Image> frames = new ArrayList<>(2);
        for(int i = 0; i < 2; i++){
            frames.add(new Image(Dizzy.class.
                    getResource("/org/example/images/dizzy/dizzy_"+i+".png").toString()));
        }
        animations.add(frames);
    }
    private Plant parent;

    public Dizzy(Plant plant) {
        super(animations, plant.getX()-10, plant.getY()-10,
                80, 45, 0);
        this.parent = plant;
    }

    @Override
    public void update() {
        this.setX(parent.getX()-10);
        this.setY(parent.getY()-10);
        if(parent.getDizzy() == 0) this.kill();
        if(!parent.isAlive()) this.kill();
    }
}
