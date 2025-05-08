package client.interfaces;

import client.Client;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
            String CurrentHost = MyClient.CurrentHost;
            int CurrentPort = MyClient.CurrentPort;
            System.out.println(CurrentHost + CurrentPort);
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
