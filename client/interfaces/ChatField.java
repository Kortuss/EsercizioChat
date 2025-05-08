package client.interfaces;
import client.Client;
import java.awt.*;
import javax.swing.*;
import util.Util;
//Classe che si occupa di gestire il textfield e bottone per l'invio dei messaggi
public class ChatField {
    JPanel TextFieldPanel = null;
    JTextField MainField = null;
    JLabel FieldTitle = null;
    JButton SendButton = null;
    Font FieldFont = null;
    public ChatField(String Title,String Button){
        //Dichiaro staticamente tutti i componenti ed il contenitore
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
        //Gestisco l'evento del click sul bottone send
        SendButton.addActionListener(_ -> {
            try {
                //se nel field non è presente un valore non faccio nulla
                if (MainField.getText().isEmpty())
                    return;
                //altrimenti richiamo la funzione che si occuopa di mandare il messaggio al server
                client.Send_Message(MainField.getText());
                //resetto il valore del field della chat
                MainField.setText("");
            } catch (Exception e) {
                Util.Log(e);
            }
        });
    }
    
    public void Render(JFrame MainFrame){
        //renderizzo il contenitore e lo posizione a sud(sotto a tutto)
        MainFrame.add(TextFieldPanel,BorderLayout.SOUTH);
    }
}