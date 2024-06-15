package org.example.pvz.game;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.pvz.font.FontLoader;

public class EndScene implements MyScene{
    private short pointA, pointB;
    private Scene scene;
    private GameDirector gameDirector;
    private Pane root = new Pane();
    private Rectangle rectA;
    private Rectangle rectB;
    private Text textA;
    private Text textB;
    private Text winnerText;
    private SoundEffect soundEffect = new SoundEffect();

    private static class SoundEffect {
        private AudioClip scoreSound = new AudioClip("file:src/main/resources/org/example/sound/squashing.wav");
        private AudioClip winnerSound = new AudioClip("file:src/main/resources/org/example/sound/finalfanfare.wav");

        private void scoreSound() {
            scoreSound.play();
        }

        private void winnerSound() {
            winnerSound.play();
        }
    }

    public EndScene(GameDirector gameDirector) {
        this.gameDirector = gameDirector;
    }

    private void keyPressed(KeyEvent e) {
        if(e.getCode() == KeyCode.SPACE) {
            gameDirector.selectScene();
        }
    }

    public void loadScene(short pointA, short pointB) {
        scene = new Scene(root, GameScene.CANVAS_WIDTH, GameScene.CANVAS_HEIGHT);
        gameDirector.getStage().setScene(scene);
        gameDirector.getStage().show();
        scene.setOnKeyPressed(this::keyPressed);

        this.pointA = pointA;
        this.pointB = pointB;

        rectA = new Rectangle(0, 0, GameScene.CANVAS_WIDTH/2, GameScene.CANVAS_HEIGHT);
        rectA.setFill(Color.BLUE);
        rectB = new Rectangle(GameScene.CANVAS_WIDTH/2, 0,
                GameScene.CANVAS_WIDTH/2, GameScene.CANVAS_HEIGHT);
        rectB.setFill(Color.RED);

        Font font = FontLoader.getFont(128);
        textA = new Text(200, 200, "");
        textA.setFont(font);
        textA.setFill(Color.WHITE);

        textB = new Text(700, 200, "");
        textB.setFont(font);
        textB.setFill(Color.WHITE);

        Font font2 = FontLoader.getFont(72);
        winnerText = new Text(150, 400, "");
        winnerText.setFont(font2);
        winnerText.setFill(Color.WHITE);

        Font indicateFont = FontLoader.getFont(20);
        Text indicateText = new Text(300, 500, "");
        indicateText.setFont(indicateFont);
        indicateText.setFill(Color.WHITE);

        root.getChildren().addAll(rectA, rectB, textA, textB, winnerText, indicateText);
        Timeline timeline = new Timeline();

        KeyFrame addScoreA = new KeyFrame(Duration.seconds(1.2),
                actionEvent -> soundEffect.scoreSound(),
                new KeyValue(textA.textProperty(), pointA+""));
        KeyFrame addScoreB = new KeyFrame(Duration.seconds(2.4),
                actionEvent -> soundEffect.scoreSound(),
                new KeyValue(textB.textProperty(), pointB+""));

        String winnerString = pointA>pointB?"Player 1 Win":"Player 2 Win";
        Color winnerColor = pointA>pointB? Color.BLUE:Color.RED;
        KeyFrame winAnimation = new KeyFrame(Duration.seconds(4),
                event -> {
                    rectA.setFill(winnerColor);
                    rectB.setFill(winnerColor);
                    soundEffect.winnerSound();
                },
                new KeyValue(winnerText.textProperty(), winnerString),
                new KeyValue(indicateText.textProperty(), "--enter space to continue--"));
        timeline.getKeyFrames().addAll(addScoreA, addScoreB, winAnimation);
        timeline.play();
    }

    @Override
    public void quit() {

    }
}
