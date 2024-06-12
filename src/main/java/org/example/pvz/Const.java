package org.example.pvz;

import javafx.geometry.Rectangle2D;

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
    public static final double ACCELERATE_SPEED = 4;
    public static final int UPDATE_FRAME = Integer.parseInt(prop.getProperty("UPDATE_FRAME"));
    public static final int FRAME_CONTINUE = Integer.parseInt(prop.getProperty("FRAME_CONTINUE"));
    public static final double GRAVITY = Double.parseDouble(prop.getProperty("GRAVITY"));
    public static final double JUMP_SPEED = Double.parseDouble(prop.getProperty("JUMP_SPEED"));
    public static final double MAX_DROP = Double.parseDouble(prop.getProperty("MAX_DROP"));
    public static final int KILL_ENERGY = Integer.parseInt(prop.getProperty("KILL_ENERGY"));
    public static final int RESPAWN_INVINCIBLE = Integer.parseInt(prop.getProperty("RESPAWN_INVINCIBLE"));

    public static final String PLATFORM_PATH = prop.getProperty("PLATFORM_PATH");

    public static final int PEA_SHOOTER_HP = Integer.parseInt(prop.getProperty("PEA_SHOOTER_HP"));
    public static final int PEA_DAMAGE = Integer.parseInt(prop.getProperty("PEA_DAMAGE"));
    public static final int PEA_SPEED = Integer.parseInt(prop.getProperty("PEA_SPEED"));
    public static final int PEA_SHOOTER_ATTACK_COOLDOWN =
            Integer.parseInt(prop.getProperty("PEA_SHOOTER_ATTACK_COOLDOWN"));
    public static final int PEA_SHOOTER_AMMO = Integer.parseInt(prop.getProperty("PEA_SHOOTER_AMMO"));
    public static final int PEA_SHOOTER_AMMO_SPEED = Integer.parseInt(prop.getProperty("PEA_SHOOTER_AMMO_SPEED"));
    public static final int PEA_COMBO_TIME = Integer.parseInt(prop.getProperty("PEA_COMBO_TIME"));
    public static final int FIRE_PEA_DIZZY = Integer.parseInt(prop.getProperty("FIRE_PEA_DIZZY"));
    public static final double FIRE_PEA_KNOCK = Double.parseDouble(prop.getProperty("FIRE_PEA_KNOCK"));
    public static final int PEA_PRIMARY_CD = Integer.parseInt(prop.getProperty("PEA_PRIMARY_CD"));
    public static final int PEA_ULTIMATE_TIME = Integer.parseInt(prop.getProperty("PEA_ULTIMATE_TIME"));

    public static final int MAX_SHIELD = Integer.parseInt(prop.getProperty("MAX_SHIELD"));
    public static final int SHIELD_BREAK_DIZZY = Integer.parseInt(prop.getProperty("SHIELD_BREAK_DIZZY"));

    public static final int SUNFLOWER_HP = Integer.parseInt(prop.getProperty("SUNFLOWER_HP"));
    public static final int SUNFLOWER_AMMO = Integer.parseInt(prop.getProperty("SUNFLOWER_AMMO"));
    public static final int SUNFLOWER_AMMO_SPEED = Integer.parseInt(prop.getProperty("SUNFLOWER_AMMO_SPEED"));
    public static final int SUNFLOWER_HP_RECOVER = Integer.parseInt(prop.getProperty("SUNFLOWER_HP_RECOVER"));
    public static final int SUNFLOWER_ATTACK_COOLDOWN= Integer.parseInt(prop.getProperty("SUNFLOWER_ATTACK_COOLDOWN"));
    public static final int SUNFLOWER_MAX_READY = Integer.parseInt(prop.getProperty("SUNFLOWER_MAX_READY"));
    public static final int SUNFLOWER_RECOVER_SPEED = Integer.parseInt(prop.getProperty("SUNFLOWER_RECOVER_SPEED"));
    public static final int SUNFLOWER_PRIMARY_CD = Integer.parseInt(prop.getProperty("SUNFLOWER_PRIMARY_CD"));
    public static final int SUNFLOWER_ULTIMATE_ENERGY = Integer.parseInt(prop.getProperty("SUNFLOWER_ULTIMATE_ENERGY"));

    public static final int SUN_COUNT_DOWN = Integer.parseInt(prop.getProperty("SUN_COUNT_DOWN"));
    public static final int SUN_DAMAGE = Integer.parseInt(prop.getProperty("SUN_DAMAGE"));
    public static final int SUN_KNOCK = Integer.parseInt(prop.getProperty("SUN_KNOCK"));
    public static final int SUN_DIZZY = Integer.parseInt(prop.getProperty("SUN_DIZZY"));

    public static final int JALAPENO_LIFE = Integer.parseInt(prop.getProperty("JALAPENO_LIFE"));
    public static final int JALAPENO_DAMAGE = Integer.parseInt(prop.getProperty("JALAPENO_DAMAGE"));
    public static final int JALAPENO_DIZZY = Integer.parseInt(prop.getProperty("JALAPENO_DIZZY"));
    public static final double JALAPENO_SPEED = Double.parseDouble(prop.getProperty("JALAPENO_SPEED"));

    public static final int TORCH_WOOD_HP = Integer.parseInt(prop.getProperty("TORCH_WOOD_HP"));
    public static final int TORCH_LIFE_INDEX = Integer.parseInt(prop.getProperty("TORCH_LIFE_INDEX"));

    public static final Rectangle2D EMPTY_RECT = new Rectangle2D(0, 0, 0, 0);

    public static final int ROOF_TOP_WIND = Integer.parseInt(prop.getProperty("ROOF_TOP_WIND"));
    public static final int ROOF_TOP_WIND_CD = Integer.parseInt(prop.getProperty("ROOF_TOP_WIND_CD"));
    public static final int ROOF_TOP_WARNING = Integer.parseInt(prop.getProperty("ROOF_TOP_WARNING"));
    public static final int ROOF_TOP_BOX_CD = Integer.parseInt(prop.getProperty("ROOF_TOP_BOX_CD"));

    public static final int DOOMSHROOM_DAMAGE = Integer.parseInt(prop.getProperty("DOOMSHROOM_DAMAGE"));
    public static final int DOOMSHROOM_DISTANCE = Integer.parseInt(prop.getProperty("DOOMSHROOM_DISTANCE"));
}
