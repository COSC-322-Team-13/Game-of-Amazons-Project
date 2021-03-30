package ubc.cosc322;

import java.util.ArrayList;

public class TreeNode {
	private BoardModel boardState;
	private TreeNode parent;
	private ArrayList<TreeNode> children;
	private boolean isTerminal;
	private int win;
	private long simulate = 0;
	private Move move;
	private boolean moveOfWhiteColor;
	private int numVisit = 0;
	private int value = 0;
	private int gameNum = 0;
	private boolean leaf;
	
	public TreeNode(BoardModel boardState, Move move, boolean isTerminal, TreeNode parent, boolean moveOfWhiteColor) {
		this.boardState = boardState;
		this.move = move;
		this.isTerminal = isTerminal;
		this.win = 0;
		this.children = new ArrayList<TreeNode>();
		this.parent = parent;
		this.simulate = 0;
		this.moveOfWhiteColor = moveOfWhiteColor;
		this.value = 0;
		this.numVisit = 0;
		leaf = false;
	}
	
	public void expandTree(TreeNode treenode, boolean isWhite) {
		// This node is no longer a leaf node so change boolean leaf to false
		treenode.leaf = false;
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
				tempTreeNode.leaf = true;
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
	
	public int getNumVisit() {
		return this.numVisit;
	}
	
	public void visitNode() {
		this.numVisit++;
	}

	public TreeNode getParent() {
		return parent;
	}


	public ArrayList<TreeNode> getChildren() {
		return children;
	}


	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean getLeaf() {
		return this.leaf;
	}
	
}
