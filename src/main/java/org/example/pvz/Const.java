package org.example.pvz;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Const {
    private Const(){}
    private static Properties prop;

    static {
        prop = new Properties();
        try {
            prop.load(new FileReader("src/main/resources/org/example/const/const.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final double PLANT_SPEED = 20;
    public static final int UPDATE_FRAME = Integer.parseInt(prop.getProperty("UPDATE_FRAME"));
    public static final int FRAME_CONTINUE = Integer.parseInt(prop.getProperty("FRAME_CONTINUE"));
    public static final double GRAVITY = Double.parseDouble(prop.getProperty("GRAVITY"));
    public static final double JUMP_SPEED = Double.parseDouble(prop.getProperty("JUMP_SPEED"));
}
