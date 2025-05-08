package client.interfaces;
import client.Client;
import java.awt.*;
import javax.swing.*;
import util.Util;
//Classe principale che si occuperà di gestire tutta l'interfaccia dell'applicazione
public class Render{
    //Il frame è l'elemento portante dell'interfaccia, è in breve la finestra windows
    JFrame frame = null;
    ChatField chatField = null;
    ChatLog ChatLog = null;
    Menu menu;
    //funzione che si occupa di gestire il dialog atto a raccoglire i parametri riguardanti la prima connessione al server
    private void  HandleFirstConnection(Client MyClient){
        //Richiamo il dialog definito nella classe ConnectionDialog
        ConnectionDialog firstConnection = new ConnectionDialog();
        while (true) {
            //ciclo infinito, il dialog risulterà bloccante finche non ci si connetterà ad un server
            /*
             * I dialog ritornano questi valori se:
             * -1 se l'utente ha cliccato sulla x del dialog
             * 0 se l'utente ha cliccato su Conferma
             * 2 se l'utente ha cliccato cancel
             */
            int result = firstConnection.Render(frame);
            //se l'utente ha cliccato cancel o sulla x l'esecuzione del programma è interrota
            if (result == 2 || result == -1)
                System.exit(0);
            //recuperiamo i valori dai textfield
            String host = firstConnection.GetHostValue();
            String portStr = firstConnection.GetPortValue();
            //Controlliamo se siano vuoti, altrimenti il dialog sarà nuovamente mostrato
            if (host.isEmpty() || portStr.isEmpty()) {
                //Mostriamo un message dialog per spiegare all'utente cos'ha sbagliato
                JOptionPane.showMessageDialog(frame, "Host e porta non possono essere vuoti.");
                continue;
            }
            try {
                //se il valore non è vuoto proviamo a connetterci
                MyClient.Change_Host(host, Integer.parseInt(portStr));
            } catch (Exception e) {
                //probabilmente la connessione non è riuscita, il dialog è di nuovo mostrato dopo aver stampato a schermo dell'utente l'errore
                JOptionPane.showMessageDialog(frame, "ERRORE NELLA CONNESSIONE: " + e.getMessage());
                Util.Log(e);
                continue;
            }
            //se il socket è connesso usciamo dal while
            if (MyClient.isConnected())
                return;
            JOptionPane.showMessageDialog(frame, "Connessione fallita. Riprova.");
        }
    }

    public Render(Client MyClient){
        //Inizializziamo il frame e gli diamo un titolo
        frame = new JFrame("Software_Chat");
        //Impostiamo una risoluzione
        frame.setSize(800, 600);
        //Impostiamo che in caso l'utente clicchi sulla X della finestra il programma fermi la propria esecuzione
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Impostiamo che l'utente non può ridimensionare la finestra,
        //così da non dover definire dei layout responsivi in base alla risoluzione
        frame.setResizable(false);
        //Impostiamo come la finestra gestisce gli elementi al suo interno
        frame.setLayout(new BorderLayout());
        /*
         * Richiamo di tutti i componenti che gestiscono l'interfaccia
         */
        chatField = new ChatField("Messaggio:","Invia");
        ChatLog = new ChatLog();
        menu = new Menu();
        chatField.HandleEvent(frame,ChatLog,MyClient);
        menu.HandleEvent(frame,MyClient);
        chatField.Render(frame);
        ChatLog.Render(frame);
        menu.render(frame);
        frame.setVisible(true);
        HandleFirstConnection(MyClient);
    }
       
    public void Render_Message (String Message){
        ChatLog.Display_Data(Message);
    }
}