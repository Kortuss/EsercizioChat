package client.interfaces;
import client.Client;
import java.awt.*;
import javax.swing.*;
import util.Util;
public class ChatField {
    JPanel TextFieldPanel = null;
    JTextField MainField = null;
    JLabel FieldTitle = null;
    JButton SendButton = null;
    Font FieldFont = null;
    public ChatField(String Title,String Button){
        TextFieldPanel = new JPanel();
        MainField = new JTextField("",40);
        FieldTitle = new JLabel(Title);
        SendButton = new JButton(Button);
        FieldFont = FieldTitle.getFont().deriveFont(Font.PLAIN, 15f);
        FieldTitle.setFont(FieldFont);
        MainField.setFont(FieldFont);
        SendButton.setFont(FieldFont);
        TextFieldPanel.add(FieldTitle);
        TextFieldPanel.add(MainField);
        TextFieldPanel.add(SendButton);
    }

    public void HandleEvent(JFrame frame, ChatLog chatLog, Client client){
        SendButton.addActionListener(_ -> {
            try {
                client.Send_Message(MainField.getText());
                MainField.setText("");
            } catch (Exception e) {
                Util.Log(e);
            }
        });
    }
    
    public void Render(JFrame MainFrame){
        MainFrame.add(TextFieldPanel,BorderLayout.SOUTH);
    }
}