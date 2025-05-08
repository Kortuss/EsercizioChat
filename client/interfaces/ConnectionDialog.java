package client.interfaces;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConnectionDialog {
    JPanel HostPanel;
    JTextField Host,Port;
    JLabel HostLabel,PortLabel;
    public ConnectionDialog(){
        HostPanel = new JPanel();
        Host = new JTextField(10);
        Port = new JTextField(10);
        HostLabel = new JLabel("Host:");
        PortLabel = new JLabel("Porta:");
        HostPanel.add(HostLabel);
        HostPanel.add(Host);
        HostPanel.add(PortLabel);
        HostPanel.add(Port);
    }

    public String GetHostValue(){
        return Host.getText();
    }

    public String GetPortValue(){
        return Port.getText();
    }

    public int Render (JFrame frame){
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
