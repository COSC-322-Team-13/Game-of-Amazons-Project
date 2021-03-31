package ubc.cosc322;

import java.util.*;

public class MonteCarlo {
	
	private TreeNode currentNode;
	private BoardModel boardState;
	private int ourPlayer;
	
	public MonteCarlo(BoardModel boardState, int ourPlayer) {
		this.boardState = boardState;
		this.ourPlayer = ourPlayer;
		this.currentNode = new TreeNode(boardState);
	}
	
	public Move getBestMove() {
		if(expand(currentNode)) {
			int winNum = 0;
			System.out.println("Node got expanded!");
			ArrayList<TreeNode> children = currentNode.getChildren();
			
			System.out.println("Total of " + children.size() + " nodes has created");
			//will implement thread -> Maybe set up time period
			// Loop through all of children one time( for now and will implement thread to loop this many many times)
			for(int itr = 0; itr < 30; itr++) {
				for(int i = 0; i < children.size(); i++) {
					int winner = simulate(children.get(i));
					// save result to the node
					if(winner == ourPlayer) {
						children.get(i).setWin(children.get(i).getWin() + 1);
						winNum++;
					}
					children.get(i).setSimulated(children.get(i).getSimulated() + 1);
				}
			}
			System.out.println("I won " + winNum + " times!");
			
			double maxUCT = 0;
			Move maxUCTmove = children.get(0).getMove();
			
			for(int i = 0; i < children.size(); i++) {
				double tempUCT = children.get(i).getUCT();
				if(tempUCT > maxUCT) {
					maxUCT = tempUCT;
					maxUCTmove = children.get(i).getMove();
				}
			}
			return maxUCTmove;
			
			
			
		}else {
			System.out.println("No Move canbe made!");
			return null;
		}
	}
	
	
	public boolean expand(TreeNode treenode) {
		BoardModel currentState = treenode.getBoardState();
		ArrayList<Move> allMove = Agent.getAllPossiblemove(currentState, ourPlayer);
		ArrayList<TreeNode> childrenNode = new ArrayList<TreeNode>();
		// if cannot make a move return false
		if(allMove.size() == 0) {
			return false;
		}
		for(int i = 0; i < allMove.size(); i++) {
			Move tempMove = allMove.get(i);
			BoardModel tempBoard = currentState.getCopy();
			tempBoard.makeMove(tempMove);
			
			tempBoard.ourPlayer = ourPlayer;
			
			childrenNode.add(new TreeNode(tempBoard, tempMove, treenode));
			treenode.setChildren(childrenNode);
		}
		return true;
	}
	
	public int simulate(TreeNode treenode) {
		BoardModel currentState = treenode.getBoardState().getCopy();
		Random random = new Random();
		
		// get our player and opponent player
		int ourPlayer = currentState.ourPlayer;
		int opponentPlayer;
		if(ourPlayer == 1) {
			opponentPlayer = 2;
		}else {
			opponentPlayer = 1;
		}
		
		int winner = 0;
		while(winner == 0) {
			// opponent move
			ArrayList<Move> opponentAllMove = Agent.getAllPossiblemove(currentState, opponentPlayer);
			if(opponentAllMove.size() == 0) {
				winner = ourPlayer;
				break;
			}else {
				int randomNum = random.nextInt(opponentAllMove.size());
		        Move selectedMove = opponentAllMove.get(randomNum);
		        currentState.makeMove(selectedMove);
			}
			
			// our move
			ArrayList<Move> ourAllMove = Agent.getAllPossiblemove(currentState, ourPlayer);
			if(ourAllMove.size() == 0) {
				winner = opponentPlayer;
				break;
			}else {
				int randomNum = random.nextInt(ourAllMove.size());
		        Move selectedMove = ourAllMove.get(randomNum);
		        currentState.makeMove(selectedMove);
			}
		}
		//System.out.println("Winner is " + winner);
		return winner;		
	}

}
