package ubc.cosc322;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

import java.util.*;

// this class represents the current state of the game board

public class CurrentBoard {

	private ArrayList <ArrayList<Integer>> allQueenPos;
	
	public CurrentBoard() {
		
		allQueenPos = initQueens();
		
	}
	
	// initial places of all the queens
	public ArrayList<ArrayList<Integer>> initQueens() {
		
		// arraylist that holds a queen in each element! each element contains another arraylist
		// with Y and then X as well as 0/1 for black or white queen
		// only pass the (y,x) but keep 0/1 for us to determine if queen is black or white
		// and thus we may move them
		
		ArrayList <ArrayList<Integer>> initialPositions = new ArrayList <ArrayList<Integer>>();
		
		ArrayList <Integer> q1 = new ArrayList <Integer>();
		ArrayList <Integer> q2 = new ArrayList <Integer>();
		ArrayList <Integer> q3 = new ArrayList <Integer>();
		ArrayList <Integer> q4 = new ArrayList <Integer>();
		ArrayList <Integer> q5 = new ArrayList <Integer>();
		ArrayList <Integer> q6 = new ArrayList <Integer>();
		ArrayList <Integer> q7 = new ArrayList <Integer>();
		ArrayList <Integer> q8 = new ArrayList <Integer>();
		
		q1.add(6);
		q1.add(0);
		
		q2.add(9);
		q2.add(3);
		
		q3.add(9);
		q3.add(6);
		
		q4.add(6);
		q4.add(9);

		q5.add(3);
		q5.add(0);
		
		q6.add(0);
		q6.add(3);
		
		q7.add(0);
		q7.add(6);
		
		q8.add(3);
		q8.add(9);
		
		initialPositions.add(q1);
		initialPositions.add(q2);
		initialPositions.add(q3);
		initialPositions.add(q4);
		initialPositions.add(q5);
		initialPositions.add(q6);
		initialPositions.add(q7);
		initialPositions.add(q8);
		
		return initialPositions;
		
	}
	
	public ArrayList <Integer> getSpecificQueenPos(int index) {
		
		return allQueenPos.get(index);
		
	}
	
	public void setSpecificQueenPos(int y, int x, int index) {
		ArrayList<Integer> newPos = this.allQueenPos.get(index);
		newPos.set(0, y);
		newPos.set(1, x);
		this.allQueenPos.set(index, newPos);
	}
	
}
