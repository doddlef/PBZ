package org.example.pvz.stick;

import javafx.scene.image.Image;
import org.example.pvz.game.GameScene;
import org.example.pvz.inter.Plant;
import org.example.pvz.inter.Status;

import java.util.ArrayList;
import java.util.List;

public class PlantFood extends Status {
    private static final List<List<Image>> animations = new ArrayList<>();

    static {
        List<Image> frames = new ArrayList<>(4);
        for(int i = 0; i < 4; i++){
            frames.add(new Image(PlantFood.class.
                    getResource("/org/example/images/PlantFood/PlantFood_"+i+".png").toString()));
        }
        animations.add(frames);
    }

    private Plant parent;

    private PlantFood(double x, double y) {
        super(animations, x, y, 100, 93, 0);
    }

    public static PlantFood create(Plant parent){
        PlantFood ret;
        if(parent.getTeamTag() == 1){
            ret = new PlantFood(30, 80);
        } else {
            ret = new PlantFood(GameScene.CANVAS_WIDTH-130, 80);
        }
        ret.parent = parent;
        return ret;
    }

    @Override
    public void update() {
        double amount = parent.getCurrentEnergy()*1.0/parent.getUltimateEnergy();
        if(parent.getCurrentEnergy() >= parent.getUltimateEnergy()){
            this.setFrameIndex(0);
        } else if(amount > 0.6)
            this.setFrameIndex(1);
        else if(amount > 0.3)
            this.setFrameIndex(2);
        else
            this.setFrameIndex(3);
    }

    @Override
    public void paint() {
        getGameScene().getGraphicsContext()
                .drawImage(animations.get(0).get(getFrameIndex()), getX(), getY());
    }
}
