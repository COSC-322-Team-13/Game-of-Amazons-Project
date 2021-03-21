package ubc.cosc322;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

import java.util.*;

// TODO general utility class for agent
public class Agent {
	
	boolean isWhite;
	
	// create a new agent, based on the colour of the pieces
	public Agent(boolean isWhite) {
		
		this.isWhite = isWhite;
		
	}
	
	// TODO pick a move at random for now
	// will be good enough for quick group stage stuff unless we have time and
	// can do some basic strategy
	// also remember TIMEOUT of 30s for the game
	public Action chooseAction() {
		
		// init
		Action action;
		Random randomNumber = new Random();
		
		// populate with random move
		
		do {
			
			ArrayList<Integer> initialPos = new ArrayList<Integer>();
			ArrayList<Integer> finalPos = new ArrayList<Integer>();
			ArrayList<Integer> shotPos = new ArrayList<Integer>();
			
			// choose a random queen to move
			initialPos.add(randomNumber.nextInt(AmazonsGameMessage.QUEEN_POS_CURR.length()));
			
			// choose a random space to move to
			// random place to fire arrow at
			
			finalPos.add(randomNumber.nextInt(8), randomNumber.nextInt(8));
			shotPos.add(randomNumber.nextInt(8), randomNumber.nextInt(8));
			
			action = new Action(shotPos, initialPos, finalPos);
			
			return action;
			
		}
		
		// change this later
		while(true);
		
		// might have to do something about getting the correctly coloured piece
		// as well as the specific piece we want to move
		
		//initialPos.add(AmazonsGameMessage.QUEEN_POS_CURR);
		//finalPos.add(AmazonsGameMessage.Queen_POS_NEXT);
		//shotPos.add(AmazonsGameMessage.ARROW_POS);
		
	}
	
}
