package com.thg.accelerator23.connectn.ai.connectbruh;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.ArrayList;
import java.util.List;

import static com.thehutgroup.accelerator.connectn.player.Counter.O;
import static com.thehutgroup.accelerator.connectn.player.Counter.X;

public class FinalBoard {
    private static List<Position> getListOfAllPositions() {
        List<Position> positions = new ArrayList<>();


        for (int width = 0; width < 10; width++) {
            for (int height = 0; height < 8; height++) {
                positions.add(new Position(width, height));

            }
        }

        return positions;
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
}
