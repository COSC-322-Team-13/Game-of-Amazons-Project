package ubc.cosc322;

import ygraph.ai.smartfox.games.GameClient;

public class GameCommunication {
	private GameClient gameClient;
	private String userName;
    private String passwd;
    private int RoomNumber;
    
    public GameCommunication(String userName, String passwd, int RoomNumber) {
    	GameClient gameclient = new GameClient(userName, passwd);
    	this.RoomNumber = RoomNumber;
    	this.userName = userName;
    	this.passwd = passwd;
    }
}
