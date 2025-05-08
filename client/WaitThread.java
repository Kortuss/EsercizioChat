package client;

import client.interfaces.Render;

public class WaitThread extends Thread{
    Client MyClient;
    Render render;
    
    public WaitThread(Client MyClient, Render render) {
        this.MyClient = MyClient;
        this.render = render;
    }

    public void Handle_Messages(String Message){
        //Chiamo la funzione per renderizzare il messaggio nel log della chat
        render.Render_Message(Message);
    }

    @Override
    public void run(){
        //Richiamo la funzione con la callback per gestire i messaggi in arrivo
        MyClient.Wait_For_Messages(Data -> Handle_Messages(Data));
    }
}
