package org.example.pvz.inter;

import javafx.scene.image.Image;

import java.util.List;

public class Bullet extends Sprite{
    private Plant parent;
    private int teamTag;

    public Bullet(List<List<Image>> animations, double x, double y, double width, double height, Plant parent) {
        super(animations, x, y, width, height);
        this.parent = parent;
        if(this.parent == null) this.teamTag = 0;
        else {
            this.teamTag = parent.getTeamTag();
            this.setToRight(parent.isToRight());
        }
    }

    public Plant getParent() {
        return parent;
    }

    public void setParent(Plant parent) {
        this.parent = parent;
    }

    public int getTeamTag() {
        return teamTag;
    }

    public void setTeamTag(int teamTag) {
        this.teamTag = teamTag;
    }

    public int getDamage(){
        return 0;
    }

    @Override
    public void update() {
        super.update();
        List<Box> collided = getGameScene().collideBox(this);
        collideBox(collided);

        getGameScene().getOtherPlant(getTeamTag()).forEach(this::reactOther);
    }

    public void reactOther(Plant other){}

    public void collideBox(List<Box> collided){
        for(Box box : collided){
            if(box.collide(this)){
                collideFirstBox(box);
                break;
            }
        }
    }

    public void collideFirstBox(Box box){
        box.takeDamage(this.getDamage());
        this.kill();
    }
}
