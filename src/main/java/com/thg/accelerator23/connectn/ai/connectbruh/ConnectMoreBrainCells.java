package com.thg.accelerator23.connectn.ai.connectbruh;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.*;
import java.util.stream.Stream;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;

public class ConnectMoreBrainCells {
    Board board;
    Counter counter;
    Counter opponentCounter;

    public ConnectMoreBrainCells(Board board, Counter counter) {
        this.board = board;
        this.counter = counter;
        this.opponentCounter = this.counter.getOther();
    }

    public int makeMove() {
        if (earlyGame()) {
            moves++;
            return prioritiseCentre();
        } else if (getWinningMove() > 0) {
            moves++;
            return getWinningMove();
        } else if (getBlockingMove() > 0) {
            moves++;
            return getBlockingMove();
        } else {
            moves++;
            return getRandomMove();
        }
    }

    private boolean earlyGame() {
        for (int col = 0; col < 10; col++) {
            if (!board.hasCounterAtPosition(new Position(col, 2))) {
                return true;
            };
        }
        return false;
    }

    private int prioritiseCentre() {
        return 5;
    }

    public int getWinningMove() {
        for (int col = 0; col < 10; col++) {
            try {
                Board simulatedBoard = new Board(board, col, opponentCounter);
                if (didPlayerWin(simulatedBoard, opponentCounter)) {
                    return col;
                }
            } catch (InvalidMoveException ignored) {}
        }

        return -1;
    }

    public int getBlockingMove() {
        for (int col = 0; col < 10; col++) {
            try {
                Board simulatedBoard = new Board(board, col, counter);
                if (didPlayerWin(simulatedBoard, counter)) {
                    return col;
                }
            } catch (InvalidMoveException e) {}
        }
        return -1;
    }

    public int getRandomMove() {
        int totalColumns = board.getConfig().getWidth();
        Random random = new Random();


        for (int attempts = 0; attempts < totalColumns; attempts++) {
            int col = random.nextInt(totalColumns);
            if (!isColumnFull(col)){
                return col;
            }
        }
        return -1;
    }

    public boolean isColumnFull(int columnIndex) {
        int totalRows = board.getConfig().getHeight();
        for (int rowIndex = 0; rowIndex < totalRows; rowIndex++) {
            if (board.getCounterAtPosition(new Position(columnIndex, rowIndex)) == null) {
                // If any position is empty, the column is not full
                return false;
            }
        }
        return true;
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
