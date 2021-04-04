package ubc.cosc322;

import java.util.*;

public class Agent {
	private int ourPlayer;
	private BoardModel model;
	private String agentName;
	private boolean heuristic;
	
	public Agent(int ourPlayer, BoardModel model, boolean heuristic) {
		
		this.ourPlayer = ourPlayer;
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
			monteCarlo mcl = new monteCarlo(model, ourPlayer);
			Move move = mcl.move();
			return move;
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
        ArrayList<Move> allMove;
        
        allMove = getAllPossiblemove(model, ourPlayer);
        
        if(allMove.size() == 0) {
        	System.out.println("Cannot make any move T_T");
        }
        int randomNum = random.nextInt(allMove.size());
        Move selectedMove = allMove.get(randomNum);
        return selectedMove;
	}
	
	
	// ----- Validation on legal move -------
	// return false if out of bound
	public static boolean isValidMove(int[] queenPosCurrent, int[] queenPosNew, int[] arrowPos, BoardModel model) {
		if(validationIsOutOfBounds(queenPosCurrent, queenPosNew, arrowPos) == false) 
			return false;
		if(validationPositionOccupied(queenPosNew, arrowPos, model) == false)
			return false;
		//Check Queen Movement if it is diagonal or orthagonal
		if((validationIsDiagonal(queenPosCurrent, queenPosNew) || validateIsOrthagonal(queenPosCurrent, queenPosNew)) == false) 
			return false;
		// Check if the path between current queen and new queen is clear
		if(validationClearPath(queenPosCurrent, queenPosNew, model) == false)
			return false;
		
		//Check arrow Movement if it is diagonal or orthagonal
		if((validationIsDiagonal(queenPosNew, arrowPos) || validateIsOrthagonal(queenPosNew, arrowPos)) == false) 
			return false;
		// Check if the path between new queen and arrow is clear
		if(validationClearPath(queenPosNew, arrowPos, model) == false)
			return false;
		return true;
	}
	public static boolean isValidQueenOrArrowMove(int[] posCurrent, int[] posNew, BoardModel model) {
		if(isOutOfBounds(posCurrent) && isOutOfBounds(posNew) == false) {
			return false;
		}
		if(validationPositionIsOccupied(posNew, model) == false) {
			return false;
		}
		if((validationIsDiagonal(posCurrent, posNew) || validateIsOrthagonal(posCurrent, posNew)) == false ) {
			return false;
		}
		if(validationClearPath(posCurrent, posNew, model) == false) {
			return false;
		}
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
			if(!(model.getTile(new int[] {testx, testy}) == model.POS_AVAILABLE)) {
				return false;
			}
			testx += xdir;
			testy += ydir;
		}
		return true;
	}
	// false if it is occupy
    private static boolean validationPositionOccupied(int[] queenPosNew, int[] arrowPos, BoardModel model) {

        int queenSpace = model.getTile(queenPosNew);
        int arrowSpace = model.getTile(arrowPos);

        if (!(queenSpace == model.POS_AVAILABLE) || !(arrowSpace == model.POS_AVAILABLE)) {
            return false;
        }

        return true;
    }
    private static boolean validationPositionIsOccupied(int[] position, BoardModel model) {

        int positionSpace = model.getTile(position);

        if (!(positionSpace == model.POS_AVAILABLE)) {
            return false;
        }

        return true;
    }
	public static ArrayList<Move> getAllPossiblemove(BoardModel model, int ourPlayer){
		ArrayList<int[]> allQueens = model.getAllQueen();
		//model.printQueens();
		ArrayList<int[]> selectedQueens = new ArrayList<int[]>();
		ArrayList<Move> allMove = new ArrayList<Move>();
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				int[] tempPos = new int[] {i, j};
				if(model.getTile(tempPos) == ourPlayer){
					selectedQueens.add(tempPos);
				}
			}
		}
		for (int q = 0; q < selectedQueens.size(); q++) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					int [] tempNewPos = new int[] {i, j};
					if(!Arrays.equals(selectedQueens.get(q),tempNewPos)) {
						if((Agent.isValidQueenOrArrowMove(selectedQueens.get(q), tempNewPos, model) == true)) {
							for (int arrowi = 0; arrowi < 10; arrowi++) {
								for (int arrowj = 0; arrowj < 10; arrowj++) {
									int [] tempArrow = new int[] {arrowi, arrowj};
									if(!Arrays.equals(tempArrow, tempNewPos)) {
										if((Agent.isValidQueenOrArrowMove(tempNewPos, tempArrow, model) == true)) {
											Move tempmove = new Move(selectedQueens.get(q), tempNewPos, tempArrow);
											allMove.add(tempmove);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return allMove;
	}
}
