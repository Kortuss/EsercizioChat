package server;
import util.Util;

public class Chat {
    String NickName;

    public void BroadCast(String Message, Client_Connections connections){
        for (ClientThread current : connections.Client_Connenctions){
            try {
                if (NickName == null || NickName.isEmpty())
                    NickName = "Anonymous";
                current.OutData(NickName + ": " + Message);   
            } catch (Exception Err) {
                Util.Log(Err);
            }
        }  
    }


    /*
     * i messaggi sono composti da una parole azione per comprendere cosa il server deve compiere ed il contenuto del messaggio
     * SET
     * Hiras_Picano
     * (Imposta il nickname)
     * 
     * TXT
     * Ciao come stai?
     * (Invia un messaggio)
     */
    //Return true se l'azione è stata eseguita con successo altrimenti false
    public void Handle_Message(String Message, Client_Connections connections) throws Exception{
        String Action = Message.substring(0, 3);
        Message = Message.substring(3);
        Util.Log("Action del client: " + Action);
        switch (Action){
            case "SET" -> {
                NickName = Message;
                return;
            }
            case "TXT" -> {
                Util.Log("Broadcasting the message");
                BroadCast(Message,connections);
                return;
            }
            default -> Util.Log("Azione non conosciuta, il server non performerà nessun azione");
        }
        throw new Exception("Azione non conosciuta, il server non performerà nessun azione");
        
    }
}
