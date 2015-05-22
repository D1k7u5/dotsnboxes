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
    
    public DandBServer(){
        try {
            sSocket = new ServerSocket(13);
        } catch (IOException ex) {
            Logger.getLogger(DandBServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connectClient(){
        try {
            client = sSocket.accept();
            new Thread(this).start();
        } catch (IOException ex) {
            Logger.getLogger(DandBServer.class.getName()).log(Level.SEVERE, null, ex);
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
    public void act() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getTurn() {
        int selectedLine = data;
        data = -1;
        return selectedLine;
    }
}