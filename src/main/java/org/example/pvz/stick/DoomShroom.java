package org.example.pvz.stick;

import javafx.scene.image.Image;
import org.example.pvz.Const;
import org.example.pvz.inter.Plant;
import org.example.pvz.inter.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DoomShroom extends Status {
    private static final List<List<Image>> animations = new ArrayList<>();

    static {
        List<Image> frames = new ArrayList<>(10);
        for(int i = 0; i < 10; i++){
            frames.add(new Image(ShroomBoom.class.
                    getResource("/org/example/images/DoomShroom/DoomShroom_"+i+".png").toString()));
        }
        animations.add(frames);
    }

    public DoomShroom(double x, double y) {
        super(animations, x, y, 102, 91, 10*Const.FRAME_CONTINUE-1);
    }

    @Override
    public void update() {
        super.update();
        if(this.getLife() == 0){
            for (Plant plant : getGameScene().getAllPlant()) {
                double xDistance = Math.abs(plant.getX()+plant.getWidth()/2-getX()-getWidth()/2);
                double yDistance = Math.abs(plant.getY()+plant.getHeight()/2-getY()-getHeight()/2);
                double distance = Math.sqrt(xDistance*xDistance+yDistance*yDistance);
                if(distance > Const.DOOMSHROOM_DISTANCE) continue;
                int damage = (int)((1-distance/Const.DOOMSHROOM_DISTANCE) * Const.DOOMSHROOM_DAMAGE);
                if(damage > 0) plant.takeDamage(damage);
            }

            Status boom = new ShroomBoom(this.getX()-90, this.getY()-233);
            getGameScene().addStatus(boom);
        }
    }
}
