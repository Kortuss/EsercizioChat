package server;
import util.Util;

public class Chat {

    public static  void BroadCast(String Message, Client_Connections connections,Long threadMit,String Nickname){
        for (ClientThread current : connections.Client_Connenctions){
            try {
                System.out.println(current.GetThreadNickName());
                if (current.threadId() == threadMit)
                    current.OutData("(TU)" + Nickname + ": " + Message); 
                else current.OutData(Nickname + ": " + Message);
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
    public static  void Handle_Message(String Message, Client_Connections connections,ClientThread This_Client) throws Exception{
        String Action = Message.substring(0, 3);
        Message = Message.substring(3);
        Util.Log("Action del client: " + Action);
        switch (Action){
            case "SET" -> {
                This_Client.SetThreadNickName(Message);
                return;
            }
            case "TXT" -> {
                Util.Log("Broadcasting the message");
                BroadCast(Message,connections,This_Client.threadId(),This_Client.GetThreadNickName());
                return;
            }
            default -> Util.Log("Azione non conosciuta, il server non performerà nessun azione");
        }
        throw new Exception("Azione non conosciuta, il server non performerà nessun azione");
        
    }
}
