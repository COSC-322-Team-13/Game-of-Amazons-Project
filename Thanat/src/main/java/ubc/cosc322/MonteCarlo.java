package ubc.cosc322;

import java.util.*;

public class MonteCarlo implements Runnable {
	TreeNode root;

	@Override
	public void run() {
		ArrayList<TreeNode> children = root.getChildren();
		ArrayList<Double> ucb1Value = new ArrayList<>();
		for (TreeNode child : children) {
			ucb1Value.add(getUCB1(child));
		}
		Double max = 0.0;
		if (ucb1Value.get(0) != Double.POSITIVE_INFINITY) max = ucb1Value.get(0);
		int index = 0;
		for (int i = 0; i < ucb1Value.size(); i++) {
			Double maybeMax = ucb1Value.get(0);
			if (maybeMax != Double.POSITIVE_INFINITY) {
				if (maybeMax > max) {
					max = maybeMax;
					index = i;
				}
			}
		}
		
		
	}

	public MonteCarlo(TreeNode root) {
		this.root = root;
		this.root.expandTree(root, root.getOurPlayer());
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
