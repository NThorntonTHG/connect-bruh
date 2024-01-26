package com.thg.accelerator23.connectn.ai.connectbruh;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;



public class ConnectMoreBrainCells extends Player {
  public ConnectMoreBrainCells(Counter counter) {
    //TODO: fill in your name here
    super(counter, ConnectMoreBrainCells.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    Counter counter = super.getCounter();
    ConnectAI ai = new ConnectAI(board, counter);
    return ai.makeMove();
  }
}
