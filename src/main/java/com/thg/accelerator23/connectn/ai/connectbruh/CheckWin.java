package com.thg.accelerator23.connectn.ai.connectbruh;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;

public class CheckWin {

    public boolean checkAboutToWin(Counter[][] counterPlacements, GameConfig gameConfig, Counter counter) {
        //check horizontal
        int width = gameConfig.getWidth();
        int height = gameConfig.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width -3; j++) {
                for (int k = j; k < j + 4 && counterPlacements[i][k] == counter; k++) {
                    if (k == j+2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
