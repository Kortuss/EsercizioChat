package client.interfaces;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
public class ChatLog {
    //dichiaro un contentitore che conterrà tutti i messaggi che sarà scrollabile tramite rotellina
    public JScrollPane ChatScrollPanel = null;
    //contenitore che contiene unicamente i messaggi
    public JPanel ChatPanel = null;
    //Dichiaro il colore dello sfondo del contenitore
    Color PanelColor = null;
    //Array contente i messaggi
    List<JLabel> Messages = null;
    //Font che voglio dare ai messaggi
    Font MainFont = null;

    public ChatLog() {
        //Istanzio gli oggetti
        ChatPanel = new JPanel();
        ChatScrollPanel = new JScrollPane();
        //Imposto che lo scroll del componente avverrà in verticale
        ChatScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //Ho scelto questo colore (verde chiaro)
        PanelColor = new Color(15858900);
        //Imposto il colore
        ChatPanel.setBackground(PanelColor);
        //Il tipo di layout, in questo caso un layout che incolonnerà tutti i label (tipo un box flex con flex direction: column[css])
        ChatPanel.setLayout(new BoxLayout(ChatPanel, BoxLayout.Y_AXIS));
        //do del padding ai messaggi, così non risultano attaccati in alto a destra
        ChatPanel.setBorder(new EmptyBorder(10, 20, 0 ,0));
        //istanzio l'array
        Messages = new ArrayList<>();   
        //Aggiungo il contenitore dei messaggi al contenitore scrollable  
        ChatScrollPanel.setViewportView(ChatPanel);  
    }
    //funzione che si occupa di aggiungere al pannello dei messaggi un nuovo messaggio e mostrarlo a schermo
    public void Display_Data(String Message){
        //dichiaramo un label che avrà come contenuto un messaggio
        JLabel message = new JLabel(Message);
        //Voglio che il messaggio appaia vicino al bordo sinistro
        message.setAlignmentX(Component.LEFT_ALIGNMENT);
        //Dichiaro il font, che in questo caso è derivato dal font classico solo con scritte più grandi
        MainFont = message.getFont().deriveFont(Font.PLAIN, 15f);
        //Imposto il font
        message.setFont(MainFont);
        Messages.add(message);
        //Lo aggiungo al contenitore
        ChatPanel.add(message,JPanel.TOP_ALIGNMENT);
        //do un gap tra i messaggi
        ChatPanel.add(Box.createVerticalStrut(10));
        //essendo un contenitore con contenuti dinamici dico di rivalidare e ridisegnare il pannello per aggiornare l'interfaccia
        ChatPanel.revalidate();
        ChatPanel.repaint();
        //stessa cosa per il pannello scrollabile
        ChatScrollPanel.revalidate();
        ChatScrollPanel.repaint();
    }

    public void Render(JFrame frame){
        //renderizzo il pannello scrollable
        frame.add(ChatScrollPanel,BorderLayout.CENTER);
    }
    
}
