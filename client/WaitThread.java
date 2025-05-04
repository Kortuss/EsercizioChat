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
        render.Render_Message(Message);
    }

    @Override
    public void run(){
        MyClient.Wait_For_Messages(Data -> Handle_Messages(Data));
    }
}
