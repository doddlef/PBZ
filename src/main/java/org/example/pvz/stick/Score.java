package org.example.pvz.stick;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.example.pvz.inter.Status;

public class Score extends Status {
    private int pointA, pointB;
    public Score( int pointA, int pointB) {
        super(null, 400,200, 400, 300, 60);
        this.pointA = pointA;
        this.pointB = pointB;
    }

    @Override
    public void paint() {
        getGameScene().getGraphicsContext().save();
        getGameScene().getGraphicsContext().setFont(Font.font(160));
        getGameScene().getGraphicsContext().setFill(Color.ORANGE);
        getGameScene().getGraphicsContext().fillText(pointA+":"+pointB, getX(), getY());
        getGameScene().getGraphicsContext().restore();
    }
}
