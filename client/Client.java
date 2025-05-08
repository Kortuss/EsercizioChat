// Scrittura di una classe Client che si occupa di Connettersi ad un server e gestire la comunicazione con esso
//Autore: Hiras Picano V B INF 
//Compito assegnato il 21/03/25 per il 24/03/25
package client;
import client.interfaces.Render;
import java.io.*;
import java.net.*;
import util.*;
public class Client
{
    //Dichiaro il socket che si connetterà al server
    private Socket DataSocket = null;
    //Dichiaro gli stream
    private DataInputStream StreamIn = null;
    private DataOutputStream StreamOut = null;
    public String CurrentHost;
    public int CurrentPort;
    
    public void CloseClient() throws Exception {
        Util.Log("Operazioni concluse\nChiusura connessione");
        //Mi ricordo di chiudere sia gli Stream che il Socket
        StreamIn.close();
        StreamOut.close();
        DataSocket.close();
    }

    public boolean isConnected(){
        return DataSocket.isConnected();
    }

    public void OutData(String Messaage) throws Exception {
        //Verifico se il messaggio ha già un carattere di fine riga, altrimenti lo aggiungo
        if (Messaage.charAt(Messaage.length() - 1) != '\n'){
            Messaage += "\n";
        }
        //Invio dati al Server tramite lo stream di output (Inviati come UTF-8)
        StreamOut.writeUTF(Messaage);
    }

    public String InData() throws Exception {
        String message = "";
        //Non conoscendo quante righe di dati invierà il server, uso un ciclo while che evenutalmente si interrompà in caso di chiusura inaspettata del socket
        while (DataSocket.isConnected()){
            try{
                //leggo ogni riga
                String m = StreamIn.readUTF();
                //Controllo se la riga corrisponde al segnale di fine trasmissione scelto da entrambi, in questo caso ho scelto exit
                if (m.equals("exit\n")){
                    Util.Log("Segnale di fine trasmissione ricevuto");
                    break;
                }
                message += m;

            }
            catch (EOFException err) {
                Util.Log("Fine Stream dati inaspettato\nErrore di conessione o il segnale di fine trasmissione non e' stata inviata\n");
                break;
            }
            catch (IOException err) {
                Util.Log(err);
            }
        }
        Util.Log("Dati ricevuti Dal Server:\n" + message);
        return message;
    }

    public Client(int Port, String Address) throws Exception {
        this.DataSocket = new Socket(Address,Port);    
        CurrentHost = Address;
        CurrentPort = Port;
        //queste operazioni mi permettono di associare le stream di input e output provienient al socket ad un Data Stream
        //Le Data Stream sono tipi di stream più avanzate ed astratte che mi facilitano il lavoro permettendomi di inviare dati nei tipi predisposti da Java (int,bool,string ecc.)
        //è anche possibile non usare questa classe, la comunicazione dovrebbe venire però byte per byte, e la gestione di questi dati primitivi risulterebbe complessa e superflua per il nostro compito
        this.StreamOut = new DataOutputStream(DataSocket.getOutputStream());
        this.StreamIn = new DataInputStream(DataSocket.getInputStream());
	}

    public Client(){
    }

    public void Send_Message(String Data) throws Exception{
        //Invio il messaggio con il prefisso TXT perchè ho scelto di impostare così la comiuncazione
        OutData("TXT " + Data);
        Util.Log("TXT " + Data);
        OutData("exit");
    }

    public void Set_NickName(String NickName) throws Exception{
        //Set è per impostare un nickname
        OutData("SET " + NickName);
        OutData("exit");
    }
    @FunctionalInterface
    public interface MessageCallback{
        //Semplice interface per implementare una callback
        public void onMessageReceived(String Data);
    }

    public String Wait_For_Messages(MessageCallback callback){
        //Il client qui non fa nient altro che mettersi in ascolto per messaggi
        while (true){
            //Ciclo infinito poichè lo dobrà fare fino a che il porgramm non sarà chiuso
            try {
                Util.Log("Waiting For messages:");
                //Richiamo semplicemente la funzione di input dal socket
                String Data = InData();
                //Se nulla ritorno un messaggio vuoto per non incorrere in runtime excpetion causate da valori nulli
                if (Data == null){
                    return "";
                }
                //qui richiamo il callback, così che la funzione chiamante(che sarà un thread in esecuzione paralle) potrà decidere cosa fare del messaggio
                callback.onMessageReceived(Data);
            } catch (Exception e) {
                Util.Log(e);
            }
        }
    }
    //Funzione che si occupa di cambiare host
    public void Change_Host (String Host, int Port) throws Exception{
        //Se il datasocket è nullo evito di chiudere il socket(sarebbe inutile)
        if (DataSocket != null)
            CloseClient();
        //richiamo il costruttore
        DataSocket = new Socket(Host,Port);
        //Se il socket è ancora nullo significa
        if (DataSocket == null)
            throw new Exception("Impossibile connettersi, verificare i parametri di connessione");
        //Associo gli stream
        this.StreamOut = new DataOutputStream(DataSocket.getOutputStream());
        this.StreamIn = new DataInputStream(DataSocket.getInputStream());
    }

    public static void main(){
        try {
            Client MyClient = new Client();
            Render Application = new Render(MyClient);
            WaitThread Wait_Messages = new WaitThread(MyClient,Application);
            Wait_Messages.start();
        } catch (Exception e) {
            Util.Log(e);
        }
    }
}