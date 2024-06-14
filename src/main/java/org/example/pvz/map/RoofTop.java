package org.example.pvz.map;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.example.pvz.Const;
import org.example.pvz.game.GameScene;
import org.example.pvz.box.CloudBox;
import org.example.pvz.box.GiftBox;
import org.example.pvz.box.Platform;
import org.example.pvz.inter.Background;
import org.example.pvz.inter.Box;
import org.example.pvz.inter.GameMap;
import org.example.pvz.inter.Plant;
import org.example.pvz.stick.Arrow;

import java.util.Random;

public class RoofTop implements GameMap {
    private static final Image background = new Image(Background.class.
            getResource("/org/example/images/background/Background_4.jpg").toString());
    private static final int random[] = new int[]{-2, -2, -1, -1, -1, 1, 1, 1, 2, 2};
    private static final Random rand = new Random();

    private GameScene gameScene;

    private Media backgroundMusic = new Media(getClass().getResource("/org/example/sound/roofTop/battle.mp3")
            .toString());
    private MediaPlayer backgroundMusicPlayer = new MediaPlayer(backgroundMusic);
    private int direction = 0;
    private int windCountDown = Const.ROOF_TOP_WIND_CD;
    private int giftCountDown = Const.ROOF_TOP_BOX_CD;

    @Override
    public void update() {
        windCountDown--;
        giftCountDown--;
        if (windCountDown == 0) {
            if(direction == 0){
                direction = random[rand.nextInt(random.length)];
                windCountDown = Const.ROOF_TOP_WARNING;
                gameScene.addStatus(new Arrow(direction>0));
            } else {
                double speed = direction*Const.ROOF_TOP_WIND;
                for (Plant plant : gameScene.getAllPlant()) {
                    plant.beKnockUp(speed, -Math.abs(speed));
                }
                windCountDown = Const.ROOF_TOP_WIND_CD;
                direction = 0;
            }
        }
        if(giftCountDown == 0){
            GiftBox giftBox;
            if(rand.nextInt(10) > 4) {
                giftBox = GiftBox.doomShroomBox(rand.nextDouble(100, 860),
                        -50, 40, 200);
                System.out.println("boom");
            } else {
                giftBox = GiftBox.coffeeBeanBox(rand.nextDouble(100, 860),
                        -50, 40, 200);
            }
            getGameScene().addBox(giftBox);
            giftCountDown = Const.ROOF_TOP_BOX_CD;
        }
    }

    @Override
    public void paint() {
        gameScene.getGraphicsContext().drawImage(background, -200, 0.0);
    }

    @Override
    public void kill() {

    }

    @Override
    public void init(GameScene gameScene) {
        this.gameScene = gameScene;

        Box base = new Platform(100, 500, 350, 60);
        gameScene.addBox(base);

        base = new Platform(550, 500, 350, 60);
        gameScene.addBox(base);

        Box platform = new Platform(300, 300, 400, 30);
        gameScene.addBox(platform);

        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    @Override
    public void respawn(Plant plant) {
        if(plant.getTeamTag() == 1){
            plant.respawn(140, 120);
            Box cloud = new CloudBox(120, 200);
            gameScene.addBox(cloud);
        } else {
            plant.respawn(800, 120);
            Box cloud = new CloudBox(700, 200);
            gameScene.addBox(cloud);
        }
    }

    @Override
    public void pause() {
        backgroundMusicPlayer.pause();
    }

    @Override
    public void resume() {
        backgroundMusicPlayer.play();
    }

    @Override
    public void start() {
        backgroundMusicPlayer.play();
    }

    @Override
    public void end() {
        backgroundMusicPlayer.stop();
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }
}
