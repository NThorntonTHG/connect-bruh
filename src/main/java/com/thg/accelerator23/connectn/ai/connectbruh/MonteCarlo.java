package com.thg.accelerator23.connectn.ai.connectbruh;
import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.ArrayList;
import java.util.List;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;

public class MonteCarlo {

    private final FinalBoard board;
    private Node root;
    private final long timeLimit;

    public static final double EXPLORATION = Math.sqrt(2);

    public MonteCarlo(FinalBoard board, long timeLimit) {
        this.board = board;
        this.timeLimit = timeLimit;
    }


    // sets root to new board state
    public void update(int move) {
        root = root.children[move] != null
                ? root.children[move]
                : new Node(null, root.board.getNextState(move));
    }

    // returns the optimal move for the current player
    public int getOptimalMove() {
        for (long stop = System.nanoTime()+timeLimit; stop>System.nanoTime();) {
            Node selectedNode = select();
            if(selectedNode == null)
                continue;
            Node expandedNode = expand(selectedNode);
            double result = simulate(expandedNode);
            backpropagate(expandedNode, result);
        }

        int maxIndex = -1;
        for(int i = 0; i < 10; i++) {
            if(root.children[i] != null) {
                if(maxIndex == -1 || root.children[i].visits > root.children[maxIndex].visits)
                    maxIndex = i;
            }
        }
        return maxIndex;
    }

    private Node select() {
        return select(root);
    }

    private Node select(Node parent) {
        // if parent has at least child without statistics, select parent
        for(int i = 0; i < 10; i++) {
            if(parent.children[i] == null && parent.board.canPlace(i)) {
                return parent;
            }
        }
        // if all children have statistics, use UCT to select next node to visit
        double maxSelectionVal = -1;
        int maxIndex = -1;
        for(int i = 0; i < 10; i++) {
            if(!parent.board.canPlace(i))
                continue;
            Node currentChild = parent.children[i];
            double wins = parent.board.getNextTurn() == FinalBoard.PLAYER_1_TURN
                    ? currentChild.getWinCount()
                    : (currentChild.visits-currentChild.getWinCount());
            double selectionVal = wins/currentChild.visits
                    + EXPLORATION*Math.sqrt(Math.log(parent.visits)/currentChild.visits);// UCT
            if(selectionVal > maxSelectionVal) {
                maxSelectionVal = selectionVal;
                maxIndex = i;
            }
        }
        if(maxIndex == -1)
            return null;
        return select(parent.children[maxIndex]);
    }

    private Node expand(Node selectedNode) {
        // get unvisited child nodes
        ArrayList<Integer> unvisitedChildrenIndices = new ArrayList<Integer>(10);
        for(int i = 0; i < 10; i++) {
            if(selectedNode.children[i] == null && selectedNode.board.canPlace(i)) {
                unvisitedChildrenIndices.add(i);
            }
        }

        // create node for random, unvisited child
        int selectedIndex = unvisitedChildrenIndices.get((int)(Math.random()*unvisitedChildrenIndices.size()));
        selectedNode.children[selectedIndex] = new Node(selectedNode, selectedNode.board.getNextState(selectedIndex));
        return selectedNode.children[selectedIndex];
    }

    // returns result of simulation
    private double simulate(Node expandedNode) {
        FinalBoard simulationBoard = expandedNode.board.copy();
        while(simulationBoard.currentGameState() == FinalBoard.ONGOING) {
            simulationBoard.place((int)(Math.random()*10));
        }

        switch(simulationBoard.currentGameState()) {
            case FinalBoard.PLAYER_1_WON:
                return 1;
            case FinalBoard.PLAYER_2_WON:
                return 0;
            default:
                return 0.5;
        }
    }

    private void backpropagate(Node expandedNode, double simulationResult) {
        Node currentNode = expandedNode;
        while(currentNode != null) {
            currentNode.incrementVisits();
            currentNode.incrementWinCount(simulationResult);
            currentNode = currentNode.parent;
        }
    }

}
