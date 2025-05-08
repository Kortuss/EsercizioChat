package client.interfaces;

import client.Client;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import util.Util;
//classe che si occupa di gestire il menu a tendina in alto a destra
public class Menu {
    //Il componente che svolgera da contenitore è il menuBar, che si occupa di contenere tutti i menu (nel nostro caso ne gestiamo un singolo)
    JMenuBar menuBar;
    //Definiamo qui gli item del menù
    JMenuItem Set_Nick;
    JMenuItem Set_Host;
    public Menu(){
        //Lo istanziamo
        menuBar = new JMenuBar();
        //Instanziamo qui il singolo menu
        JMenu optionMenu = new JMenu("Menù");
        //Istanziamo gli oggetti del menu
        Set_Nick = new JMenuItem("Imposta un NickName");
        Set_Host = new JMenuItem("Impostazioni di Connessione");
        //ed un tasto per chiuderlo
        JMenuItem exitItem = new JMenuItem("Chiudi");
        //aggiungiamo al menu singolo tutti gli oggetti
        optionMenu.add(Set_Nick);
        optionMenu.add(Set_Host);
        //Separatore tra le opzione e il chiudi menu
        optionMenu.addSeparator();
        optionMenu.add(exitItem);
        //aggiungiamo alla barra dei menu questo menu
        menuBar.add(optionMenu);
    }
    //funzione che si occupa di gestire gli eventi riguardo i menu, ovvero i click sui menuItem
   public void HandleEvent(JFrame frame,Client MyClient){
        //Mettiamo in ascolto di un click sull item Set_Nick
        Set_Nick.addActionListener(_ -> {
            String Value = "";
            while (Value.isEmpty()) {
                //eseguiamo il ciclo finchè l'utente non inserisce un valore valido, cioè non vuoto
                Value = JOptionPane.showInputDialog(frame,"Inserisci un NickName\n(il nome che apparirà all'invio dei messaggi)","Inserisci NickName",JOptionPane.PLAIN_MESSAGE);
                if (Value == null)
                    break;
            }
            try {
                //Impostiamo il nickname nella variabile del nostro client
                MyClient.Set_NickName(Value);
            } catch (Exception e) {
                Util.Log(e);
            }
        });
        //Mettiamo in ascolto di un click sull item Set_Host
        Set_Host.addActionListener(_ -> {
            //gestiamo il cambio di host da parte del client
            String CurrentHost = MyClient.CurrentHost;
            int CurrentPort = MyClient.CurrentPort;
            System.out.println(CurrentHost + CurrentPort);
            //Similmente a cosa suggente nella classe Render, tentiamo un cambio host, guardare commenti su Render per maggiori informazioni
            ConnectionDialog ChangeHost = new ConnectionDialog();
            while (true) {
                int result = ChangeHost.Render(frame);
                if (result == 2 || result == -1){
                   System.exit(0);
                }        
                String host = ChangeHost.GetHostValue();
                String portStr = ChangeHost.GetPortValue();
        
                if (host.isEmpty() || portStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Host e porta non possono essere vuoti.");
                    continue;
                }
        
                try {
                    MyClient.Change_Host(host, Integer.parseInt(portStr));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "ERRORE NELLA CONNESSIONE: " + e.getMessage());
                    Util.Log(e);
                    continue;
                }
        
                if (MyClient.isConnected())
                    return;
        
                JOptionPane.showMessageDialog(frame, "Connessione fallita. Riprova.");
            }
        });
    }

    public void render(JFrame frame){
        frame.add(menuBar,BorderLayout.NORTH);
    }
}
