package com.github.salkinsen.crcalculator;

/***
 * The wrapper class is necessary for the maven-shade-plugin to work with JavaFX.
 */
public class Main {
    public static void main(String[] args) {
        JavaFXApp.main(args);
    }
}
