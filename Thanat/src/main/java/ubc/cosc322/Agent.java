package ubc.cosc322;

import java.util.*;

public class Agent {
	private boolean isWhite;
	private BoardModel model;
	private String agentName;
	private boolean heuristic;
	
	public Agent(boolean isWhite, BoardModel model, boolean heuristic) {
		
		this.isWhite = isWhite;
		this.model = model;
		this.agentName = "team-13-Agent";
		this.heuristic = heuristic;
	}
	
	public void makeMove(Move move) {
        if(move.getArrowPos()[0]<0){
            System.out.println("I lost!");
            System.exit(0);
        }
        model.makeMove(move.getQueenPosCurrent(), move.getQueenPosNew(), move.getArrowPos());
    }
	
	public Move pickMove() {
		if(heuristic == true) {
			return randomMove(); // will implement heuristic search
		}else {
			return randomMove();
		}
	}
	
	// Make Random Move
	// This is for testing need to be configure
	// Code taken from github
	private Move randomMove() {
		Random random = new Random();
        Move move;
        int[] queenPosCurrent;
        int[] queenPosNew;
        int[] arrowPos;
        
        int failNum = 0;
        do {
	        String queenColor;
	        do {
	        	queenPosCurrent = model.queenPositions.get(random.nextInt(8));
	        	queenColor = model.getTile(queenPosCurrent);
	        }while(queenColor.equalsIgnoreCase(isWhite ? BoardModel.POS_MARKED_BLACK : BoardModel.POS_MARKED_WHITE));
	        
	        queenPosNew = new int[] {random.nextInt(10), random.nextInt(10)};
	        arrowPos = new int[] {random.nextInt(10), random.nextInt(10)};
	        move = new Move(queenPosCurrent, queenPosNew, arrowPos);
        }while(isValidMove(queenPosCurrent, queenPosNew, arrowPos, model));
        return move;
	}
	
	
	// ----- Validation on legal move -------
	// return false if out of bound
	private static boolean isValidMove(int[] queenPosCurrent, int[] queenPosNew, int[] arrowPos, BoardModel model) {
		if(validationIsOutOfBounds(queenPosCurrent, queenPosNew, arrowPos) == false) 
			return false;
		if(validationPositionOccupied(queenPosNew, arrowPos, model) == false)
			return false;
		//Check Queen Movement if it is diagonal or orthagonal
		if(validationIsDiagonal(queenPosCurrent, queenPosNew) || validateIsOrthagonal(queenPosCurrent, queenPosNew) == false) 
			return false;
		// Check if the path between current queen and new queen is clear
		if(validationClearPath(queenPosCurrent, queenPosNew, model) == false)
			return false;
		
		//Check arrow Movement if it is diagonal or orthagonal
		if(validationIsDiagonal(queenPosNew, arrowPos) || validateIsOrthagonal(queenPosNew, arrowPos) == false) 
			return false;
		// Check if the path between new queen and arrow is clear
		if(validationClearPath(queenPosNew, arrowPos, model) == false)
			return false;
		return true;
	}
	
	private static boolean isOutOfBounds(int[] position) {
		if(position[0] < 0 || position[0] > 10 || position[1] < 0 || position[1] > 10) {
			return false;
		}else {
			return true;
		}
    }
	private static boolean validationIsOutOfBounds(int[] curposition, int[] newposition, int[] arrowposition) {
		boolean isValid = isOutOfBounds(curposition) && isOutOfBounds(newposition) && isOutOfBounds(arrowposition);
		if(isValid == true) {
			return true;
		}else {
			return false;
		}
	}
	
	private static boolean validationIsDiagonal(int[] position1, int[] position2) {
		boolean isValid = Math.abs(position1[0] - position2[0]) - Math.abs(position1[1] - position2[1]) == 0;
        return isValid;
    }
	
	private static boolean validateIsOrthagonal(int[] position1, int[] position2) {
		boolean isValid = (position1[0] == position2[0] && position1[1] != position2[1]) || (position1[0] != position2[0] && position1[1] == position2[1]);
        return isValid;
    }
	
	private static boolean validationClearPath(int[] curposition, int[] newposition, BoardModel model) {
		if(validateIsOrthagonal(curposition, newposition)) {
			if(curposition[0] == newposition[0] && curposition[1] != newposition[1]) {
				// vertically
				int start = 0;
				int length = Math.abs(newposition[1] - curposition[1]);
				
				if(newposition[1] < curposition[1]) {
					start = newposition[1];
				}else {
					start = curposition[1];
				}
				
				for(int i = 1; i < length; i++) {
					int [] temp = new int[]{curposition[0], start+i};
					if (!model.getTile(temp).equalsIgnoreCase(model.POS_AVAILABLE)) {
		                return false;
		            }
				}
			}
			if(curposition[0] != newposition[0] && curposition[1] == newposition[1]){
				// horizontal
				int start = 0;
				int length = Math.abs(newposition[0] - curposition[0]);
				
				if(newposition[0] < curposition[0]) {
					start = newposition[0];
				}else {
					start = curposition[0];
				}
				
				for(int i = 1; i < length; i++) {
					int [] temp = new int[]{start+i, curposition[1]};
					if (!model.getTile(temp).equalsIgnoreCase(model.POS_AVAILABLE)) {
		                return false;
		            }
				}
			}
		}
		if(validationIsDiagonal(curposition, newposition)) {
			int startvertically = 0;
			int starthorizontally = 0;
			
			int length = Math.abs(newposition[0] - curposition[0]);
			if((curposition[0] < newposition[0] && curposition[1] < newposition[1]) || (curposition[0] > newposition[0] && curposition[1] > newposition[1])) {
				if(newposition[0] < curposition[0]) {
					starthorizontally = newposition[0];
				}else if(curposition[0] < newposition[0]) {
					starthorizontally = curposition[0];
				}
				
				if(newposition[1] < curposition[1]) {
					startvertically = newposition[1];
				}else if(curposition[1] < newposition[1]) {
					startvertically = curposition[1];
				}
				
				for(int i = 1; i < length; i++) {
					int [] temp = new int[]{starthorizontally+i, startvertically+i};
					if (!model.getTile(temp).equalsIgnoreCase(model.POS_AVAILABLE)) {
		                return false;
		            }
				}
			}
			// diagonal to northeast (to the right and up) or opposite
			if((curposition[0] < newposition[0] && curposition[1] < newposition[1]) || (curposition[0] > newposition[0] && curposition[1] < newposition[1])) {
				if(newposition[0] < curposition[0]) {
					starthorizontally = newposition[0];
				}else if(curposition[0] < newposition[0]) {
					starthorizontally = curposition[0];
				}
				
				if(newposition[1] < curposition[1]) {
					startvertically = curposition[1];
				}else if(curposition[1] < newposition[1]) {
					startvertically = newposition[1];
				}
				for(int i = 1; i < length; i++) {
					int [] temp = new int[]{starthorizontally+i, startvertically-i};
					if (!model.getTile(temp).equalsIgnoreCase(model.POS_AVAILABLE)) {
		                return false;
		            }
				}
			}
		}
		return true;
	}
	// false if it is occupy
    private static boolean validationPositionOccupied(int[] queenPosNew, int[] arrowPos, BoardModel model) {

        String queenSpace = model.getTile(queenPosNew);
        String arrowSpace = model.getTile(arrowPos);

        if (!queenSpace.equals(model.POS_AVAILABLE) || !arrowSpace.equals(model.POS_AVAILABLE)) {
            return false;
        }

        return true;
    }
}
