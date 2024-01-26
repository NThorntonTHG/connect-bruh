package com.thg.accelerator23.connectn.ai.connectbruh;

import com.thehutgroup.accelerator.connectn.player.Board;


public class Node {
    Node parent;
    protected Node[] children;
    int visits;
    private double winCount;
    protected final Board board;

    public Node(Node parent, Board board) {
        this.parent = parent;
        this.board = board;
        this.visits = 0;
        this.winCount = 0;
        children = new Node[10];
    }

    public int incrementVisits() {
        return ++visits;
    }

    public double incrementWinCount(double result) {
        return winCount + result;
    }

    public double getWinCount() {
        return winCount;
    }
}
