package com.github.salkinsen.crcalculator;

import com.github.salkinsen.crcalculator.controller.EditController;
import com.github.salkinsen.crcalculator.controller.EnemyGroupEditController;
import com.github.salkinsen.crcalculator.controller.PCGroupEditController;
import com.github.salkinsen.crcalculator.controller.PrimaryController;
import com.github.salkinsen.crcalculator.model.EnemyGroup;
import com.github.salkinsen.crcalculator.model.PCGroup;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * JavaFX App
 */
public class App extends Application {

    private Stage primaryStage;
    private static Scene mainScene;

    public App() {
    }


    @Override
    public void start(Stage primaryStage) throws IOException {

        this.primaryStage = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
        mainScene = new Scene(fxmlLoader.load());
        PrimaryController controller = fxmlLoader.getController();
        controller.setApp(this);    // set up reference in controller to App
        primaryStage.setScene(mainScene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Shows the Dialog that allows user to edit a PC Group.
     *
     * @param pcGroup The Group to be edited.
     * @return true, if everything went as accordingly and OK was clicked. false otherwise (e.g. if Cancel was clicked)
     */
    public boolean showPCGroupEditDialog(PCGroup pcGroup) {
        return showEditDialog(pcGroup, "Edit Player Character Group", "pcGroupEdit.fxml");
    }

    /**
     * Shows the Dialog that allows user to edit an Enemy Group.
     *
     * @param enemyGroup The Group to be edited.
     * @return true, if everything went as accordingly and OK was clicked. false otherwise (e.g. if Cancel was clicked)
     */
    public boolean showEnemyEditDialog(EnemyGroup enemyGroup) {
        return showEditDialog(enemyGroup, "Edit Enemy Group", "enemyGroupEdit.fxml");
    }

    /**
     * Shows the Dialog that allows user to edit a PC Group or an Enemy Group.
     *
     * @param pcOrEnemyGroup Must be of type PCGroup or EnemyGroup or InvalidParameterException will be thrown
     * @param title          Title of the Dialog Stage
     * @param fxml           Name of the fxml-file to be loaded (including .fxml)
     * @return true, if everything went as accordingly and OK was clicked. false otherwise (e.g. if Cancel was clicked)
     */
    private boolean showEditDialog(Object pcOrEnemyGroup, String title, String fxml) {

        if (!(pcOrEnemyGroup instanceof PCGroup) && !(pcOrEnemyGroup instanceof EnemyGroup)) {
            throw new InvalidParameterException("pcOrEnemyGroup must be either of type PCGroup or EnemyGroup");
        }

        try {

            // Create stage and scene. Set scene.
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
            Scene editScene = new Scene(loader.load());
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setScene(editScene);


            // Hand pcOrEnemyGroup to the right controller, so it can be shown
            EditController editController;
            if (pcOrEnemyGroup instanceof PCGroup) {
                PCGroupEditController pcEditController = loader.getController();
                pcEditController.setDialogStage(dialogStage);
                pcEditController.setPcGroup((PCGroup) pcOrEnemyGroup);
                editController = pcEditController;
            } else {
                EnemyGroupEditController enemyEditController = loader.getController();
                enemyEditController.setDialogStage(dialogStage);
                enemyEditController.setEnemyGroup((EnemyGroup) pcOrEnemyGroup);
                editController = enemyEditController;
            }


            // show dialog until its closed (either through OK or Cancel)
            dialogStage.showAndWait();

            return editController.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}