package org.example.pvz;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.pvz.inter.Plant;
import org.example.pvz.inter.PlantController;

public class LocalPlantController implements PlantController {
    private KeyCode rightKey;
    private KeyCode leftKey;
    private KeyCode jumpKey;
    private KeyCode defendKey;
    private KeyCode attackKey;
    private KeyCode primaryKey;
    private KeyCode ultimateKey;
    private Plant plant;

    @Override
    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public static PlantController getLocalPlayerOne(){
        PlantController plantController = new LocalPlantController();
        plantController.setRightKey(KeyCode.D);
        plantController.setLeftKey(KeyCode.A);
        plantController.setJumpKey(KeyCode.W);
        plantController.setDefendKey(KeyCode.SHIFT);
        plantController.setAttackKey(KeyCode.F);
        plantController.setPrimaryKey(KeyCode.E);
        plantController.setUltimateKey(KeyCode.Q);
        return plantController;
    }

    public static PlantController getLocalPlayerTwo(){
        PlantController plantController = new LocalPlantController();
        plantController.setRightKey(KeyCode.RIGHT);
        plantController.setLeftKey(KeyCode.LEFT);
        plantController.setJumpKey(KeyCode.UP);
        plantController.setDefendKey(KeyCode.DOWN);
        plantController.setAttackKey(KeyCode.SPACE);
        plantController.setPrimaryKey(KeyCode.J);
        plantController.setUltimateKey(KeyCode.K);
        return plantController;
    }

    @Override
    public void reactKeyEvent(KeyEvent keyEvent) {
        KeyCode key = keyEvent.getCode();
        if(keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
            if(key == rightKey){
                plant.setRight(true);
            } else if(key == leftKey){
                plant.setLeft(true);
            } else if(key == jumpKey){
                plant.jump();
            } else if(key == defendKey){
                plant.defend();
            } else if(key == attackKey){
                plant.attack();
            } else if(key == primaryKey){
                plant.primaryPress();
            } else if(key == ultimateKey){
                plant.ultimatePress();
            }
        } else if(keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
            if(key == rightKey){
                plant.setRight(false);
            } else if(key == leftKey){
                plant.setLeft(false);
            } else if(key == jumpKey){
                plant.jumpRelease();
            } else if(key == defendKey){
                plant.defendRelease();
            } else if(key == attackKey){
                plant.attackRelease();
            } else if(key == primaryKey){
                plant.primaryRelease();
            }
        }
    }

    @Override
    public void shutdown() {
    }

    public KeyCode getRightKey() {
        return rightKey;
    }

    public void setRightKey(KeyCode rightKey) {
        this.rightKey = rightKey;
    }

    public KeyCode getLeftKey() {
        return leftKey;
    }

    public void setLeftKey(KeyCode leftKey) {
        this.leftKey = leftKey;
    }

    public KeyCode getJumpKey() {
        return jumpKey;
    }

    public void setJumpKey(KeyCode jumpKey) {
        this.jumpKey = jumpKey;
    }

    public KeyCode getDefendKey() {
        return defendKey;
    }

    public void setDefendKey(KeyCode defendKey) {
        this.defendKey = defendKey;
    }

    public KeyCode getAttackKey() {
        return attackKey;
    }

    public void setAttackKey(KeyCode attackKey) {
        this.attackKey = attackKey;
    }

    public KeyCode getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(KeyCode primaryKey) {
        this.primaryKey = primaryKey;
    }

    public KeyCode getUltimateKey() {
        return ultimateKey;
    }

    public void setUltimateKey(KeyCode ultimateKey) {
        this.ultimateKey = ultimateKey;
    }
}
