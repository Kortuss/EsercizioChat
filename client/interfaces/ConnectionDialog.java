package client.interfaces;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
//Classe che gestisce il dialog che si occupa di prendere in input i parametri riguardo la connessione verso il Server
public class ConnectionDialog {
    //Contenitore che contine i label(scritte) e Textfield
    JPanel HostPanel;
    JTextField Host,Port;
    JLabel HostLabel,PortLabel;
    public ConnectionDialog(){
        //Istanziamo tutti i componenti
        HostPanel = new JPanel();
        Host = new JTextField(10);
        Port = new JTextField(10);
        HostLabel = new JLabel("Host:");
        PortLabel = new JLabel("Porta:");
        //Aggiungiamo i componenti al contentitore
        HostPanel.add(HostLabel);
        HostPanel.add(Host);
        HostPanel.add(PortLabel);
        HostPanel.add(Port);
    }
    //restituisce il valore del textfield dell'host
    public String GetHostValue(){
        return Host.getText();
    }
    //restituisce il valore del textfield della porta
    public String GetPortValue(){
        return Port.getText();
    }

    public int Render (JFrame frame){
        //mostriamo il dialog con il pannello contenente scritte e fields
        int result = JOptionPane.showConfirmDialog(
            frame,
            HostPanel,
            "Inserisci Dati Connessione",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        ); 
        System.out.println(result);
        return result;
    }
    
}
