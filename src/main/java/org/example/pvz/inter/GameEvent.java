package org.example.pvz.inter;

import org.example.pvz.GameScene;

public interface GameEvent {
    void happen(GameScene gameScene, Sprite trigger);
}
