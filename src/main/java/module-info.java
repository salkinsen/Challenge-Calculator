module com.github.salkinsen {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.github.salkinsen.crcalculator to javafx.fxml;
    exports com.github.salkinsen.crcalculator;

    opens com.github.salkinsen.crcalculator.controller to javafx.fxml;
    exports com.github.salkinsen.crcalculator.controller;
}
