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
	public Move getBestMoveThread() {
		int THREADNUM = 8;
		int ITERATIONNUM;
		if(expand(currentNode)) {
			int winNum = 0;
			System.out.println("Node got expanded!");
			ArrayList<TreeNode> children = currentNode.getChildren();
			
			System.out.println("Total of " + children.size() + " nodes has created");
			
			// if number of node are more than 8 then use multithreading
			if(children.size() >= 8) {
				
				int threadChildCount = children.size() / THREADNUM;
				int extraChildCount = children.size() % THREADNUM;
				
				Thread[] threads = new Thread[THREADNUM];
				//int[] winSum = new int[THREADNUM];
				if(children.size() >= 1000) {
					ITERATIONNUM = 15;
				}else if(children.size() < 1000 && children.size() >= 500) {
					ITERATIONNUM = 50;
				}else if(children.size() < 500 && children.size() >= 250){
					ITERATIONNUM = 200;
				}else if(children.size() < 250 && children.size() >= 100) {
					ITERATIONNUM = 400;
				}else {
					ITERATIONNUM = 800;
				}
				
				System.out.println("There will be a total of " + ITERATIONNUM + " simulations.");
				
				for (int tdIndex = 0; tdIndex < THREADNUM; tdIndex++) {
					int startIdx = threadChildCount * (tdIndex - 1);
					int endIdx = threadChildCount * tdIndex;
					if(tdIndex == 0) {
						if(extraChildCount != 0) {
							startIdx = children.size() - extraChildCount;
							endIdx = children.size();
						}else {
							startIdx = children.size() - threadChildCount;
							endIdx = children.size();
						}
					}
					ArrayList<TreeNode> tempArray = new ArrayList<>(children.subList(startIdx, endIdx));
					//System.out.println("Thread Number " + tdIndex + " have index " + startIdx + " to " + endIdx);
					SimulationRun simThread = new SimulationRun(tempArray, ITERATIONNUM);
					Thread thread = new Thread(simThread);
					threads[tdIndex] = thread;
					thread.start();
					
				}
				for (int i = 0; i < THREADNUM; i++) {
					try {
						threads[i].join();
					} catch (Exception e) {
						System.out.println(e);
					}
				}
				
				double maxUCT = 0;
				Move maxUCTmove = children.get(0).getMove();
				
				int winSum = 0;
				int itrSum = 0;
				for(int i = 0; i < children.size(); i++) {
					winSum += children.get(i).getWin();
					itrSum += children.get(i).getSimulated();
					double tempUCT = children.get(i).getUCT();
					if(tempUCT > maxUCT) {
						maxUCT = tempUCT;
						maxUCTmove = children.get(i).getMove();
					}
				}
				System.out.println("I won " + winSum + " times from " + itrSum + " rounds.");
				return maxUCTmove;
			}else {
				// if there are less than 8 node then used single thread.
				for(int itr = 0; itr < 500; itr++) {
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
			}
		}else {
			System.out.println("No Move can be made!");
			System.exit(0);
			return null;
		}
		
	}
	
	private class SimulationRun implements Runnable {
		ArrayList<TreeNode> childrenSubset;
		int IterationNum;

		public SimulationRun(ArrayList<TreeNode> childrenSubset, int IterationNum) {
			this.childrenSubset = childrenSubset;
			this.IterationNum = IterationNum;
		}

		@Override
		public void run() {
			for(int itr = 0; itr < IterationNum; itr++) {
				for(int i = 0; i < childrenSubset.size(); i++) {
					int winner = simulate(childrenSubset.get(i));
					// save result to the node
					if(winner == ourPlayer) {
						childrenSubset.get(i).setWin(childrenSubset.get(i).getWin() + 1);
					}
					childrenSubset.get(i).setSimulated(childrenSubset.get(i).getSimulated() + 1);
				}
			}
		}
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
			System.out.println("No Move can be made!");
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
