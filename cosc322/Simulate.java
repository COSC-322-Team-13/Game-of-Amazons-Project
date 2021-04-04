package ubc.cosc322;

import java.util.ArrayList;
import java.util.Random;

public class Simulate implements Runnable {
	private int winner;
	private TreeNode treenode;
	private long endTime;
	
	@Override
	public void run() {
		while (System.currentTimeMillis() < endTime) {
			TreeNode i = null;
		}
	}

	public Simulate(TreeNode treenode, long endTime) {
		this.winner = 0;
		this.treenode = treenode;
		this.endTime = endTime;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public int getWinner() {
		return this.winner;
	}

	public int simulate() {
		BoardModel currentState = treenode.getBoardState().getCopy();
		Random random = new Random();

		// get our player and opponent player
		int ourPlayer = currentState.ourPlayer;
		int opponentPlayer;
		if (ourPlayer == 1) {
			opponentPlayer = 2;
		} else {
			opponentPlayer = 1;
		}

		int winner = 0;
		while (winner == 0) {
			// opponent move
			ArrayList<Move> opponentAllMove = Agent.getAllPossiblemove(currentState, opponentPlayer);
			if (opponentAllMove.size() == 0) {
				winner = ourPlayer;
				break;
			} else {
				int randomNum = random.nextInt(opponentAllMove.size());
				Move selectedMove = opponentAllMove.get(randomNum);
				currentState.makeMove(selectedMove);
			}

			// our move
			ArrayList<Move> ourAllMove = Agent.getAllPossiblemove(currentState, ourPlayer);
			if (ourAllMove.size() == 0) {
				winner = opponentPlayer;
				break;
			} else {
				int randomNum = random.nextInt(ourAllMove.size());
				Move selectedMove = ourAllMove.get(randomNum);
				currentState.makeMove(selectedMove);
			}
		}
		// System.out.println("Winner is " + winner);
		return winner;
	}
}
