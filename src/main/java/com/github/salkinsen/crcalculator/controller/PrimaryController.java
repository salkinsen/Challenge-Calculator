package com.github.salkinsen.crcalculator.controller;


import com.github.salkinsen.crcalculator.App;
import com.github.salkinsen.crcalculator.model.DataModel;
import com.github.salkinsen.crcalculator.model.DifficultyResult;
import com.github.salkinsen.crcalculator.model.EnemyGroup;
import com.github.salkinsen.crcalculator.model.PCGroup;
import com.github.salkinsen.crcalculator.util.DifficultyCalcUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.util.List;

public class PrimaryController {

    private App app;
    private DataModel dataModel = new DataModel();

    // PC Groups Table and Buttons
    @FXML
    private TableView<PCGroup> pcGroupTable;
    @FXML
    private TableColumn<PCGroup, Integer> playerAmountColumn;
    @FXML
    private TableColumn<PCGroup, Integer> levelColumn;
    @FXML
    private Button deletePCGroupButton;
    @FXML
    private Button editPCGroupButton;

    // Enemy Groups Table, Label and Buttons
    @FXML
    private TableView<EnemyGroup> enemyGroupTable;
    @FXML
    private TableColumn<EnemyGroup, String> enemyNameColumn;
    @FXML
    private TableColumn<EnemyGroup, Integer> enemyAmountColumn;
    @FXML
    private TableColumn<EnemyGroup, Integer> expColumn;
    @FXML
    private TableColumn<EnemyGroup, Boolean> weakerColumn;
    @FXML
    private Button deleteEnemyButton;
    @FXML
    private Button editEnemyButton;

    // Result Labels
    @FXML
    private Label resultInfoLabel;
    @FXML
    private Label totalPCsLabel;
    @FXML
    private Label totalExpLabel;
    @FXML
    private Label totalAdjExpLabel;
    @FXML
    private Label encounterDiffLabel;



    public PrimaryController() {
    }

    /**
     * automatically called after fxml has been loaded
     */
    @FXML
    private void initialize() {
        setUpPlayerCharacterTable();
        setUpEnemiesTable();

        // add listeners, so we can update labels when the lists change
        dataModel.getPcGroups().addListener((ListChangeListener.Change<? extends PCGroup> change) -> {
            updateTotalPCNumber();
            updateResult();
        });
        dataModel.getEnemyGroups().addListener((ListChangeListener.Change<? extends EnemyGroup> change) -> {
            updateResult();
        });

        // show start values for labels
        updateTotalPCNumber();
        updateResult();

    }

    private void updateResult() {
        if (dataModel.getPcGroups().stream().noneMatch(p -> p.getPlayerAmount() > 0)) {
            resultInfoLabel.setText("Waiting for input... Need at least on PC.");
            totalExpLabel.setText("");
            totalAdjExpLabel.setText("");
            encounterDiffLabel.setText("");
        } else if (dataModel.getEnemyGroups().stream().noneMatch(e -> e.getEnemyAmount() > 0 && !e.isWeaker())) {
            resultInfoLabel.setText("Waiting for input... Need at least one enemy that is not marked 'Weaker'.");
            totalExpLabel.setText("");
            totalAdjExpLabel.setText("");
            encounterDiffLabel.setText("");
        } else {
            DifficultyResult result = DifficultyCalcUtil.calculateDifficulty(dataModel.getPcGroups(), dataModel.getEnemyGroups());
            resultInfoLabel.setText("Results");
            totalExpLabel.setText(String.valueOf(result.getTotalExp()));
            totalAdjExpLabel.setText(String.valueOf(result.getTotalAdjustedExp()));
            encounterDiffLabel.setText(result.getEncounterDifficulty());
        }


    }

    private void setUpPlayerCharacterTable() {
        // set up table and connect with ObservableList<PCGroup> pcGroups
        playerAmountColumn.setCellValueFactory(cellData -> cellData.getValue().playerAmountProperty().asObject());
        levelColumn.setCellValueFactory(cellData -> cellData.getValue().levelProperty().asObject());
        pcGroupTable.setItems(dataModel.getPcGroups());

        // Delete Button disabled when list is empty
        deletePCGroupButton.disableProperty().bind(Bindings.isEmpty(pcGroupTable.getItems()));
        // Edit and Delete Button disabled when no item selected
        BooleanBinding noSelection = pcGroupTable.getSelectionModel().selectedItemProperty().isNull();
        editPCGroupButton.disableProperty().bind(noSelection);
        deletePCGroupButton.disableProperty().bind(noSelection);
    }

    private void setUpEnemiesTable() {
        // set up table and connect with ObservableList<PCGroup> pcGroups
        enemyNameColumn.setCellValueFactory(cellData -> cellData.getValue().enemyNameProperty());
        expColumn.setCellValueFactory(cellData -> cellData.getValue().expProperty().asObject());
        enemyAmountColumn.setCellValueFactory(cellData -> cellData.getValue().enemyAmountProperty().asObject());
        weakerColumn.setCellValueFactory(cellData -> cellData.getValue().weakerProperty());
        weakerColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        enemyGroupTable.setItems(dataModel.getEnemyGroups());

        // Delete Button disabled when list is empty
        deleteEnemyButton.disableProperty().bind(Bindings.isEmpty(enemyGroupTable.getItems()));
        // Edit Button disabled when no item selected
        BooleanBinding noSelection = enemyGroupTable.getSelectionModel().selectedItemProperty().isNull();
        editEnemyButton.disableProperty().bind(noSelection);
        deleteEnemyButton.disableProperty().bind(noSelection);

    }


    private void updateTotalPCNumber() {
        int sum = dataModel.getPcGroups().stream().mapToInt(p -> p.getPlayerAmount()).sum();
        totalPCsLabel.setText(Integer.toString(sum));
    }

    /**
     * Get called from App to set up reference
     *
     * @param app
     */
    public void setApp(App app) {
        this.app = app;
    }

    /**
     * Gets called if deleteButton for PC Group is pressed.
     * Deletes row from table. Does not handle case where table is empty, because
     * button should be disabled in that case.
     */
    @FXML
    private void handleDeleteButtonPCGroup() {
        int selectedIndex = pcGroupTable.getSelectionModel().getSelectedIndex();
        pcGroupTable.getItems().remove(selectedIndex);
    }

    /**
     * Gets called if deleteButton for Enemy Group is pressed.
     * Deletes row from table. Does not handle case where table is empty, because
     * button should be disabled in that case.
     */
    @FXML
    private void handleDeleteButtonEnemyGroup() {
        int selectedIndex = enemyGroupTable.getSelectionModel().getSelectedIndex();
        enemyGroupTable.getItems().remove(selectedIndex);
    }

    /**
     * Gets called if New Button for new PCGroup is clicked.
     * Opens Edit Dialog and adds new PC Group if OK is clicked.
     */
    @FXML
    private void handleNewPCGroupClicked() {
        PCGroup newPCGroup = new PCGroup(0, 0);
        boolean okWasClicked = app.showPCGroupEditDialog(newPCGroup);
        if (okWasClicked) {
            dataModel.getPcGroups().add(newPCGroup);
        }
    }

    /**
     * Gets called if New Button for new Enemy Group is clicked.
     * Opens Edit Dialog and adds new Enemy Group if OK is clicked.
     */
    @FXML
    private void handleNewEnemyGroupClicked() {
        EnemyGroup newEnemyGroup = new EnemyGroup("", 0, 0, false);
        boolean okWasClicked = app.showEnemyEditDialog(newEnemyGroup);
        if (okWasClicked) {
            dataModel.getEnemyGroups().add(newEnemyGroup);
        }
    }

    /**
     * Gets called if Edit Button for PCGroup is clicked.
     * Opens Edit Dialog, which will save changes to selectedPCGroup if OK is clicked.
     */
    @FXML
    private void handleEditPCGroupClicked() {
        PCGroup selectedPCGroup = pcGroupTable.getSelectionModel().getSelectedItem();
        if (selectedPCGroup != null) {
            app.showPCGroupEditDialog(selectedPCGroup);
        }
    }

    /**
     * Gets called if Edit Button for Enemy Group is clicked.
     * Opens Edit Dialog, which will save changes to selectedEnemyGroup if OK is clicked.
     */
    @FXML
    private void handleEditEnemyGroupClicked() {
        EnemyGroup selectedEnemyGroup = enemyGroupTable.getSelectionModel().getSelectedItem();
        if (selectedEnemyGroup != null) {
            app.showEnemyEditDialog(selectedEnemyGroup);
        }

    }


}
