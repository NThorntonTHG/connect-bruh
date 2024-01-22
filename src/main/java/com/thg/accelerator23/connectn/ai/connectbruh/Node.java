package com.thg.accelerator23.connectn.ai.connectbruh;

import com.thehutgroup.accelerator.connectn.player.Board;


public class Node {
    private Node parent;
    private Node[] children;
    private int visits;
    private double winCount;
    private final Board board;

    public Node(Node parent, Board board) {
        this.parent = parent;
        this.board = board;
    }

    public int incrementVisits() {

    }

    public double incrementWinCount(double result) {

    }
}
