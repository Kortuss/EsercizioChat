import java.awt.*;
import javax.swing.*;
public class Render{
       // Main function
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Software_Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);
        // Set layout for the main frame
        frame.setLayout(new BorderLayout());
        ChatField ChatField = new ChatField("Messaggio:","Invia");
        ChatLog ChatLog = new ChatLog();
        ChatField.HandleEvent(frame,ChatLog);
        ChatField.Render(frame);
        ChatLog.Render(frame);
        frame.setVisible(true);
    }
}