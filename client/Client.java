// Scrittura di una classe Client che si occupa di Connettersi ad un server e gestire la comunicazione con esso
//Autore: Hiras Picano V B INF 
//Compito assegnato il 21/03/25 per il 24/03/25
package client;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import util.*;
public class Client
{
    public String Nickname = "";
    //Dichiaro il socket che si connetterà al server
    private Socket DataSocket = null;
    //Dichiaro gli stream
    private DataInputStream StreamIn = null;
    private DataOutputStream StreamOut = null;
    
    public void CloseClient() throws Exception {
        Util.Log("Operazioni concluse\nChiusura connessione");
        //Mi ricordo di chiudere sia gli Stream che il Socket
        StreamIn.close();
        StreamOut.close();
        DataSocket.close();
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
        String m = "";
        //Non conoscendo quante righe di dati invierà il server, uso un ciclo while che evenutalmente si interrompà in caso di chiusura inaspettata del socket
        while (DataSocket.isConnected()){
            try{
                //leggo ogni riga
                m = StreamIn.readUTF();
                //Controllo se la riga corrisponde al segnale di fine trasmissione scelto da entrambi, in questo caso ho scelto exit
                if (m.equals("exit\n")){
                    Util.Log("Segnale di fine trasmissione ricevuto");
                    break;
                }
                //Stampo il messaggio ricevuto
                Util.Log("Dati ricevuti Dal Server:\n" + m);
            }
            catch (EOFException err) {
                Util.Log("Fine Stream dati inaspettato\nErrore di conessione o il segnale di fine trasmissione non e' stata inviata\n");
                break;
            }
            catch (IOException err) {
                Util.Log(err);
            }
        }
        return m;
    }

    public Client(int Port, String Address) throws Exception {
        this.DataSocket = new Socket(Address,Port);    
        //queste operazioni mi permettono di associare le stream di input e output provienient al socket ad un Data Stream
        //Le Data Stream sono tipi di stream più avanzate ed astratte che mi facilitano il lavoro permettendomi di inviare dati nei tipi predisposti da Java (int,bool,string ecc.)
        //è anche possibile non usare questa classe, la comunicazione dovrebbe venire però byte per byte, e la gestione di questi dati primitivi risulterebbe complessa e superflua per il nostro compito
        this.StreamOut = new DataOutputStream(DataSocket.getOutputStream());
        this.StreamIn = new DataInputStream(DataSocket.getInputStream());
	}

    public void Handle_User_Interaction(){

    }

    @SuppressWarnings("ConvertToTryWithResources")
	public static void main(String[] args){
        Scanner in = new Scanner(System.in);
		try{
            //Creo un'istanza del client, alla porta 3009 e in locale (127.0.0.1)
            Client MyClient = new Client(3009, "localhost");
            Util.Log("Connessione stabilita");
            int choice = 1;
            while (choice != 2) { 
                choice = in.nextInt();
                if (choice == 0){
                    Util.Log("Inviando un sms prova");
                    MyClient.OutData("TXT Ciao Raga, questo è un test");
                    MyClient.OutData("exit");
                    String data = MyClient.InData();
                    Util.Log(data);
                }
                else if (choice == 1){
                    Util.Log("Imposta un Nickname");
                    in.nextLine();
                    String NickName = in.nextLine();
                    MyClient.OutData("SET " + NickName);
                    MyClient.OutData("exit");
                }
                else if (choice == 2){
                    String Data = MyClient.InData();
                    Util.Log(Data);
                }
            }

            MyClient.InData();
            //Chiudo connessione e stream
            MyClient.CloseClient();
            //Chiudo lo scanner per evitare eventuali leak di memoria
        }
        catch (Exception err){
            //nel caso di errore di qualsiasi metodo, stampo esso
            Util.Log(err);

            //L'errore java.net.ConnectException: Connection refused: connect indica che il server non è attivo, accertarsi che esso sia stato correttamente avviato
        }
       in.close();
	}
}