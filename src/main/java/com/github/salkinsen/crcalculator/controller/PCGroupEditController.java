package com.github.salkinsen.crcalculator.controller;

import com.github.salkinsen.crcalculator.model.PCGroup;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialog to edit Player Character Groups
 */
public class PCGroupEditController implements EditController {

    @FXML
    private TextField playerAmountField;
    @FXML
    private TextField levelField;

    private Stage dialogStage;
    private PCGroup pcGroup;
    private boolean okClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize(){

    }

    /**
     * Set up reference to PCGroup and show current values in text fields.
     * @param pcGroup
     */
    public void setPcGroup(PCGroup pcGroup) {
        this.pcGroup = pcGroup;
        playerAmountField.setText(Integer.toString(pcGroup.getPlayerAmount()));
        levelField.setText(Integer.toString(pcGroup.getLevel()));
    }

    public boolean isOkClicked() {
        return okClicked;
    }



    /**
     * Gets called when OK Button is clicked.
     */
    @FXML
    private void handleOkButtonClicked() {
        if (isInputValid()) {
            pcGroup.setPlayerAmount(Integer.parseInt(playerAmountField.getText()));
            pcGroup.setLevel(Integer.parseInt(levelField.getText()));
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Gets called when Cancel Button is clicked.
     */
    @FXML
    private void handleCancelButtonClicked() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (playerAmountField.getText() == null || playerAmountField.getText().length() == 0) {
            errorMessage += "Please enter the amount of player characters with this level.\n";
        } else {
            try {
                int playerAmount = Integer.parseInt(playerAmountField.getText());
                if (playerAmount <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                errorMessage += "Number of player characters not valid, must be a whole number greater than 0.\n";
            }
        }

        if (levelField.getText() == null || levelField.getText().length() == 0) {
            errorMessage += "Please enter the level that these player characters have.\n";
        } else {
            try {
                int level = Integer.parseInt(levelField.getText());
                if (level < 1 || level > 20) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                errorMessage += "Level entry not valid, must be a whole number between 1 and 20.\n";
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
