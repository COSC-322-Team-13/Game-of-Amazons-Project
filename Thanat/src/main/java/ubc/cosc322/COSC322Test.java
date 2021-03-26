
package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.GameMessage;

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
    public static BoardModel board = new BoardModel();
 
	
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
//    	System.out.println("Congratualations!!! "
//    			+ "I am called because the server indicated that the login is successfully");
//    	System.out.println("The next step is to find a room and join it: "
//    			+ "the gameClient instance created in my constructor knows how!");
//    	GameClient gclient = getGameClient();
//    	List<Room> gamerooms = gclient.getRoomList();
//    	for (Room room : gamerooms) {
//            System.out.println(room);
//        }
//    	String roomToJoin = "Yellow Lake";
//    	gclient.joinRoom(roomToJoin);
    	userName = gameClient.getUserName();
    	if(gamegui != null) {
    	gamegui.setRoomInformation(gameClient.getRoomList());
    	}
    }

    @Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	//This method will be called by the GameClient when it receives a game-related message
    	//from the server.
	
    	//For a detailed description of the message types and format, 
    	//see the method GamePlayer.handleGameMessage() in the game-client-api document. 
    	//System.out.println(msgDetails);
//    	if(messageType.equals("cosc322.game-state.board"))
//            gamegui.setGameState((ArrayList<Integer>) msgDetails.get("game-state"));
//        if(messageType.equals("cosc322.game-action.move"))
//            gamegui.updateGameState(msgDetails);
    	if(messageType.equals(GameMessage.GAME_ACTION_START)) {
    		
    		System.out.println("This is a test, we received GAME_ACTION_START " +
    				"and we will find out if we received WHITE or BLACK" + '\n' + "so we know which " +
    				"pieces to move. We will now print out TRUE or FALSE for isWhite");
    		
    		isWhite = msgDetails.get("player-white").equals(this.userName);
    		System.out.println(isWhite);
    		
    		if(isWhite) {
    			// if white do not do anything at first round
    			agent = new Agent(true, board, false);
    			//myTurn(agent.chooseAction());    			
    		}
    		else if(!isWhite) {
    			
    			agent = new Agent(true, board, false);
    			myTurn(agent.pickMove());
    			//need to check
    			//gamegui.updateGameState(msgDetails);
    			board.printBoard();
    			
    		}
    		if(messageType.equals(GameMessage.GAME_ACTION_MOVE)) {
        		
        		System.out.println("ENEMY JUST MADE A MOVE");
        		handleOppoentMove(msgDetails);
        		//print Game state
        		board.printBoard();
        		// might be the case that we can play right after enemy?
        		System.out.println("My move!");
        		myTurn(agent.pickMove());
        		//print Game state
        		
        		
        		//need to check line 129
        		//gamegui.updateGameState(msgDetails); 
        		board.printBoard();
        		
        	}	
    	}
        return true; 	
    }
    @SuppressWarnings("unchecked")
	private void handleOppoentMove(Map<String, Object> msgDetails) {
    	ArrayList<Integer> queenCurrent = (ArrayList<Integer>) msgDetails.get("queen-position-current");
        ArrayList<Integer> queenTarget = (ArrayList<Integer>) msgDetails.get("queen-position-next");
        ArrayList<Integer> arrowTarget = (ArrayList<Integer>) msgDetails.get("arrow-position");
        int[] queenCurrentArr = new int[2];
        int[] queenTargetArr = new int[2];
        int[] arrowTargetArr = new int[2];
        for (int i=0; i < 2; i++)
        {
        	queenCurrentArr[i] = queenCurrent.get(i).intValue() - 1;
        	queenTargetArr[i] = queenTarget.get(i).intValue() - 1;
        	arrowTargetArr[i] = arrowTarget.get(i).intValue() - 1;
        }
        board.setTile(queenCurrentArr, BoardModel.POS_AVAILABLE);
        if(isWhite == true) {
        	board.setTile(queenTargetArr, BoardModel.POS_MARKED_BLACK);
        }else {
        	board.setTile(queenTargetArr, BoardModel.POS_MARKED_WHITE);
        }
        board.setTile(arrowTargetArr, BoardModel.POS_MARKED_ARROW);
    }
    
    public void myTurn(Move move) {
    	ArrayList<Integer> queenPosCurrent = new ArrayList<>(Arrays.asList( move.getQueenPosCurrent()[0] + 1, move.getQueenPosCurrent()[1] + 1 ));
    	ArrayList<Integer> queenPosNew = new ArrayList<>(Arrays.asList( move.getQueenPosNew()[0] + 1, move.getQueenPosNew()[1] + 1 ));
    	ArrayList<Integer> arrowPos = new ArrayList<>(Arrays.asList( move.getArrowPos()[0] + 1, move.getArrowPos()[1] + 1 ));
        gameClient.sendMoveMessage(queenPosCurrent, queenPosNew, arrowPos);
        System.out.println("My move:");
        System.out.println("Current: " + queenPosCurrent.toString() + " New: " + queenPosNew.toString() + " Arrow: " + arrowPos.toString());
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
