/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Remo
 */
public class DandBClientPlayer implements IPlayer,Runnable {
    private Socket server;
    private String host = "localhost";
    private int port = 13;
    
    DandBClientPlayer(){
        try {
            Socket server = new Socket(host,port);
        } catch (IOException ex) {
            Logger.getLogger(DandBClientPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void act() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getTurn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
