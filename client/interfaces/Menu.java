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
    public Menu(){
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menù");
        Set_Nick = new JMenuItem("Imposta un NickName");
        JMenuItem exitItem = new JMenuItem("Chiudi");
        fileMenu.add(Set_Nick);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
    }

   public void HandleEvent(JFrame frame,Client MyClient){
        Set_Nick.addActionListener(_ -> {
            String Value = "";
            while (Value.equals("")) {
                Value = JOptionPane.showInputDialog(frame,"Inserisci un NickName\n(il nome che apparirà all'invio dei messaggi)");
            }
            try {
                MyClient.Set_NickName(Value);
            } catch (Exception e) {
                Util.Log(e);
            }
        });
    }

    public void render(JFrame frame){
        frame.add(menuBar,BorderLayout.NORTH);
    }
}
