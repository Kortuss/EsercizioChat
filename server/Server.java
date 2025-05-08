// Scrittura di una classe Server che si occupa di gestire le connessioni in entrata e la comunicazione con i client+
//Autore: Hiras Picano V B INF
//Compito assegnato il 21/03/25 per il 24/03/25
// package declaration removed as it was incomplete
// package Server; // Commented out as it may not match the directory structure
package server;
import java.net.*;
import util.Util;

/*
 * TO DO:
 * Il software client deve potersi aprire senza essere connesso al server, far aprire il software e impostare un dialog bloccante, finche non ci si connette 
 * ad un server funzionante non si puo accedere all'app: ✅
 */

public class Server
{
    
    //Creazione degli attributi
	private ServerSocket ListeningPoint = null;
	private int Port = 3009;
    private Client_Connections connections = null;
    //Creazione dei metodi
	private void CreateListeningSocket() throws Exception{
        //qui definisco il ConnectionSocket che si metterà in ascolto sulla porta scelta da me: 3009
		this.ListeningPoint = new ServerSocket(Port);
	}

	private Socket AcceptConnection () throws Exception{
        //mi metto in attesa di un client
        //quando un client si connette definisco il DataSocket che si occuperà di gestire la comunicazione.
		Util.Log("Aspettando Client...");
		Socket CurrentSocket = this.ListeningPoint.accept();
		Util.Log("Client Accettato");
		return CurrentSocket;
	}

    public void MainLoop() throws Exception{
        //Questo è il ciclo principale del server
        //Gestisce le operazioni relative ad ogni client che si connette.
        //Il server rimane in ascolto per sempre, finchè non viene interrotto il processo.
        try{
            connections = new Client_Connections();
            while (!ListeningPoint.isClosed()){
                //Semplicemente richiamo i metodi
                Socket NewSocket = AcceptConnection();
                ClientThread thread = new ClientThread(NewSocket, connections);
                thread.start();
                connections.Push(thread);
            }
        }
        catch (Exception err){
            Util.Log(err);
        }
    }

    Server(){
        try{
            //Definisco il ConnectionSocket nel costruttore
			CreateListeningSocket();
		}
        catch (Exception err){
            Util.Log(err);
        }
	}

	public static void main(String args[]){
		Server MyServer = new Server();
        try{
            //Qui richiamo il main loop.
            //Ho adottato questa soluzione per rendere il codice più pulito e leggibile.
            //Per migliorare l'use-case di questa classe avrei potuto adottare delle callback function, per aggiungere Operazioni di i/o per ogni client.
            //Ovvio che questa soluzione sarebbe stata più complessa e avrebbe richiesto più tempo e svia dall'oggetto di questa consegna
            MyServer.MainLoop();
            //A differenza del server non è possibile chiudere il server in modo pulito, quindi il server rimarrà in ascolto per sempre.
            //Oppure può essere chiuso forzatamente con ctrl+c dal terminal.
        }
        catch (Exception err){
            //Gestisco eventuali errori
            Util.Log(err);
        }        
    }
}