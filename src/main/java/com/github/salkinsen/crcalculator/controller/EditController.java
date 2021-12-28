package com.github.salkinsen.crcalculator.controller;

import javafx.stage.Stage;

/**
 * A Controller for an Edit Dialog.
 */
public interface EditController {

    /**
     *
     * @return  true, if Dialog was closed via OK. false otherwise.
     */
    boolean isOkClicked();

    void setDialogStage(Stage dialogStage);
}
