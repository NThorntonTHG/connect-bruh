package com.thg.accelerator23.connectn.ai.connectbruh;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thg.accelerator23.connectn.ai.connectbruh.analysis.BoardAnalyser;

public class MCTS {

    private Board board;
    private BoardAnalyser boardAnalyser;
    private Node root;
    private Counter counter;

    public static final double EXPLORATION = Math.sqrt(2);

    public MCTS(Board board, BoardAnalyser boardAnalyser, Counter counter) {
        this.board = board;
        this.boardAnalyser = boardAnalyser;
        this.counter = counter;
        this.root = setRoot();
    }

    public int makeMove() {
        int move;
        return move;
    }

    // Set root node with UCT
    private Node setRoot() {

    }
    }
}
