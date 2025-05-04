package client.interfaces;
import client.Client;
import java.awt.*;
import javax.swing.*;
public class Render{
    JFrame frame = null;
    ChatField chatField = null;
    ChatLog ChatLog = null;
    public Render(Client MyClient){
        frame = new JFrame("Software_Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        chatField = new ChatField("Messaggio:","Invia");
        ChatLog = new ChatLog();
        chatField.HandleEvent(frame,ChatLog,MyClient);
        chatField.Render(frame);
        ChatLog.Render(frame);
        frame.setVisible(true);
    }

    public void Render_Message (String Message){
        ChatLog.Display_Data(Message);
    }
}