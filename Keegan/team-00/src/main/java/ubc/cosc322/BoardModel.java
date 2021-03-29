package ubc.cosc322;

import java.util.*;

public class BoardModel {
	// TA suggest it should be number better than string
    //public static final String POS_MARKED_BLACK = "queen-black";
    //public static final String POS_MARKED_WHITE = "queen-white";
    //public static final String POS_MARKED_ARROW = "arrow";
    //public static final String POS_AVAILABLE = "available";
    
    public static final String POS_MARKED_BLACK = "1";
    public static final String POS_MARKED_WHITE = "2";
    public static final String POS_MARKED_ARROW = "4";
    public static final String POS_AVAILABLE = "0";
    
    public ArrayList<int[]> queenPositions = new ArrayList<int[]>(8);
    private String[][] gameBoard = null;
    
    public void setTile(int[] position, String occupant) {
        gameBoard[position[0]][position[1]] = occupant;
    }
    
    public String getTile(int[] position) {
        return gameBoard[position[0]][position[1]];
    }
    
    private void setQueen(int[] position, boolean isWhite) {
    	if(isWhite == true) {
    		setTile(position, POS_MARKED_WHITE);
    	}else {
    		setTile(position, POS_MARKED_BLACK);
        }
        queenPositions.add(position);
    }
    
    public BoardModel() {
        gameBoard = new String[10][10];
        // Create Array of Board
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gameBoard[i][j] = POS_AVAILABLE;
            }
        }
        setQueen(new int[]{0, 3}, true);
        setQueen(new int[]{0, 6}, true);
        setQueen(new int[]{3, 0}, true);
        setQueen(new int[]{3, 9}, true);
        
        setQueen(new int[]{6, 0}, false);
        setQueen(new int[]{6, 9}, false);
        setQueen(new int[]{9, 3}, false);
        setQueen(new int[]{9, 6}, false);
    }
    
    public void makeMove(int[] oldQueenPosition, int[] newQueenPosition, int[] arrowPosition) {
        moveQueen(oldQueenPosition, newQueenPosition);
        setTile(arrowPosition, POS_MARKED_ARROW);
    }
    public void makeMove(Move move) {
    	makeMove(move.getQueenPosCurrent(), move.getQueenPosNew(), move.getArrowPos());
    }
    
    //Move Queen
    public void moveQueen(int[] position1, int[] position2) {
        setQueen(position2, getTile(position1).equalsIgnoreCase(POS_MARKED_WHITE));
        setTile(position1, POS_AVAILABLE);
        // Remove from the original position
        for (int i = 0; i < queenPositions.size(); i++) {
            if (queenPositions.get(i)[0] == position1[0] && queenPositions.get(i)[1] == position1[1]) {
                queenPositions.remove(i);
                break;
            }
        }
    }
    
    public void printBoard() {
    	for (String[] row : gameBoard) {
    		System.out.println(Arrays.toString(row));
    	}
    }
}
