package org.example.pvz.inter;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public interface PlantController {
    void setPlant(Plant plant);
    void reactKeyEvent(KeyEvent keyEvent);
    void shutdown();
    KeyCode getRightKey();
    void setRightKey(KeyCode rightKey);
    KeyCode getLeftKey();
    void setLeftKey(KeyCode leftKey);
    KeyCode getJumpKey();
    void setJumpKey(KeyCode jumpKey);
    KeyCode getDefendKey();
    void setDefendKey(KeyCode defendKey);
    KeyCode getAttackKey();
    void setAttackKey(KeyCode attackKey);
    KeyCode getPrimaryKey();
    void setPrimaryKey(KeyCode primaryKey);
    KeyCode getUltimateKey();
    void setUltimateKey(KeyCode ultimateKey);
}
