package com.github.salkinsen.crcalculator.model;

import javafx.beans.property.*;

/**
 * Model class for Enemy Group
 */
public class EnemyGroup {

    private final StringProperty enemyName;
    private final IntegerProperty exp;
    private final IntegerProperty enemyAmount;
    private final BooleanProperty weaker;

    public boolean isWeaker() {
        return weaker.get();
    }

    public BooleanProperty weakerProperty() {
        return weaker;
    }

    public void setWeaker(boolean weaker) {
        this.weaker.set(weaker);
    }


    public EnemyGroup(String enemyName, int exp, int enemyAmount, boolean weaker) {
        this.enemyName = new SimpleStringProperty(enemyName);
        this.exp = new SimpleIntegerProperty(exp);
        this.enemyAmount = new SimpleIntegerProperty(enemyAmount);
        this.weaker = new SimpleBooleanProperty(weaker);
    }

    public String getEnemyName() {
        return enemyName.get();
    }

    public StringProperty enemyNameProperty() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName.set(enemyName);
    }

    public int getExp() {
        return exp.get();
    }

    public IntegerProperty expProperty() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp.set(exp);
    }

    public int getEnemyAmount() {
        return enemyAmount.get();
    }

    public IntegerProperty enemyAmountProperty() {
        return enemyAmount;
    }

    public void setEnemyAmount(int enemyAmount) {
        this.enemyAmount.set(enemyAmount);
    }
}
