package com.github.salkinsen.crcalculator.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Model class for Player Character Group
 */
public class PCGroup {

    private final IntegerProperty playerAmount;
    private final IntegerProperty level;


    public PCGroup(int playerAmount, int level) {
        this.playerAmount = new SimpleIntegerProperty(playerAmount);
        this.level = new SimpleIntegerProperty(level);
    }

    public int getPlayerAmount() {
        return playerAmount.get();
    }

    public IntegerProperty playerAmountProperty() {
        return playerAmount;
    }

    public void setPlayerAmount(int playerAmount) {
        this.playerAmount.set(playerAmount);
    }

    public int getLevel() {
        return level.get();
    }

    public IntegerProperty levelProperty() {
        return level;
    }

    public void setLevel(int level) {
        this.level.set(level);
    }
}
