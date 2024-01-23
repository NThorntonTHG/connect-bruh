package com.thg.accelerator23.connectn.ai.connectbruh;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.ArrayList;
import java.util.List;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;

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
