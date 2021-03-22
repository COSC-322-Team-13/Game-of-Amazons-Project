package ubc.cosc322;

import java.util.List;

import java.util.Map;
import java.util.ArrayList;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

/**
 * An example illustrating how to implement a GamePlayer
 * @author Yong Gao (yong.gao@ubc.ca)
 * Jan 5, 2021
 *
 */
public class COSC322Test extends GamePlayer{

    private GameClient gameClient = null; 
    private BaseGameGUI gamegui = null;
	
    private String userName = null;
    private String passwd = null;
 
    private Agent agent;
    private boolean isWhite;
    
    public static CurrentBoard board = new CurrentBoard();
	
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	COSC322Test player = new COSC322Test(args[0], args[1]);
    	
    	if(player.getGameGUI() == null) {
    		player.Go();
    	}
    	else {
    		BaseGameGUI.sys_setup();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                	player.Go();
                }
            });
    	}
    }
	
    /**
     * Any name and passwd 
     * @param userName
      * @param passwd
     */
    public COSC322Test(String userName, String passwd) {
    	this.userName = userName;
    	this.passwd = passwd;
    	
    	//To make a GUI-based player, create an instance of BaseGameGUI
    	//and implement the method getGameGUI() accordingly
    	this.gamegui = new BaseGameGUI(this);
    }
 


    @Override
    public void onLogin() {
    	//System.out.println("Congratualations!!! "
    	//		+ "I am called because the server indicated that the login is successfully");
    	//System.out.println("The next step is to find a room and join it: "
    	//		+ "the gameClient instance created in my constructor knows how!"); 
    	userName = gameClient.getUserName();
    	if(gamegui != null)
    		gamegui.setRoomInformation(gameClient.getRoomList());
    	
    }
    
    @Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	//This method will be called by the GameClient when it receives a game-related message
    	//from the server.
	
    	//For a detailed description of the message types and format, 
    	//see the method GamePlayer.handleGameMessage() in the game-client-api document. 
    	
    	// THIS PART WORKS! JUST HARD TO CHECK CAUSE NEED GAME TO ACTUALLY START
    	// I.E., NEED TWO PEOPLE IN ROOM.
    	// I checked to make sure works for white and black, it does. We get a true and a false,
    	// so it is working as intended and we can play the game based on our piece colour.
    	if(messageType.equals(GameMessage.GAME_ACTION_START)) {
    		
    		
    		System.out.println("This is a test, we received GAME_ACTION_START " +
    				"and we will find out if we received WHITE or BLACK" + '\n' + "so we know which " +
    				"pieces to move. We will now print out TRUE or FALSE for isWhite");
    		
    		isWhite = msgDetails.get("player-white").equals(this.userName);
    		System.out.println(isWhite);
    		
    		if(isWhite) {
    			
    			agent = new Agent(true);
    			//myTurn(agent.chooseAction());
    			
    		}
    		
    		else if(!isWhite) {
    			
    			agent = new Agent(false);
    			myTurn(agent.chooseAction());
    			
    		}
    		
    	}
    	
    	// denotes the enemy player just made a move, so we 
    	// need to handle that and then move on to our turn
    	if(messageType.equals(GameMessage.GAME_ACTION_MOVE)) {
    		
    		System.out.println("ENEMY JUST MADE A MOVE");
    		
    		//Agent agent = new Agent(isWhite);
    		// might be the case that we can play right after enemy?
    		myTurn(agent.chooseAction());
    		
    	}
    	
    	if(messageType.equals(GameMessage.GAME_STATE_BOARD))
    		gamegui.setGameState((ArrayList <Integer>)msgDetails.get("game-state"));
    	if(messageType.equals(GameMessage.GAME_ACTION_MOVE))
    		gamegui.updateGameState(msgDetails);
    		
    	
    	return true;   	
    	
    }
    
    // use this to update piece positions after enemy moves
    public void enemyTurn() {
    	
    	
    	
    }
    
    public void myTurn(Action action) {
    	
    	ArrayList<Integer> iniQPos = new ArrayList<Integer>();
    	int queenIndex;
    	
    	if(isWhite) {
    		
    		queenIndex = (int) Math.random()*4+4;
    		iniQPos = board.getSpecificQueenPos(queenIndex);
    		
    	}
    	
    	else {
    		
    		queenIndex = (int) Math.random()*4;
    		iniQPos = board.getSpecificQueenPos(queenIndex);
    		
    	}
    	
    	System.out.println(iniQPos);
    	ArrayList<Integer> finQPos = action.getqf();
    	board.setSpecificQueenPos(finQPos.get(0), finQPos.get(1), queenIndex);
    	ArrayList<Integer> arrowPos = action.getShot();
    	
    	System.out.println("MY MOVE IS BEING SENT");
    	System.out.println(iniQPos + " " + finQPos + " " + arrowPos);
    	gameClient.sendMoveMessage(iniQPos, finQPos, arrowPos);
    	System.out.println("MY MOVE WAS MADE");
    }
   
    
    @Override
    public String userName() {
    	return userName;
    }

	@Override
	public GameClient getGameClient() {
		// TODO Auto-generated method stub
		return this.gameClient;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		// TODO Auto-generated method stub
		return this.gamegui;
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
    	gameClient = new GameClient(userName, passwd, this);			
	}

 
}//end of class
