package server;
import java.io.*;
import java.net.*;
import util.Util;
public class ClientThread extends Thread {
    private Socket ThreadSocket = null;
    private DataInputStream StreamIn = null;
    private DataOutputStream StreamOut = null;
    private Client_Connections connections = null;
    public int Interazioni;
    String NickName;
    public ClientThread(Socket CurrentDataSocket,Client_Connections connections){
        ThreadSocket = CurrentDataSocket;
        this.connections = connections;
        NickName = "User" + Long.toString(this.threadId());
        Util.Log(Thread.currentThread().getName());
        Interazioni = 0;
    }

        
    public String InData() throws Exception{
        String m = "";
        //Ciclo while che si interrompe in caso di chiusura inaspettata del DataSocket
        while (true)
        {
            try{
                String in;
                in = StreamIn.readUTF();
                //Controllo se la riga corrisponde al segnale di fine trasmissione scelto da client e server, in questo caso ho scelto exit
                if (in.equals("exit\n")){
                    Util.Log("Segnale di fine trasmissione ricevuto");
                    break;
                }
                //Leggo ogni riga
                m += in;
                Util.Log("Dati ricevuti dal Client:\n" + m);
            }
            catch (EOFException err) {
                Util.Log("Fine Stream dati inaspettato\n O errore di conessione, o la parola di fine trasmissione non e' stata inviata\n");
                break;
            }
            catch (IOException err) {
                Util.Log(err);
                CloseClientInstances();
                break;
            }
        }

        Util.Log(m);
        return m;
    }
    
    public String GetThreadNickName (){
        return NickName;
    }

    public void SetThreadNickName (String NickName){
        this.NickName = NickName;
    }

    public void OutData(String Message) throws Exception{
        //Qui invio una risposta statica al client, poichè un input tramite scanner risulterebbe un evento bloccante per il main Loop del server.
        //Essendo la consegna relativa unicamente alla realizzazione di un cliente ho preferito tralasciare la gestione di questo problema.
        //Egli infatti risulterebbe alquanto complessso da gestire.
        StreamOut.writeUTF(Message);
        StreamOut.writeUTF("exit\n");
    }

    public void CloseClientInstances() throws Exception {
        //Mi ricordo di chiudere DataSocke e Streams di questo specifico client
        Util.Log("Chiusura Connessione di questo specifico Client");
        System.out.println("Sto stampando Le interazioni del client " + NickName + ": " + Interazioni);
        StreamIn.close();
        StreamOut.close();
        ThreadSocket.close();
    }


    private void InstanceStreams() throws Exception{
        //Istanzio gli stream per la comunicazione con il client
        StreamOut = new DataOutputStream(ThreadSocket.getOutputStream());
        StreamIn = new DataInputStream(ThreadSocket.getInputStream());
    }
    //Definisco un thread per ogni client che si connette al server
    //In questo modo il server può gestire più client in parallelo
    @Override
    public void run() {
        try {
            InstanceStreams();
            while (!ThreadSocket.isClosed()){
                String Data = InData();
                if (Data.equals("Close") || Data.equals("Close\n"))
                    break;
                else{
                    try {
                        Interazioni++;
                        Chat.Handle_Message(Data,connections,this);
                    } catch (Exception e) {
                        OutData("qualcosa è andato storto nella gestione della tua richiesta (internal server error)");
                        OutData("Ecco l'errore: " + e);
                    }
                        
                }
            }
            CloseClientInstances();
        } catch (Exception e) {
            Util.Log(e);
        }
    }
}