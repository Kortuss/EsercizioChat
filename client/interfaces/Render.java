package client.interfaces;
import client.Client;
import java.awt.*;
import javax.swing.*;
import util.Util;
public class Render{
    JFrame frame = null;
    ChatField chatField = null;
    ChatLog ChatLog = null;
    Menu menu;
    private void  HandleFirstConnection(Client MyClient){
        ConnectionDialog firstConnection = new ConnectionDialog();
        while (true) {
            int result = firstConnection.Render(frame);
            if (result == 2 || result == -1)
                System.exit(0);
    
            String host = firstConnection.GetHostValue();
            String portStr = firstConnection.GetPortValue();
    
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
    }

    public Render(Client MyClient){
        frame = new JFrame("Software_Chat");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
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