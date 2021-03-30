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
	private int ourPlayer;
	
	public TreeNode(BoardModel boardState, Move move, boolean isTerminal, TreeNode parent, int ourPlayer) {
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
		this.ourPlayer = ourPlayer;
	}
	
	public void expandTree(TreeNode treenode, int ourPlayer) {
		// If node is terminal then return
		if(treenode.isTerminal == true) {
			return;
		}
		ArrayList<Move> allPossibleMove;
		// Check if it is white or black
		allPossibleMove = Agent.getAllPossiblemove(treenode.boardState, ourPlayer);
		
		// loop through all possible move
		for(int i = 0; i < allPossibleMove.size(); i++) {
			Move tempmove = allPossibleMove.get(i);
			BoardModel boardStateTemp = boardState;
			boardStateTemp.makeMove(tempmove);
			
			ArrayList<Move> childrenAllPossibleMove;
			if(ourPlayer == 1) {
				childrenAllPossibleMove = Agent.getAllPossiblemove(boardStateTemp, 2);
			}else{
				childrenAllPossibleMove = Agent.getAllPossiblemove(boardStateTemp, 1);
			}
			
			boolean isTerminalTemp;
			if(childrenAllPossibleMove.size() == 0) {
				isTerminalTemp = true;
			}else {
				isTerminalTemp = false;
			}
			
			TreeNode tempTreeNode = new TreeNode(boardStateTemp, tempmove, isTerminalTemp, this, ourPlayer);
			children.add(tempTreeNode);
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

	public int getOurPlayer() {
		// TODO Auto-generated method stub
		return ourPlayer;
	}
}
