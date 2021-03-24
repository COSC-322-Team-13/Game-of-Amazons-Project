package ubc.cosc322;

import java.util.*;

import ygraph.ai.smartfox.games.GameClient;

public class Move {
	
	private int[] queenPosCurrent;
	private int[] queenPosNew;
	private int[] arrowPos;
	
	
	public int[] getQueenPosCurrent() {
		return queenPosCurrent;
	}

	public int[] getQueenPosNew() {
		return queenPosNew;
	}

	public int[] getArrowPos() {
		return arrowPos;
	}

	public void setQueenPosCurrent(int[] queenPosCurrent) {
		this.queenPosCurrent = queenPosCurrent;
	}

	public void setQueenPosNew(int[] queenPosNew) {
		this.queenPosNew = queenPosNew;
	}

	public void setArrowPos(int[] arrowPos) {
		this.arrowPos = arrowPos;
	}

	public Move(int[] queenPosCurrent, int[] queenPosNew, int[] arrowPos) {
		this.queenPosCurrent = queenPosCurrent;
		this.queenPosNew = queenPosNew;
		this.arrowPos = arrowPos;
	
		//GameClient.sendMoveMessage(this.queenPosCurrent, this.queenPosNew, this.arrowPos);
	}
	
	
	
}
