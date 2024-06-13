package org.example.pvz.inter;


import org.example.pvz.GameScene;

public interface GameMap extends GameObject{
    void init(GameScene gameScene);
    void respawn(Plant plant);
    void pause();
    void resume();
    void start();
    void end();
}
