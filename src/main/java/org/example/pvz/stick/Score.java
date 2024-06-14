package org.example.pvz.stick;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.pvz.font.FontLoader;
import org.example.pvz.inter.Status;

public class Score extends Status {
    private static final Font scoreFont = FontLoader.getFont(128);
    private int pointA, pointB;
    public Score( int pointA, int pointB) {
        super(null, -600, 100, 600, 300, 80);
        this.pointA = pointA;
        this.pointB = pointB;
    }

    @Override
    public void update() {
        super.update();
        if(this.getLife() > 60) setX(getX()+40);
        else if(this.getLife() < 20) setX(getX()+40);
        if(this.getLife() == 0) getGameScene().clearCurrentScore();
    }

    @Override
    public void paint() {
        getGameScene().getGraphicsContext().save();
        getGameScene().getGraphicsContext().setFont(scoreFont);
        getGameScene().getGraphicsContext().setFill(Color.WHITE);
        getGameScene().getGraphicsContext().fillText(pointA + " : " + pointB, getX()+115, getY()+200);
        getGameScene().getGraphicsContext().restore();
    }
}
