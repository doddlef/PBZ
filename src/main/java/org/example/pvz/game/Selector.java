package org.example.pvz.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class  Selector {
    private double x, y;
    private int index = 0;

    private List<Image> images = new ArrayList<>();
    private List<String> names = new ArrayList<>();

    public Selector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Selector getPlantSelector(double x, double y){
        Selector selector = new Selector(x, y);
        Image image;
        String name;

        image = new Image("file:src/main/resources/org/example/images/Cards/card_peashooter.png");
        name = "org.example.pvz.plant.PeaShooter";
        selector.add(image, name);

        image = new Image("file:src/main/resources/org/example/images/Cards/card_sunflower.png");
        name = "org.example.pvz.plant.Sunflower";
        selector.add(image, name);

        return selector;
    }

    public static Selector getMapSelector(double x, double y){
        Selector selector = new Selector(x, y);
        Image image;
        String name;

        image = new Image("file:src/main/resources/org/example/images/background/Background_4.jpg",
                250, 150, true, true);
        name = "org.example.pvz.map.RoofTop";
        selector.add(image, name);
        return selector;
    }

    public void add(Image image, String name) {
        images.add(image);
        names.add(name);
    }

    public void paint(GraphicsContext gc) {
        gc.drawImage(images.get(index), x, y);
    }

    public void next(){
        index = (index + 1) % images.size();
    }

    public void prev(){
        index = (index - 1) % images.size();
        if(index < 0) index = images.size() - 1;
    }

    public String getName(){
        return names.get(index);
    }
}