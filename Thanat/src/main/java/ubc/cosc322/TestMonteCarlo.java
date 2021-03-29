package ubc.cosc322;

import java.util.*;

public class TestMonteCarlo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BoardModel model = new BoardModel();
		BoardModel model2 = new BoardModel();
		model.printBoard();
		//System.out.println(model.queenPositions.toString());
		//getAllPossiblemove(model, false).size()
		System.out.println(getAllPossiblemove(model, false).size());
		getAllPossiblemove(model, false).get(1588).printMove();
		model2.makeMove(getAllPossiblemove(model, false).get(555));
		model2.printBoard();
	}
	public static ArrayList<Move> getAllPossiblemove(BoardModel model, boolean whiteTurn){
		ArrayList<int[]> allQueens = model.queenPositions;
		ArrayList<int[]> selectedQueens = new ArrayList<int[]>();
		ArrayList<Move> allMove = new ArrayList<Move>();
		if(whiteTurn == true) {
			for(int i = 0; i < 8; i++) {
				if(model.getTile(allQueens.get(i)).equalsIgnoreCase(BoardModel.POS_MARKED_WHITE)){
					selectedQueens.add(allQueens.get(i));
				}
			}
		}else {
			for(int i = 0; i < 8; i++) {
				if(model.getTile(allQueens.get(i)).equalsIgnoreCase(BoardModel.POS_MARKED_BLACK)){
					selectedQueens.add(allQueens.get(i));
				}
			}
		}
		for (int q = 0; q < 4; q++) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					int [] tempNewPos = new int[] {i, j};
					if(Agent.isValidQueenOrArrowMove(selectedQueens.get(q), tempNewPos, model) == true) {
						for (int arrowi = 0; arrowi < 10; arrowi++) {
							for (int arrowj = 0; arrowj < 10; arrowj++) {
								int [] tempArrow = new int[] {arrowi, arrowj};
								if(Agent.isValidQueenOrArrowMove(tempNewPos, tempArrow, model) == true) {
									Move tempmove = new Move(selectedQueens.get(q), tempNewPos, tempArrow);
									allMove.add(tempmove);
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
