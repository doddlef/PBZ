package org.example.pvz.game;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.pvz.LocalPlantController;
import org.example.pvz.font.FontLoader;
import org.example.pvz.inter.Plant;
import org.example.pvz.inter.PlantController;
import org.example.pvz.map.RoofTop;

import java.lang.reflect.InvocationTargetException;

public class SelectScene implements MyScene{
    private static final Image selectBackground =
            new Image("file:src/main/resources/org/example/images/background/selectBack.png");
    private GameDirector gameDirector;
    private Scene scene;
    private Canvas canvas = new Canvas(GameScene.CANVAS_WIDTH, GameScene.CANVAS_HEIGHT);
    private BorderPane root = new BorderPane(canvas);
    private Selector selectorA;
    private Selector selectorB;
    private Selector mapSelector;
    private SoundEffect soundEffect;

    private PlantController controllerA;
    private PlantController controllerB;

    private static class SoundEffect {
        private AudioClip clickCard = new AudioClip("file:src/main/resources/org/example/sound/clickCard.wav");
        private Media backgroundMusic = new Media(getClass().getResource("/org/example/sound/chooseYourSeeds.wav")
                .toString());
        private MediaPlayer backgroundMusicPlayer = new MediaPlayer(backgroundMusic);

        private void clickCard(){
            clickCard.play();
        }

        private void backgroundMusic(){
            backgroundMusicPlayer.play();
        }

        private void end(){
            backgroundMusicPlayer.stop();
        }
    }


    public SelectScene(GameDirector gameDirector) {
        this.gameDirector = gameDirector;
        this.controllerA = LocalPlantController.getLocalPlayerOne();
        this.controllerB = LocalPlantController.getLocalPlayerTwo();
        this.soundEffect = new SoundEffect();
    }

    public void loadScene(Stage stage) {
        scene = new Scene(root, GameScene.CANVAS_WIDTH, GameScene.CANVAS_HEIGHT);
        stage.setScene(scene);
        stage.show();

        selectorA = Selector.getPlantSelector(200, 200);
        selectorB = Selector.getPlantSelector(700, 200);
        mapSelector = Selector.getMapSelector(375, 205);

        scene.setOnKeyPressed(this::keyPressed);

        soundEffect.backgroundMusic();
        paint();
    }

    private void keyPressed(KeyEvent event){
        KeyCode code = event.getCode();
        if(code == KeyCode.W) {
            selectorA.prev();
            soundEffect.clickCard();
            plantUpdate();
        } else if(code == KeyCode.S) {
            selectorA.next();
            soundEffect.clickCard();
            plantUpdate();
        } else if(code == KeyCode.UP) {
            selectorB.prev();
            soundEffect.clickCard();
            plantUpdate();
        } else if(code == KeyCode.DOWN) {
            selectorB.next();
            soundEffect.clickCard();
            plantUpdate();
        } else if(code == KeyCode.SPACE) {
            this.triggerGame();
            soundEffect.clickCard();
            plantUpdate();
        }
    }

    public void paint(){
        Font smallFont = FontLoader.getFont(14);
        Font largeFont = FontLoader.getFont(24);

        GraphicsContext pen = canvas.getGraphicsContext2D();
        canvas.getGraphicsContext2D().drawImage(selectBackground, 0, 0);

        pen.save();
        pen.setFont(smallFont);
        pen.fillText(controllerA.toString(), 20, 440);
        pen.fillText(controllerB.toString(), 820, 440);
        pen.restore();

        pen.save();
        pen.setFont(largeFont);
        pen.setFill(Color.RED);
        pen.fillText("Player 1", 180, 100);
        pen.fillPolygon(new double[]{200D, 300D, 250D}, new double[]{130D, 130D, 160D}, 3);
        pen.setFill(Color.BLUE);
        pen.fillText("Player 2", 680, 100);
        pen.fillPolygon(new double[]{700D, 800D, 750D}, new double[]{130D, 130D, 160D}, 3);
        pen.restore();

        pen.save();
        pen.setFont(smallFont);
        selectorA.paint(canvas.getGraphicsContext2D());
        pen.fillText("w", 244, 190);
        pen.fillText("s", 244, 350);
        selectorB.paint(canvas.getGraphicsContext2D());
        pen.fillText("up", 744, 190);
        pen.fillText("down", 728, 350);

        mapSelector.paint(canvas.getGraphicsContext2D());

        pen.setFont(largeFont);
        pen.fillText("--enter space to start--", 300, 500);
        pen.restore();
    }

    private void plantUpdate(){
        selectorA.paint(canvas.getGraphicsContext2D());
        selectorB.paint(canvas.getGraphicsContext2D());
    }

    private void triggerGame(){
        try {
            Class<Plant> plantClassA = (Class<Plant>) Class.forName(selectorA.getName());
            Class<Plant> plantClassB = (Class<Plant>) Class.forName(selectorB.getName());
            Plant plantA = plantClassA.getConstructor().newInstance();
            Plant plantB = plantClassB.getConstructor().newInstance();
            gameDirector.gameStart(new RoofTop(), plantA, controllerA, plantB, controllerB);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void quit() {
        soundEffect.end();
    }
}
