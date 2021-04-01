package ubc.cosc322;

import java.util.ArrayList;

public class TreeNode {
	private ArrayList<TreeNode> children;
	private BoardModel boardState;
	private int win;
	private int simulated;
	private Move move;
	private TreeNode parent;

	// For children Node
	public TreeNode(BoardModel boardState, Move move, TreeNode parent) {
		this.boardState = boardState;
		this.move = move;
		this.parent = parent;
		this.win = 0;
		this.simulated = 0;
		this.children = new ArrayList<TreeNode>();
	}
	// initial node
	public TreeNode(BoardModel boardState) {
		this.boardState = boardState;
		this.move = null;
		this.parent = null;
		this.win = 0;
		this.simulated = 0;
		this.children = new ArrayList<TreeNode>();
	}
	public double getUCT() {
		if (this.getSimulated() == 0) {
			return 0;
		}
		double winNum = (double)this.win;
		double simNum = (double)this.simulated;
		double uct = winNum / simNum;
		return uct;
	}
	public ArrayList<TreeNode> getChildren() {
		return children;
	}
	public void setWin(int win) {
		this.win = win;
	}
	public void setSimulated(int simulated) {
		this.simulated = simulated;
	}
	public void setChildren(ArrayList<TreeNode> children) {
		this.children = children;
	}
	public int getWin() {
		return win;
	}
	public int getSimulated() {
		return simulated;
	}
	public Move getMove() {
		return move;
	}
	public TreeNode getParent() {
		return parent;
	}
	public BoardModel getBoardState() {
		return boardState;
	}
}
