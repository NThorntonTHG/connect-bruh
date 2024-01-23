package com.thg.accelerator23.connectn.ai.connectbruh;

import com.thehutgroup.accelerator.connectn.player.Board;


public class Node {
    private Node parent;
    private Node[] children;
    private int visits;
    private double winCount;
    private final FinalBoard board;

    public Node(Node parent, FinalBoard board) {
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
}
