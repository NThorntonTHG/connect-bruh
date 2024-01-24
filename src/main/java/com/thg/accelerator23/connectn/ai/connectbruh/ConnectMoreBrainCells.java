package com.thg.accelerator23.connectn.ai.connectbruh;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;

public class ConnectMoreBrainCells {
    Board board;

    public ConnectMoreBrainCells(Board board) {
        this.board = board;
    }

    public int getWinningMove() {
        for (int col = 0; col < 10; col++) {
            try {
                Board simulatedBoard = new Board(board, col, O);
                if (didPlayerWin(simulatedBoard, O)) {
                    return col;
                }
            } catch (InvalidMoveException e) {}
        }

        return -1;
    }

    public int getBlockingMove() {
        for (int col = 0; col < 10; col++) {
            try {
                Board simulatedBoard = new Board(board, col, X);
                if (didPlayerWin(simulatedBoard, X)) {
                    return col;
                }
            } catch (InvalidMoveException e) {}
        }
        return -1;
    }

    public boolean didPlayerWin(Board board, Counter playerDisk) {
        // check horizontal
        int height = 8;
        int width = 10;
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width - 3; j++)
                for(int k = j; k < j + 4 && board.getCounterAtPosition(new Position(i, k)) == playerDisk; k++)
                    if(k == j+3)
                        return true;
        // check vertical
        for(int i = 0; i < height - 3; i++)
            for(int j = 0; j < width; j++)
                for(int k = i; k < i + 4 && board.getCounterAtPosition(new Position(k, j)) == playerDisk; k++)
                    if(k == i+3)
                        return true;
        // check diagonal down right
        for(int i = 0; i < height - 3; i++)
            for(int j = 0; j < width - 3; j++)
                for(int k = 0; k < 4 && board.getCounterAtPosition(new Position(i+k, j+k)) == playerDisk; k++)
                    if(k == 3)
                        return true;
        // check diagonal down
        for(int i = 0; i < height - 3; i++)
            for(int j = 3; j < width; j++)
                for(int k = 0; k < 4 && board.getCounterAtPosition(new Position(i+k, j-k)) == playerDisk; k++)
                    if(k == 3)
                        return true;
        return false;
    }
}
