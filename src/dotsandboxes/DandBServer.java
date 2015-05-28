/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Remo
 */
public class DandBServer implements Runnable,IPlayer {

    private Socket client;
    private ServerSocket sSocket;
    private int data;
    
    public DandBServer(int port){
        try {
            sSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(DandBServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean connectClient(){
        try {
            System.out.println("wait for client...");
            client = sSocket.accept();
            System.out.println("client accepted");
            new Thread(this).start();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(DandBServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    @Override
    public void run() {
        while(true){
            try(OutputStream out = client.getOutputStream();
                InputStream in = client.getInputStream()){
                data = in.read();
                while(data != -1){
                    out.write(data);
                    data = in.read();
                }
                out.flush();
            }catch(IOException e){
                System.err.println("Error: " + e.getMessage());
            }
        }
    }    

    @Override
    public int getTurn() {
        int selectedLine = data;
        data = -1;
        return selectedLine;
    }
}
