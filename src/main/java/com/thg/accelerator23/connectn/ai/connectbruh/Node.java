package com.thg.accelerator23.connectn.ai.connectbruh;

import com.thehutgroup.accelerator.connectn.player.Board;

import java.util.HashMap;
import java.util.Map;

public class Node {

    private int move;
    private Node parent;
    private double N;
    private double Q;
    private Map<Board, Node> children;
    protected Board board;

    public Node(Board board, Node parent) {
        this.board = board;
        this.parent = parent;
        this.N = 0;
        this.Q = 0;
        this.children = new HashMap<>();
    }

//    public void addChildren() {
//        for (Node childNode : children) {
//            this.children = childNode;
//        }
//    }
}
