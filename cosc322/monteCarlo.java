package ubc.cosc322;

import java.util.*;

public class monteCarlo {

	private TreeNode root;
	private BoardModel boardState;
	private int ourPlayer;

	public monteCarlo(BoardModel boardState, int ourPlayer) {
		this.boardState = boardState;
		this.ourPlayer = ourPlayer;
		this.root = new TreeNode(boardState);
	}

	public Move move() {
		final int threadcount = 4;
		expand(root);
		ArrayList<TreeNode> children = root.getChildren();
		Thread[] threadArray = new Thread[threadcount];
		monteCarloRunning[] running = new monteCarloRunning[threadcount];
		int childPerThread = children.size() / threadcount;
		int extraChildren = children.size() % threadcount;
		for (int i = 0; i < threadcount; i++) {
			TreeNode rootOfThread = new TreeNode (this.boardState);
			int start = childPerThread * i;
			int endI = childPerThread * (i + 1);
			rootOfThread.setChildren(new ArrayList<> (children.subList(start, endI)));
			if (i == 0) {
				start = children.size() - extraChildren;
				endI = children.size();
				rootOfThread.setChildren(new ArrayList<> (children.subList(start, endI)));
			}
			monteCarloRunning run = new monteCarloRunning(rootOfThread);
			running[i] = run;
			Thread thread = new Thread(run);
			threadArray[i] = thread;
			thread.start();
		}
		for (int i = 0; i < threadcount; i++) {
			try {
				System.out.println("Attempting to join thread " + i);
				threadArray[i].join(4000);
			} catch (Exception e) {
				threadArray[i].interrupt();
				e.printStackTrace();
			}
		}
		System.out.println("All threads rejoined");
		
		return getBestMove(root);
	}
	public Move getBestMove(TreeNode root) {
		Move best = null;
		int mostWin = -2;
		
		for (TreeNode child: root.getChildren()) {
			if (child.getWin() > mostWin) {
				mostWin = child.getWin();
				best = child.getMove();
			}
		}
		return best;
		
	}

	public boolean expand(TreeNode treenode) {
		BoardModel currentState = treenode.getBoardState();
		ArrayList<Move> allMove = Agent.getAllPossiblemove(currentState, ourPlayer);
		ArrayList<TreeNode> childrenNode = new ArrayList<TreeNode>();
		// if cannot make a move return false
		if (allMove.size() == 0) {
			return false;
		}
		for (int i = 0; i < allMove.size(); i++) {
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

	
	public void backPropogate(TreeNode simulated, int winner) {
		while (simulated != null) {
			if (winner == ourPlayer) {
				simulated.incrementWin();
			}
			simulated.setSimulated();
		}
	}

	private class monteCarloRunning implements Runnable {
		TreeNode rootNode;
		
		public monteCarloRunning(TreeNode rootNode) {
			this.rootNode = rootNode;
		}

		@Override
		public void run() {
			while (true) {
				System.out.println("Thread has begun running");
				TreeNode iterator = getBestLeafChild(this.rootNode);
				expand(iterator);
				ArrayList<TreeNode> children = iterator.getChildren();
				Random random = new Random();
				TreeNode randomChild = children.get(random.nextInt(children.size()));
				if (randomChild == null) {
					if (ourPlayer == 1) backPropogate(iterator, 2);
					else backPropogate(iterator, 1);
					continue;
				}
				int winner = simulate(randomChild);
				backPropogate(randomChild, winner);
			}

		}

	}

	public TreeNode getBestLeafChild(TreeNode root) {
		TreeNode iterator = root;
		while (!iterator.getChildren().isEmpty()) {
			double maxUCB1 = Double.MIN_VALUE;
			TreeNode max = null;
			ArrayList<TreeNode> children = iterator.getChildren();
			for (TreeNode child : children) {
				double UCB1 = child.getUCB1();
				if (UCB1 > maxUCB1) {
					maxUCB1 = UCB1;
					max = child;
				}
			}
			iterator = max;
		}
		return iterator;
	}

}
