package org.example.pvz.inter;

import javafx.scene.image.Image;

import java.util.List;

public class Bullet extends Sprite{
    private Plant parent;
    private int teamTag;

    public Bullet(List<List<Image>> animations, double x, double y, double width, double height, Plant parent) {
        super(animations, x, y, width, height);
        this.parent = parent;
        this.teamTag = parent.getTeamTag();
    }

    public void collide(Plant plant){
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
}
