package com.github.salkinsen.crcalculator.controller;

import com.github.salkinsen.crcalculator.model.EnemyGroup;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EnemyGroupEditController implements EditController{

    @FXML
    private TextField enemyNameField;
    @FXML
    private TextField expField;
    @FXML
    private TextField unitAmountField;
    @FXML
    private CheckBox weakerCheckBox;

    private Stage dialogStage;
    private EnemyGroup enemyGroup;
    private boolean okClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize(){

    }

    /**
     * Set up reference to EnemyGroup and show current values in text fields.
     * @param enemyGroup
     */
    public void setEnemyGroup(EnemyGroup enemyGroup) {
        this.enemyGroup = enemyGroup;
        enemyNameField.setText(enemyGroup.getEnemyName());
        expField.setText(Integer.toString(enemyGroup.getExp()));
        unitAmountField.setText(Integer.toString(enemyGroup.getEnemyAmount()));
        weakerCheckBox.setSelected(enemyGroup.isWeaker());
    }

    public boolean isOkClicked() {
        return okClicked;
    }


    /**
     * Gets called when Cancel Button is clicked.
     */
    @FXML
    private void handleCancelButtonClicked() {
        dialogStage.close();
    }

    /**
     * Gets called when OK Button is clicked.
     */
    @FXML
    private void handleOkButtonClicked() {
        if (isInputValid()) {
            enemyGroup.setEnemyName(enemyNameField.getText());
            enemyGroup.setExp(Integer.parseInt(expField.getText()));
            enemyGroup.setEnemyAmount(Integer.parseInt(unitAmountField.getText()));
            enemyGroup.setWeaker(weakerCheckBox.isSelected());
            okClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (expField.getText() == null || expField.getText().length() == 0) {
            errorMessage += "Please enter the experience points per unit.\n";
        } else {
            try {
                int playerAmount = Integer.parseInt(expField.getText());
                if (playerAmount < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                errorMessage += "Experience points not valid, must be a whole number greater or equal than 0.\n";
            }
        }

        if (unitAmountField.getText() == null || unitAmountField.getText().length() == 0) {
            errorMessage += "Please enter the amount of units.\n";
        } else {
            try {
                int level = Integer.parseInt(unitAmountField.getText());
                if (level <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                errorMessage += "Amount of units not valid, must be a whole number greater than 0.\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Entries");
            alert.setHeaderText("Please correct invalid entries or abort editing by clicking Cancel.");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
