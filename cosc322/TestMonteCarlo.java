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
		//System.out.println(Agent.getAllPossiblemove(model, 2).size());
		//ArrayList<Move> allmove = Agent.getAllPossiblemove(model, 2);
		//Move move = Agent.getAllPossiblemove(model, 2).get(1500);
		monteCarlo mcl = new monteCarlo(model, 2);
		Move move = mcl.move();
		Agent enemy = new Agent(1, model, false);
		Move eMove = enemy.pRandomMove();
		for (int i = 0; i < 50; i++) {
			model2.makeMove(move);
			model2.makeMove(eMove);
			move.printMove();
			model2.printBoard();
			mcl = new monteCarlo(model2, 2);
			eMove = enemy.pRandomMove();
			move = mcl.move();
		}
	}

}
