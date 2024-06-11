package org.example.pvz;

import javafx.scene.media.AudioClip;
import org.example.pvz.bullet.PeaBullet;

public class MediaPlayer {
    private AudioClip peaExplode;

    public MediaPlayer() {
        peaExplode = new AudioClip(PeaBullet.class.
                getResource("/org/example/sound/bulletExplode.mp3").toString());
    }

    public void peaExplode(){
        peaExplode.play();
    }
}
