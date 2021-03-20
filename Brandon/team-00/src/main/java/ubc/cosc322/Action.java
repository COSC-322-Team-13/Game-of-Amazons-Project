package ubc.cosc322;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

import java.util.*;

// Class for returning state to Agent so it can make an action
public class Action {

	// Define position of projectile, initial position of queen, final position of queen
	private ArrayList<String> shot;
	private ArrayList<String> qi;
	private ArrayList<String> qf;
	
	
	// Constructor. Initialize private variables
	public Action(ArrayList<String> shot, ArrayList<String> qi, ArrayList<String> qf) {
		
		this.shot = shot;
		this.qi = qi;
		this.qf = qf;
		
	}
	
}