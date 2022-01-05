package com.github.salkinsen.crcalculator.model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModel {

    // construct lists with an extractor, so that we can be updated when elements within list change
    private ObservableList<PCGroup> pcGroups =
            FXCollections.observableArrayList(p -> new Observable[]{p.playerAmountProperty(), p.levelProperty()});

    //private ObservableList<EnemyGroup> enemyGroups = FXCollections.observableArrayList();
    private ObservableList<EnemyGroup> enemyGroups =
            FXCollections.observableArrayList(e -> new Observable[]{e.enemyAmountProperty(), e.expProperty(), e.weakerProperty()});

    public DataModel() {
    }

    public ObservableList<PCGroup> getPcGroups() {
        return pcGroups;
    }

    public ObservableList<EnemyGroup> getEnemyGroups() {
        return enemyGroups;
    }


}
