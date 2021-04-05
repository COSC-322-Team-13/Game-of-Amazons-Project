package ubc.cosc322;

import java.sql.Time;
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
		MonteCarlo mcl = new MonteCarlo(model, 2);
		long start = System.currentTimeMillis();
		Move move = mcl.getBestMoveThread();
		long end = System.currentTimeMillis();
		long elapsedTime = (end - start)/1000;
		
		System.out.println("It took " + elapsedTime + "s to run simulation.");
		model2.makeMove(move);
		move.printMove();
		model2.printBoard();
	}

}
