package ubc.cosc322;

import java.util.Map;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.GameMessage;

public class Game extends GamePlayer{
	
	private GameClient gameClient;
	private String userName;
    private String passwd;
    private int RoomNumber;
    private BaseGameGUI gamegui;
    private boolean isWhite;
    
    public Game(String userName, String passwd, int RoomNumber) {
    	GameClient gameclient = new GameClient(userName, passwd);
    	this.RoomNumber = RoomNumber;
    	this.userName = userName;
    	this.passwd = passwd;
    	this.gamegui = new BaseGameGUI();
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
	public void connect() {
		// TODO Auto-generated method stub
		gameClient = new GameClient(userName, passwd, this);
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
	public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
		// TODO Auto-generated method stub
		if(messageType.equals(GameMessage.GAME_STATE_BOARD)) {
			if(((String)msgDetails.get("player-white")).equals(this.userName)){
				System.out.println("User: " + this.userName + " play as WHITE.");
				
			}
		}else if(messageType.equals(GameMessage.GAME_ACTION_MOVE)){
            
		}
		return false;
	}

	@Override
	public String userName() {
		// TODO Auto-generated method stub
		return userName;
	}
}
