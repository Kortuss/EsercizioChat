package server;

import java.util.ArrayList;
import java.util.List;



public class Client_Connections {
	public List<ClientThread> Client_Connenctions = null;

    public Client_Connections() {
		Client_Connenctions = new ArrayList<>();
	}
    
	public void Push(ClientThread ToPush){
		Client_Connenctions.add(ToPush);
	}



}
