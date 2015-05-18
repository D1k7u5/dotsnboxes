/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

import java.awt.Color;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tom
 */
public class GameController implements IBoxObserver, Runnable{

    private ArrayList<Box> boxList;
    private IPlayer players[] = new IPlayer[2];
    private Player playerModels[] = new Player[2];
    private int playerIndex;
    
    public GameController(IPlayer p1, IPlayer p2, ArrayList boxes) {
        
        
        players[0] = p1;
        players[1] = p2;
        playerModels[0] = new Player(Color.BLUE);
        playerModels[1] = new Player(Color.RED);
        boxList = boxes;
        for(int i = 0; i < boxList.size(); i++){
            boxList.get(i).setObserver(this);
        }
        
    }
    
    
    @Override
    public void boxIsFull(int id) {
        System.out.println("\n Box "+id+" is full and owned by Player "+(playerIndex+1));
    }
    
    private void changePlayer(){
        if (playerIndex == 0){
            playerIndex = 1;
        }else{
            playerIndex = 0;
        }
    }

    @Override
    public void run() {
        while(true){
            int line = players[playerIndex].getTurn();
            if(line != -1){
                for (int i = 0;i < boxList.size(); i++){
                    boxList.get(i).setLine(line, playerModels[playerIndex].getColor());
                }
                changePlayer();
            }
            try {
                sleep(80);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
