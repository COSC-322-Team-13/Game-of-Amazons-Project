package ubc.cosc322;

import java.util.*;

import ygraph.ai.smartfox.games.GameClient;

public class Move {
	
	private ArrayList<Integer> queenPosCurrent;
	private ArrayList<Integer> queenPosNew;
	private ArrayList<Integer> arrowPos;
	
	
	public ArrayList<Integer> getQueenPosCurrent() {
		return queenPosCurrent;
	}

	public ArrayList<Integer> getQueenPosNew() {
		return queenPosNew;
	}

	public ArrayList<Integer> getArrowPos() {
		return arrowPos;
	}

	public void setQueenPosCurrent(ArrayList<Integer> queenPosCurrent) {
		this.queenPosCurrent = queenPosCurrent;
	}

	public void setQueenPosNew(ArrayList<Integer> queenPosNew) {
		this.queenPosNew = queenPosNew;
	}

	public void setArrowPos(ArrayList<Integer> arrowPos) {
		this.arrowPos = arrowPos;
	}

	public Move(ArrayList<Integer> queenPosCurrent, ArrayList<Integer> queenPosNew, ArrayList<Integer> arrowPos) {
		this.queenPosCurrent = queenPosCurrent;
		this.queenPosNew = queenPosNew;
		this.arrowPos = arrowPos;
	
		//GameClient.sendMoveMessage(this.queenPosCurrent, this.queenPosNew, this.arrowPos);
	}
	
	
	
}
