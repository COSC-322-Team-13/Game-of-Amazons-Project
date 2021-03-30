
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
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	if(messageType.equalsIgnoreCase("cosc322.game-state.board")) {
    		ArrayList<Integer> gameState = (ArrayList<Integer>) msgDetails.get("game-state");
    		board.printQueens();
    		board.printBoard();
    		gamegui.setGameState(gameState);
    		return true;
    	}
    	if(messageType.equalsIgnoreCase("cosc322.game-action.start")) {
    		isWhite = msgDetails.get("player-white").equals(this.userName);
    		if(isWhite == true) {
    			board.ourPlayer = BoardModel.POS_MARKED_WHITE;
    		}else {
    			board.ourPlayer = BoardModel.POS_MARKED_BLACK;
    		}
    		agent = new Agent(board.ourPlayer, board, false);
    		System.out.println("We are playing as :" + board.ourPlayer);
    		if (isWhite == false) {
    			System.out.println("Game Start!!");
    			System.out.println("My agent is picking a move.");
    			Move thismove = agent.pickMove();
        		myTurn(thismove);
        		board.printQueens();
        		board.printBoard();
    		}
    	}
    	if(messageType.equalsIgnoreCase("cosc322.game-action.move")) {
    		handleOppoentMove(msgDetails);
    		board.printQueens();
    		board.printBoard();
    		
    		Move thismove = agent.pickMove();
    		myTurn(thismove);
    		board.printQueens();
    		board.printBoard();
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
        gamegui.updateGameState(queenCurrent, queenTarget, arrowTarget);
        board.makeMove(queenCurrentArr, queenTargetArr, arrowTargetArr);
    }
    
    public void myTurn(Move move) {
    	ArrayList<Integer> queenPosCurrent = new ArrayList<>(Arrays.asList( move.getQueenPosCurrent()[0] + 1, move.getQueenPosCurrent()[1] + 1 ));
    	ArrayList<Integer> queenPosNew = new ArrayList<>(Arrays.asList( move.getQueenPosNew()[0] + 1, move.getQueenPosNew()[1] + 1 ));
    	ArrayList<Integer> arrowPos = new ArrayList<>(Arrays.asList( move.getArrowPos()[0] + 1, move.getArrowPos()[1] + 1 ));
        gameClient.sendMoveMessage(queenPosCurrent, queenPosNew, arrowPos);
        board.makeMove(move);
        System.out.println("My move:");
        System.out.println("Current: " + queenPosCurrent.toString() + " New: " + queenPosNew.toString() + " Arrow: " + arrowPos.toString());
        gamegui.updateGameState(queenPosCurrent, queenPosNew, arrowPos);
    }

    
    
    @Override
    public void onLogin() {
    	userName = gameClient.getUserName();
    	if(gamegui != null) {
    	gamegui.setRoomInformation(gameClient.getRoomList());
    	}
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
