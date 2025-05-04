package client.interfaces;

import client.Client;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import util.Util;

public class Menu {
    JMenuBar menuBar;
    JMenuItem Set_Nick;
    JMenuItem Set_Host;
    public Menu(){
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menù");
        Set_Nick = new JMenuItem("Imposta un NickName");
        Set_Host = new JMenuItem("Impostazioni di Connessione");
        JMenuItem exitItem = new JMenuItem("Chiudi");
        fileMenu.add(Set_Nick);
        fileMenu.add(Set_Host);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
    }

   public void HandleEvent(JFrame frame,Client MyClient){
        Set_Nick.addActionListener(_ -> {
            String Value = "";
            while (Value.isEmpty()) {
                Value = JOptionPane.showInputDialog(frame,"Inserisci un NickName\n(il nome che apparirà all'invio dei messaggi)","Inserisci NickName",JOptionPane.PLAIN_MESSAGE);
                if (Value == null)
                    break;
            }
            try {
                MyClient.Set_NickName(Value);
            } catch (Exception e) {
                Util.Log(e);
            }
        });

        Set_Host.addActionListener(_ -> {
            JPanel HostPanel = new JPanel();
            JTextField Host = new JTextField(10);
            JTextField Port = new JTextField(10);
            JLabel HostLabel = new JLabel("Host:");
            JLabel PortLabel = new JLabel("Porta:");
            HostPanel.add(HostLabel);
            HostPanel.add(Host);
            HostPanel.add(PortLabel);
            HostPanel.add(Port);
            do{ 
                int result = JOptionPane.showConfirmDialog(
                    frame,
                    HostPanel,
                    "Inserisci Dati Connessione",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
                ); 
                if (result == 2)
                    return;
            }while(Host.getText().isEmpty() || Port.getText().isEmpty());
            try {
                MyClient.Change_Host(Host.getText(), Integer.parseInt(Port.getText()));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame,"ERRORE NELLA CONNESSIONE: " + e);
                Util.Log(e);
                try {
                    MyClient.Change_Host("localhost", 3009);
                } catch (Exception err) {
                    Util.Log(err);
                }
            }
        });
    }

    public void render(JFrame frame){
        frame.add(menuBar,BorderLayout.NORTH);
    }
}
