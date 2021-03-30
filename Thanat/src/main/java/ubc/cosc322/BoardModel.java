package ubc.cosc322;

import java.util.*;

public class BoardModel {
	// TA suggest it should be number better than string
    //public static final String POS_MARKED_BLACK = "queen-black";
    //public static final String POS_MARKED_WHITE = "queen-white";
    //public static final String POS_MARKED_ARROW = "arrow";
    //public static final String POS_AVAILABLE = "available";
    
    public static final int POS_MARKED_BLACK = 1;
    public static final int POS_MARKED_WHITE = 2;
    public static final int POS_MARKED_ARROW = 4;
    public static final int POS_AVAILABLE = 0;
    
    public int ourPlayer = -1;
    
    private int[][] gameBoard = null;
    
    public void setTile(int[] position, int occupant) {
        gameBoard[position[0]][position[1]] = occupant;
    }
    
    public int getTile(int[] position) {
        return gameBoard[position[0]][position[1]];
    }
    
    public BoardModel() {
        gameBoard = new int[10][10];
        // Create Array of Board
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gameBoard[i][j] = POS_AVAILABLE;
            }
        }
        setTile(new int[]{0, 3}, POS_MARKED_WHITE);
        setTile(new int[]{0, 6}, POS_MARKED_WHITE);
        setTile(new int[]{3, 0}, POS_MARKED_WHITE);
        setTile(new int[]{3, 9}, POS_MARKED_WHITE);
        
        setTile(new int[]{6, 0}, POS_MARKED_BLACK);
        setTile(new int[]{6, 9}, POS_MARKED_BLACK);
        setTile(new int[]{9, 3}, POS_MARKED_BLACK);
        setTile(new int[]{9, 6}, POS_MARKED_BLACK);
    }
    
    public void makeMove(int[] oldQueenPosition, int[] newQueenPosition, int[] arrowPosition) {
        int color = getTile(oldQueenPosition);
        setTile(oldQueenPosition, POS_AVAILABLE);
        setTile(newQueenPosition, color);
        setTile(arrowPosition, POS_MARKED_ARROW);
    }
    public void makeMove(Move move) {
    	makeMove(move.getQueenPosCurrent(), move.getQueenPosNew(), move.getArrowPos());
    }
    
    
    public void printBoard() {
    	for (int[] row : gameBoard) {
    		System.out.println(Arrays.toString(row));
    	}
    }
    public void printQueens() {
    	System.out.println("All Queens Position: ");
    	ArrayList<int[]> allQueen = getAllQueen();
    	for(int i = 0; i < allQueen.size(); i++) {
    		System.out.print(getTile(allQueen.get(i)) + ": " + Arrays.toString(allQueen.get(i)) + " ");
    	}
    	System.out.println();
    }
    public ArrayList<int[]> getAllQueen(){
    	ArrayList<int[]> tempArray = new ArrayList<int[]>();
    	for(int i = 0; i < 10; i++) {
    		for(int j = 0; j < 10; j++) {
    			int[] temp = new int[] {i, j};
    			if(getTile(temp) == POS_MARKED_BLACK || getTile(temp) == POS_MARKED_WHITE) {
    				tempArray.add(temp);
    			}
    		}
    	}
    	return tempArray;
    }
}
