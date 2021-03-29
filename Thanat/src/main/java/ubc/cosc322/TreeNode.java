package ubc.cosc322;

import java.util.ArrayList;

public class TreeNode {
	private BoardModel boardState;
	private TreeNode parent;
	private ArrayList<TreeNode> children;
	private boolean isTerminal;
	private int win;
	private int gameNum;
	private long simulate = 0;
	private Move move;
	private boolean moveOfWhiteColor;
	
	public TreeNode(BoardModel boardState, Move move, boolean isTerminal, TreeNode parent, boolean moveOfWhiteColor) {
		this.boardState = boardState;
		this.move = move;
		this.isTerminal = isTerminal;
		this.win = 0;
		this.children = new ArrayList<TreeNode>();
		this.parent = parent;
		this.simulate = 0;
		this.moveOfWhiteColor = moveOfWhiteColor;
		this.gameNum = 0;
	}
	
	public void expandTree(TreeNode treenode, boolean isWhite) {
		// If node is terminal then return
		if(treenode.isTerminal == true) {
			return;
		}
		ArrayList<Move> allPossibleMove;
		// Check if it is white or black
		if(isWhite == true) {
			allPossibleMove = TestMonteCarlo.getAllPossiblemove(treenode.boardState, true);
		}else {
			allPossibleMove = TestMonteCarlo.getAllPossiblemove(treenode.boardState, false);
		}
		
		// loop through all possible move
		for(int i = 0; i < allPossibleMove.size(); i++) {
			Move tempmove = allPossibleMove.get(i);
			BoardModel boardStateTemp = boardState;
			boardStateTemp.makeMove(tempmove);
			
			ArrayList<Move> childrenAllPossibleMove;
			if(isWhite == true) {
				childrenAllPossibleMove = TestMonteCarlo.getAllPossiblemove(boardStateTemp, true);
			}else {
				childrenAllPossibleMove = TestMonteCarlo.getAllPossiblemove(boardStateTemp, false);
			}
			
			boolean isTerminalTemp;
			if(childrenAllPossibleMove.size() == 0) {
				isTerminalTemp = true;
			}else {
				isTerminalTemp = false;
			}
			
			if(isWhite == true) {
				TreeNode tempTreeNode = new TreeNode(boardStateTemp, tempmove, isTerminalTemp, this, true);
				children.add(tempTreeNode);
			}else {
				TreeNode tempTreeNode = new TreeNode(boardStateTemp, tempmove, isTerminalTemp, this, false);
				children.add(tempTreeNode);
			}
		}
	}
	public BoardModel getBoardState() {
		return boardState;
	}

	public boolean isTerminal() {
		return isTerminal;
	}

	public void setBoardState(BoardModel boardState) {
		this.boardState = boardState;
	}

	public void setTerminal(boolean isTerminal) {
		this.isTerminal = isTerminal;
	}
}
