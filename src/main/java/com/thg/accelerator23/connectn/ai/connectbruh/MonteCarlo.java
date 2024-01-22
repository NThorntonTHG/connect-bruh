package com.thg.accelerator23.connectn.ai.connectbruh;
import com.thehutgroup.accelerator.connectn.player.Board;

public class MonteCarlo {

    private final Board board;
    private final long timeLimit;

    public static final double EXPLORATION = Math.sqrt(2);

    public MonteCarlo(Board board, long timeLimit) {
        this.board = board;
        this.timeLimit = timeLimit;
    }

    public void update(int move) {

    }

    public int getOptimalMove() {

    }

    private Node select() {

    }


}
