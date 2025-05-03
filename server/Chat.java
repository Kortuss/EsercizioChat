package server;
import util.Util;

public class Chat {
    

    public static void BroadCast(String NickName,String Message, Client_Connections connections){
        for (ClientThread current : connections.Client_Connenctions){
            try {
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
    public static  boolean Handle_Message(String NickName,String Message, Client_Connections connections){
        if (!Message.contains(" ")){
            Util.Log("Messaggio incomprensibile, nessun Azione");
            return false;
        }
        String Action = Message.substring(0, Message.indexOf(" "));
        switch (Action){
            case "SET" -> {
                return false;
            }
            case "TXT" -> {
                BroadCast(NickName, Message,connections);
                return true;
            }
            default -> Util.Log("Azione non conosciuta, il server non performerà nessun azione");
        }
        return false;
        
    }
}
