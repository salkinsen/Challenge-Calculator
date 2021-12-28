package com.github.salkinsen.crcalculator.model;

/**
 * Model Class for the Result of the Encounter Difficulty Calculation
 */
public class DifficultyResult {

    private int totalExp;
    private int totalAdjustedExp;
    private String encounterDifficulty;

    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
    }

    public int getTotalAdjustedExp() {
        return totalAdjustedExp;
    }

    public void setTotalAdjustedExp(int totalAdjustedExp) {
        this.totalAdjustedExp = totalAdjustedExp;
    }

    public String getEncounterDifficulty() {
        return encounterDifficulty;
    }

    public void setEncounterDifficulty(String encounterDifficulty) {
        this.encounterDifficulty = encounterDifficulty;
    }
}
