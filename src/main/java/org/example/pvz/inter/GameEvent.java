package org.example.pvz.inter;

import org.example.pvz.game.GameScene;

public interface GameEvent {
    void happen(GameScene gameScene, Sprite trigger);
}
