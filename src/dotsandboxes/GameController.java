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
    private AI cpuPlayer;
    private boolean additionalTurn = false;
    private StorageGame gameSaver;
    private IWinnerCallback observer;
    
    
    public GameController(IPlayer p1, IPlayer p2, ArrayList boxes, int type, int rows, int columns) {
       
                switch (type) {
            case 0: //local game
                players[0] = p1;
                players[1] = p2;
                playerModels[0] = new Player(Color.BLUE);
                playerModels[1] = new Player(Color.RED);
                break;
            case 1: //network game
                break;
            case 2: //computer game
                cpuPlayer = new AI(Color.GREEN, boxes, rows, columns);
                players[0] = p1;
                players[1] = cpuPlayer;
                playerModels[0] = new Player(Color.BLUE);
                playerModels[1] = cpuPlayer;

                break;

        }

        boxList = boxes;
        for(int i = 0; i < boxList.size(); i++){
            boxList.get(i).setObserver(this);
        } 
        
        gameSaver = new StorageGame(type, rows, columns);
        
    }
    
    public Player getPlayer(int index){
        return playerModels[index];
    }
    
    @Override
    public void boxIsFull(int id) {
        additionalTurn = true;
        playerModels[playerIndex].addBox(id);
    }
    
    private void changePlayer(){
        System.out.println("player1 owns "+playerModels[0].getBoxes().size()+" boxes");
        System.out.println("player2 owns "+playerModels[1].getBoxes().size()+" boxes");
        if (playerIndex == 0){
            playerIndex = 1;
        }else{
            playerIndex = 0;
        }
    }

    @Override
    public void run() {
        while(true){
            checkForAWinner();
            int line = players[playerIndex].getTurn();
            if(line != -1){
                gameSaver.SaveTurn(playerIndex, line, playerModels[playerIndex].getColor());
                for (int i = 0;i < boxList.size(); i++){
                    boxList.get(i).setLine(line, playerModels[playerIndex].getColor());
                }
                if(!additionalTurn){
                    changePlayer();
                }
                additionalTurn = false;
            }
            try {
                sleep(80);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void checkForAWinner() {
        try{
        if ((boxList.size() / 2) < (playerModels[playerIndex].getBoxes().size())){
            System.out.println("Player "+(playerIndex+1)+" wins!!");
            playerModels[playerIndex].addVictory();
            observer.winnerIs("Player "+(playerIndex+1)+" wins!!");
            resetGame();
        }else if ((playerModels[0].getBoxes().size() == playerModels[1].getBoxes().size()) 
                && (playerModels[0].getBoxes().size() == (boxList.size()/2))){
            System.out.println("No winner! DRAW!");
            observer.winnerIs("No winner! DRAW!");
            resetGame();
        }
        }catch(Exception e){
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void resetGame() {
        for (int i = 0;i < boxList.size();i++){
            boxList.get(i).reset();
        }
        playerModels[0].reset();
        playerModels[1].reset();
        
        gameSaver.reInitFile(playerModels[0].getVictories(), playerModels[1].getVictories());
    }

    void addWinnerObserver(GPanel aThis) {
        observer = aThis;
    }
    
}
