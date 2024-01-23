package com.thg.accelerator23.connectn.ai.connectbruh;

import com.thehutgroup.accelerator.connectn.player.Board;


public class Node {
    private Node parent;
    private Node[] children;
    private int visits;
    private double winCount;
    private final FinalBoard finalBoard;

    public Node(Node parent, FinalBoard finalBoard) {
        this.parent = parent;
        this.finalBoard = finalBoard;
    }

    public int incrementVisits() {

    }

    public double incrementWinCount(double result) {

    }
}
