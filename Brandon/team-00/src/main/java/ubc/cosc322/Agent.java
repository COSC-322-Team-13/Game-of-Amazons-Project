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
	
	GamePlayer boardatm;
	
	public Agent(boolean isWhite) {
		
		
		
	}
	
	// TODO pick a move at random for now
	
	public Action chooseAction() {
		
		// init
		Action action;
		Random randomNumber = new Random();
		
		// populate with random move
		
		ArrayList<String> initialPos = new ArrayList<String>();
		ArrayList<String> finalPos = new ArrayList<String>();
		ArrayList<String> shotPos = new ArrayList<String>();
		initialPos.add(AmazonsGameMessage.QUEEN_POS_CURR);
		finalPos.add(AmazonsGameMessage.Queen_POS_NEXT);
		shotPos.add(AmazonsGameMessage.ARROW_POS);
		
		// make the chosen action
		action = new Action(shotPos, initialPos, finalPos);
		
		// return the chosen action to whoever called
		return action;
		
	}
	
}
