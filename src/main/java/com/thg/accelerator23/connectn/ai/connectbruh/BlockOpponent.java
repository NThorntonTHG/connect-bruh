package com.thg.accelerator23.connectn.ai.connectbruh;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;

public class BlockOpponent {
    private GameConfig gameConfig;
    private CheckWin checkWin;

    public BlockOpponent (GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public CheckWin getCheckWin() {
        return checkWin;
    }

    public Counter chooseMove(Board board, Counter[][] counterPlacements, Counter counter) {
        Counter opponentCounter = (counter == Counter.X) ? Counter.O : Counter.X;
        for (int rowIndex = 0; rowIndex < board.getConfig().getWidth(); rowIndex++) {
            boolean opponentAboutToWin = checkWin.checkAboutToWin(counterPlacements, board.getConfig(), opponentCounter);
            if (opponentAboutToWin) {
                return counterPlacements[rowIndex][gameConfig.getHeight()];
            }
        }
    }
}
