package ubc.cosc322;

import java.util.*;

public class monteCarlo{
	TreeNode root;
	
	public monteCarlo(TreeNode root) {
		this.root = root;
		this.root.expandTree(root, false);
	}
	
	public void backPropogateVisit(TreeNode leaf) {
		// visit the leafNode
		leaf.visitNode();
		// save the value to back propogate it
		int value = leaf.getValue();
		while (leaf.getParent() != null) {
			leaf = leaf.getParent();
			int parentValue = leaf.getValue();
			leaf.setValue(parentValue + value);
			leaf.visitNode();
		}
	}
	
	public void simulate(TreeNode leaf) {
		// Dummy method right now that just assigns a value to the leaf node randomly for now
		Random value = new Random();
		leaf.setValue(value.nextInt(100));
		
	}

	public TreeNode pickNodeToExpand() {
		ArrayList<TreeNode> children = root.getChildren();
		Queue<TreeNode> searchLeaf = new LinkedList<>(children);
		ArrayList<TreeNode> leaf = new ArrayList<>();
		while(!searchLeaf.isEmpty()) {
			TreeNode possibleLeaf = searchLeaf.poll();
			if (possibleLeaf.getLeaf()) {
				leaf.add(possibleLeaf);
			} else {
				searchLeaf.addAll(possibleLeaf.getChildren());
			}
		}
		ArrayList<Double> ucb1Value = new ArrayList<>();
		for (TreeNode targetNodes : leaf) {
			ucb1Value.add(getUCB1(targetNodes));
		}
		Double max = 0.0;
		if (ucb1Value.get(0) != Double.POSITIVE_INFINITY) max = ucb1Value.get(0);
		int index = 0;
		for (int i = 0; i < ucb1Value.size(); i++) {
			Double maybeMax = ucb1Value.get(i);
			if (maybeMax != Double.POSITIVE_INFINITY) {
				if (maybeMax > max) {
					max = maybeMax;
					index = i;
				}
			}
		}
		return leaf.get(index);
	}

	public Double getUCB1(TreeNode node) {
		if (node.getNumVisit() == 0) {
			return Double.POSITIVE_INFINITY;
		} else {
			double averageValue = node.getValue() / node.getNumVisit();
			int parentVisit = node.getParent().getNumVisit();
			Double UCB1 = averageValue + (2 * Math.sqrt(Math.log(parentVisit) / node.getNumVisit()));
			return UCB1;
		}
	}
}
