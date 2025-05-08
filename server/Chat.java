package server;
import util.Util;
//Classe statica che gestisce tutte le funzioni statiche riguardo la gestioen della chat
public class Chat {

    public static  void BroadCast(String Message, Client_Connections connections,Long threadMit,String Nickname){
        //Ciclo (for in range) per tutte le connessione (client thread) ed inoltro il messaggio a tutti i client connessi 
        for (ClientThread current : connections.Client_Connenctions){
            try {
                System.out.println(current.GetThreadNickName());
                //Al client mittente inseriamo nel messaggio anche il prefisso (TU) per indicare quali messaggi sono inviati da lui
                //per controllare chi è il destinatario verifichiamo la corrispondenza fra i socket id di ogni client con quello del mittente
                if (current.threadId() == threadMit)
                    current.OutData("(TU)" + Nickname + ": " + Message); 
                //Altrimenti invia solo nickname e messaggio
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
            //Se l'azione è Set configuriamo il Nickname
            case "SET" -> {
                This_Client.SetThreadNickName(Message);
                return;
            }
            //Se TXT Inoltriamo il messaggio in broadcast
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
