package com.thg.accelerator23.connectn.ai.connectbruh;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.ArrayList;
import java.util.List;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;

public class FinalBoard {

    private final int[][] board;
    public int height;
    public int width;
    private boolean nextTurn;

    public static final boolean PLAYER_1_TURN = true;
    public static final boolean PLAYER_2_TURN = false;

    public static final int EMPTY_SLOT = 0;
    public static final int PLAYER_1_DISK = 1;
    public static final int PLAYER_2_DISK = 2;

    public static final int ONGOING = 0;
    public static final int PLAYER_1_WON = 1;
    public static final int PLAYER_2_WON = 2;
    public static final int TIE = 3;


    public FinalBoard(Board oldBoard) {
        this.board = convertCounterArrayToIntArray(oldBoard);
        this.nextTurn = PLAYER_1_TURN;
    }

    public boolean getNextTurn() {
        return nextTurn;
    }

    public FinalBoard(int[][] contents, boolean nextTurn) {
        this(contents[0].length, contents.length);
        loadContents(contents);
        this.nextTurn = nextTurn;
    }

    public FinalBoard(int width, int height) {
        this.width = width;
        this.height = height;
        board = new int[width][height]; // default all 0
        nextTurn = PLAYER_1_TURN;
    }

    public FinalBoard() {
        this(10, 8);
    }

    private static List<Position> getListOfAllPositions() {
        List<Position> positions = new ArrayList<>();


        for (int width = 0; width < 10; width++) {
            for (int height = 0; height < 8; height++) {
                positions.add(new Position(width, height));

            }
        }

        return positions;
    }

    public boolean isFull() {
        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[i].length; j++)
                if(board[i][j] == EMPTY_SLOT)
                    return false;
        return true;
    }

    public int currentGameState() {
        return this.didPlayerWin(PLAYER_1_DISK) ? PLAYER_1_WON
                : this.didPlayerWin(PLAYER_2_DISK) ? PLAYER_2_WON
                : this.isFull() ? TIE
                : ONGOING;
    }

    public boolean didPlayerWin(int playerDisk) {
        // check horizontal
        int height = board.length;
        int width = board[0].length;
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width - 3; j++)
                for(int k = j; k < j + 4 && board[i][k] == playerDisk; k++)
                    if(k == j+3)
                        return true;
        // check vertical
        for(int i = 0; i < height - 3; i++)
            for(int j = 0; j < width; j++)
                for(int k = i; k < i + 4 && board[k][j] == playerDisk; k++)
                    if(k == i+3)
                        return true;
        // check diagonal down right
        for(int i = 0; i < height - 3; i++)
            for(int j = 0; j < width - 3; j++)
                for(int k = 0; k < 4 && board[i+k][j+k] == playerDisk; k++)
                    if(k == 3)
                        return true;
        // check diagonal down
        for(int i = 0; i < height - 3; i++)
            for(int j = 3; j < width; j++)
                for(int k = 0; k < 4 && board[i+k][j-k] == playerDisk; k++)
                    if(k == 3)
                        return true;
        return false;
    }

    public static int[][] convertCounterArrayToIntArray(Board board) {
        int[][] newBoard = new int[10][8];
        for (Position p : getListOfAllPositions()) {
            if (board.getCounterAtPosition(p) == X) {
                newBoard[p.getX()][p.getY()] = 1;
            } else if (board.getCounterAtPosition(p) == O) {
                newBoard[p.getX()][p.getY()] = 2;
            } else {
                newBoard[p.getX()][p.getY()] = 0;
            }
        }

        return newBoard;
    }

    public void loadContents(int[][] contents) {
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++)
                board[i][j] = contents[i][j];
    }

    public boolean place(int column) {
        int disk = (nextTurn == PLAYER_1_TURN) ? PLAYER_1_DISK : PLAYER_2_DISK;
        if(!canPlace(column))
            return false;
        int diskHeight = height - 1;
        while(board[diskHeight][column] != EMPTY_SLOT)
            diskHeight--;
        board[diskHeight][column] = disk;
        nextTurn = !nextTurn;
        return true;
    }

    public FinalBoard getNextState(int column) {
        FinalBoard next = this.copy();
        next.place(column);
        return next;
    }

    public FinalBoard copy() {
        return new FinalBoard(board, this.nextTurn);
    }

    public boolean canPlace(int column) {
        return column >= 0 && column < width && board[0][column] == 0;
    }
    public int[][] getBoard() {
        return board;
    }
}
