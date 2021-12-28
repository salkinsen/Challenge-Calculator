package com.github.salkinsen.crcalculator.util;

import com.github.salkinsen.crcalculator.model.EnemyGroup;
import com.github.salkinsen.crcalculator.model.PCGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyCalcUtilTest {


    @Test
    void calculateDifficulty() {
        PCGroup pcGroup = new PCGroup(1, 0);
        List<PCGroup> pcGroupList = new ArrayList<PCGroup>();
        pcGroupList.add(pcGroup);
        List<EnemyGroup> enemyGroupList = new ArrayList<EnemyGroup>();

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> DifficultyCalcUtil.calculateDifficulty(pcGroupList, enemyGroupList),
                "Should throw IllegalArgumentException, because level of PCGroup ist < 1");
    }

    @Test
    void calculateDifficulty2() {
        PCGroup pcGroup = new PCGroup(1, 21);
        List<PCGroup> pcGroupList = new ArrayList<PCGroup>();
        pcGroupList.add(pcGroup);
        List<EnemyGroup> enemyGroupList = new ArrayList<EnemyGroup>();

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> DifficultyCalcUtil.calculateDifficulty(pcGroupList, enemyGroupList),
                "Should throw IllegalArgumentException, because level of PCGroup ist > 20");
    }

    @Test
    void calculateDifficulty3() {
        PCGroup pcGroup1 = new PCGroup(2, 3);
        PCGroup pcGroup2 = new PCGroup(3, 4);
        List<PCGroup> pcGroupList = new ArrayList<PCGroup>();
        pcGroupList.add(pcGroup1);
        pcGroupList.add(pcGroup2);

        EnemyGroup enemyGroup1 = new EnemyGroup("MonsterTypeA", 200, 4, false);
        EnemyGroup enemyGroup2 = new EnemyGroup("MonsterTypeB", 300, 2, false);
        EnemyGroup enemyGroup3 = new EnemyGroup("MonsterTypeC", 20, 3, true);
        List<EnemyGroup> enemyGroupList = new ArrayList<EnemyGroup>();
        enemyGroupList.add(enemyGroup1);
        enemyGroupList.add(enemyGroup2);
        enemyGroupList.add(enemyGroup3);

        DifficultyCalcUtil.calculateDifficulty(pcGroupList,enemyGroupList );

        // TODO: finish
    }

    @Test
    void calculateDifficulty4() {
        PCGroup pcGroup1 = new PCGroup(6, 3);
        List<PCGroup> pcGroupList = new ArrayList<PCGroup>();
        pcGroupList.add(pcGroup1);

        EnemyGroup enemyGroup1 = new EnemyGroup("MonsterTypeA", 1000, 1, false);
        List<EnemyGroup> enemyGroupList = new ArrayList<EnemyGroup>();
        enemyGroupList.add(enemyGroup1);

        DifficultyCalcUtil.calculateDifficulty(pcGroupList,enemyGroupList );

        // TODO: finish
    }

    @Test
    void calculateDifficulty5() {
        PCGroup pcGroup1 = new PCGroup(1, 3);
        List<PCGroup> pcGroupList = new ArrayList<PCGroup>();
        pcGroupList.add(pcGroup1);

        EnemyGroup enemyGroup1 = new EnemyGroup("MonsterTypeA", 100, 20, false);
        List<EnemyGroup> enemyGroupList = new ArrayList<EnemyGroup>();
        enemyGroupList.add(enemyGroup1);

        DifficultyCalcUtil.calculateDifficulty(pcGroupList,enemyGroupList );

        // TODO: finish
    }


}