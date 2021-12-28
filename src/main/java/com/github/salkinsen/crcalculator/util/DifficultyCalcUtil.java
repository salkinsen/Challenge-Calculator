package com.github.salkinsen.crcalculator.util;

import com.github.salkinsen.crcalculator.model.DifficultyResult;
import com.github.salkinsen.crcalculator.model.EnemyGroup;
import com.github.salkinsen.crcalculator.model.PCGroup;
import com.github.salkinsen.crcalculator.model.PartyExpThreshold;

import java.util.List;

/**
 * Utility Class for calculating Encounter Difficulties.
 */
public class DifficultyCalcUtil {

    private static final int[] EASY_LIST = {0, 25, 50, 75, 125, 250, 300, 350, 450, 550, 600, 800, 1000, 1100, 1250, 1400, 1600, 2000, 2100, 2400, 2800};
    private static final int[] MEDIUM_LIST = {0, 50, 100, 150, 250, 500, 600, 750, 900, 1100, 1200, 1600, 2000, 2200, 2500, 2800, 3200, 3900, 4200, 4900, 5700};
    private static final int[] HARD_LIST = {0, 75, 150, 225, 375, 750, 900, 1100, 1400, 1600, 1900, 2400, 3000, 3400, 3800, 4300, 4800, 5900, 6300, 7300, 8500};
    private static final int[] DEADLY_LIST = {0, 100, 200, 400, 500, 1100, 1400, 1700, 2100, 2400, 2800, 3600, 4500, 5100, 5700, 6400, 7200, 8800, 9500, 10900, 12700};


    /**
     * @param pcGroups    Must all have valid levels (1 - 20). Must include at least one group with playerAmount > 0.
     * @param enemyGroups Must include at least one group with enemyAmount > 0 and weaker == false.
     * @return
     */
    public static DifficultyResult calculateDifficulty(List<PCGroup> pcGroups, List<EnemyGroup> enemyGroups) {

        if (pcGroups.stream().anyMatch(p -> p.getLevel() <= 0 || p.getLevel() > 20)) {
            throw new IllegalArgumentException("Level of every PC in pcGroups must be >= 1 and <= 20.");
        }

        if (pcGroups.stream().noneMatch(p -> p.getPlayerAmount() > 0)) {
            throw new IllegalArgumentException("pcGroups must include at least one group with playerAmount > 0.");
        }

        if (enemyGroups.stream().noneMatch(e -> e.getEnemyAmount() > 0 && !e.isWeaker())) {
            throw new IllegalArgumentException("enemyGroups must include at least one group with enemyAmount > 0 and weaker == false.");
        }


        PartyExpThreshold partyExpThreshold = determinePartyExpThresholds(pcGroups);
        int totalEnemyExp = calcTotalEnemyExp(enemyGroups);
        int adjustedEnemyExp = calcAdjustedEnemyExp(enemyGroups, pcGroups, totalEnemyExp);


        // TODO: implement!
        return new DifficultyResult();
    }

    private static PartyExpThreshold determinePartyExpThresholds(List<PCGroup> pcGroups) {

        int easyThreshold = calcExpThresholdForDifficultyLevel(pcGroups, EASY_LIST);
        int mediumThreshold = calcExpThresholdForDifficultyLevel(pcGroups, MEDIUM_LIST);
        int hardThreshold = calcExpThresholdForDifficultyLevel(pcGroups, HARD_LIST);
        int deadlyThreshold = calcExpThresholdForDifficultyLevel(pcGroups, DEADLY_LIST);

        return new PartyExpThreshold(easyThreshold, mediumThreshold, hardThreshold, deadlyThreshold);

    }

    private static int calcExpThresholdForDifficultyLevel(List<PCGroup> pcGroups, int[] difficultyLevel) {
        int total = 0;
        for (PCGroup pcGroup : pcGroups) {
            total += pcGroup.getPlayerAmount() * difficultyLevel[pcGroup.getLevel()];
        }
        return total;
    }

    private static int calcTotalEnemyExp(List<EnemyGroup> enemyGroups) {
        int total = 0;
        for (EnemyGroup enemyGroup : enemyGroups) {
            total += enemyGroup.getEnemyAmount() * enemyGroup.getExp();
        }
        return total;
    }

    private static int calcAdjustedEnemyExp(List<EnemyGroup> enemyGroups, List<PCGroup> pcGroups, int totalEnemyExp) {

        int totalPCs = pcGroups.stream().mapToInt(PCGroup::getPlayerAmount).sum();

        int multiplierAdjuster = 0;
        if (totalPCs < 3) multiplierAdjuster = +1;
        if (totalPCs >= 6) multiplierAdjuster = -1;

        int totalSignificantEnemies = enemyGroups.stream().mapToInt(e -> e.isWeaker() ? 0 : e.getEnemyAmount()).sum();
        float[] multiplierArr = {0.5f, 1f, 1.5f, 2f, 2.5f, 3f, 4f, 4f};

        float multiplier;
        if (totalSignificantEnemies == 1) {
            multiplier = multiplierArr[1 + multiplierAdjuster];
        } else if (totalSignificantEnemies == 2) {
            multiplier = multiplierArr[2 + multiplierAdjuster];
        } else if (totalSignificantEnemies >= 3 && totalSignificantEnemies <= 6) {
            multiplier = multiplierArr[3 + multiplierAdjuster];
        } else if (totalSignificantEnemies >= 7 && totalSignificantEnemies <= 10) {
            multiplier = multiplierArr[4 + multiplierAdjuster];
        } else if (totalSignificantEnemies >= 11 && totalSignificantEnemies <= 14) {
            multiplier = multiplierArr[5 + multiplierAdjuster];
        } else if (totalSignificantEnemies >= 15) {
            multiplier = multiplierArr[6 + multiplierAdjuster];
        } else {
            throw new IllegalArgumentException("No significant enemy in enemyGroups.");
        }

        return Math.round(multiplier * totalEnemyExp);
    }

}
