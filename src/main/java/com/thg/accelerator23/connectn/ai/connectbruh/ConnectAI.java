package com.thg.accelerator23.connectn.ai.connectbruh;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;
import com.thg.accelerator23.connectn.ai.connectbruh.analysis.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.connectbruh.analysis.GameState;

import java.util.*;

public class ConnectAI {
    Board board;
    Counter counter;
    Counter opponentCounter;

    BoardAnalyser boardAnalyser;
    GameState gameState;

    public ConnectAI(Board board, Counter counter) {
        this.board = board;
        this.counter = counter;
        this.opponentCounter = this.counter.getOther();
        this.boardAnalyser = new BoardAnalyser(this.board.getConfig());
        this.gameState = boardAnalyser.calculateGameState(this.board);
    }

    // Final checking that a valid move is being made
    public int makeMove() {
        int candidateMove = getCandidateMove();

        if (candidateMove == 3) {
            return (int)(Math.random() * 5);
        } else {
            return candidateMove;
        }
    }


    private int getCandidateMove() {
        if (earlyGame()) {
            return prioritiseCentre();
        } else if (getWinningMove() > 0) {
            return getWinningMove();
        } else if (getBlockingMove() > 0) {
            return getBlockingMove();
        } else if (makeThreeInARow() > 0) {
            return makeThreeInARow();
        } else {
            return getStrategicMove();
        }
    }

    private boolean earlyGame() {
        return !board.hasCounterAtPosition(new Position(5, 2));
    }

    private int prioritiseCentre() {
        return 5;
    }

    public int getWinningMove() {
        for (int col = 0; col < 10; col++) {
            try {
                Board simulatedBoard = new Board(board, col, counter);
                if (didPlayerWin(simulatedBoard, counter)) {
                    return col;
                }
            } catch (InvalidMoveException ignored) {}
        }

        return -1;
    }

    public int getBlockingMove() {
        for (int col = 0; col < 10; col++) {
            try {
                Board simulatedBoard = new Board(board, col, opponentCounter);
                if (didPlayerWin(simulatedBoard, opponentCounter)) {
                    return col;
                }
            } catch (InvalidMoveException e) {}
        }
        return -1;
    }

    private int makeThreeInARow() {
        for (int col = 0; col < board.getConfig().getWidth(); col++) {
            try {
                Board simulatedBoard = new Board(board, col, counter);
                GameState simulatedState = boardAnalyser.calculateGameState(simulatedBoard);

                Map<Counter, Integer> maxInARow = simulatedState.getMaxInARowByCounter();
                if (maxInARow.get(counter) != null && maxInARow.get(counter) == 3) {
                    return col;
                }
            } catch (InvalidMoveException ignored) {}
        }
        return -1;
    }

    public int getStrategicMove() {
        int move = findAdjacentMove();
        if (move >= 0) {
            return move;
        }
//        return basicRandomMove();
        return (int)(Math.random() * board.getConfig().getWidth());
    }

    public int findAdjacentMove() {
        int centerStart = 3;
        int centerEnd = 6;
        int move = findMoveInColumnsRange(centerStart, centerEnd);
        if (move >= 0) {
            return move;
        }

        // If no move found in center, check the remaining columns
        // Check columns to the left of the center
        move = findMoveInColumnsRange(0, centerStart - 1);
        if (move >= 0) {
            return move;
        }

        // Check columns to the right of the center
        return findMoveInColumnsRange(centerEnd + 1, board.getConfig().getWidth() - 1);
    }

    public int findMoveInColumnsRange(int startCol, int endCol) {
        for (int col = startCol; col <= endCol; col++) {
            int row = findFirstEmptyRowInColumn(col);
            if (row != -1 && isAdjacentToCounter(col, row)) {
                return col;
            }
        }
        return -1;
    }

    private boolean isAdjacentToCounter(int col, int row) {
        int width = board.getConfig().getWidth();
        int height = board.getConfig().getHeight();
        Counter myCounter = this.counter;

        int[] rowOffsets = {-1, 0, 1};
        int[] colOffsets = {-1, 0, 1};

        for (int rowOffset : rowOffsets) {
            for (int columnOffset : colOffsets) {
                if (rowOffset == 0 && columnOffset == 0) {
                    continue;
                }

                int checkRow = row + rowOffset;
                int checkCol = col + columnOffset;

                if (checkRow >= 0 && checkRow < height && checkCol >= 0 && checkCol < width) {
                    Counter adjacentCounter = board.getCounterAtPosition(new Position(checkCol, checkRow));
                    if (adjacentCounter == myCounter) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private int findFirstEmptyRowInColumn(int col) {
        int height = board.getConfig().getHeight();
        for (int row = height - 1; row >= 0; row--){
            if (row == 0 || board.getCounterAtPosition( new Position(col, (row - 1))) != null) {
                return row;
            }
        }
        return -1;
    }

    public int basicRandomMove() {
        int totalColumns = board.getConfig().getWidth();
        List<Integer> centerCols = Arrays.asList(3, 4, 5, 6);
        List<Integer> edgeCols = new ArrayList<>();

        for(int col = 0; col < totalColumns; col++) {
            if (col < 3 || col > 6) {
                edgeCols.add(col);
            }
        }

        Collections.shuffle(centerCols);
        for (int col : centerCols) {
            if (!isColumnFull(col)) {
                return col;
            }
        }

        Collections.shuffle(edgeCols);
        for (int col : edgeCols) {
            if (!isColumnFull(col)) {
                return col;
            }
        }

        return -1;
    }

    public boolean isColumnFull(int columnIndex) {
        int totalRows = board.getConfig().getHeight();
        for (int rowIndex = 0; rowIndex < totalRows; rowIndex++) {
            if (board.getCounterAtPosition(new Position(columnIndex, rowIndex)) == null) {
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
