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
	        	// while queen color is not the same with our color
	        }while(!queenColor.equalsIgnoreCase(isWhite ? BoardModel.POS_MARKED_WHITE : BoardModel.POS_MARKED_BLACK));
	        
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
		if(position[0] < 0 || position[0] >= 10 || position[1] < 0 || position[1] >= 10) {
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
	// implement with TA suggestion
	private static boolean validationClearPath(int[] curposition, int[] newposition, BoardModel model) {
		int xdir = (int)Math.signum(newposition[0]-curposition[0]);
		int ydir = (int)Math.signum(newposition[1]-curposition[1]);
		int testx = curposition[0] + xdir;
		int testy = curposition[1] + ydir;
		while((testx != newposition[0]) || (testy != newposition[1])) {
			if(!model.getTile(new int[] {testx, testy}).equals(model.POS_AVAILABLE)) {
				return false;
			}
			testx += xdir;
			testy += ydir;
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
