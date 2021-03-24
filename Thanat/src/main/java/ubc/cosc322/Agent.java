package ubc.cosc322;

public class Agent {
	private boolean isWhite;
	private BoardModel model;
	private String agentName;
	private boolean heuristic;
	
	public Agent(boolean isWhite, BoardModel model, boolean heuristic) {
		
		this.isWhite = isWhite;
		this.model = model;
		this.agentName = "team-13-Agent";
		this.heuristic = heuristic;
	}
	
	public void makeMove(Move move) {
        if(move.getArrowPos()[0]<0){
            System.out.println("I lost!");
            System.exit(0);
        }
        model.makeMove(move.getQueenPosCurrent(), move.getQueenPosNew(), move.getArrowPos());
    }
	
	public Move pickMove() {
		if(heuristic == true) {
			return randomMove(); // will implement heuristic search
		}else {
			return randomMove();
		}
	}
	
	private Move randomMove() {
		
	}
}
