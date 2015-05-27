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
    private RandomAI randomCpuPlayer;
    private boolean additionalTurn = false;
    private int difficulty = 0;
    private IWinnerCallback observer;
    private int rows;
    private int columns;
    private IPlayer localPlayer;    
    private LoadGamePlayer loadGamePlayer;
    private StorageGame gameSaver;

    
    
    
    public GameController(IPlayer p1, IPlayer p2, ArrayList boxes, int type, int rows, int columns) {
        
        localPlayer = p1;
        this.rows = rows;
        this.columns = columns;
        
        boxList = boxes;
        setGameType(type);
        
        
        for(int i = 0; i < boxList.size(); i++){
            boxList.get(i).setObserver(this);
        } 

        if(type == 3) {
            type = loadGamePlayer.getGameType();
            if(loadGamePlayer.getStartPlayerID() == 1) {
                this.changePlayer();
            }
        }
        gameSaver = new StorageGame(type, rows, columns,playerModels[0].getVictories(), playerModels[1].getVictories());
        
    }
    
    
    public void setGameType(int gameType) {
        switch (gameType) {
            case 0: //local game
                players[0] = localPlayer;
                players[1] = localPlayer;
                playerModels[0] = new Player(Color.BLUE);
                playerModels[1] = new Player(Color.RED);
                break;
            case 1: //network game
                break;
            case 2: //computer game
                cpuPlayer = new AI(Color.RED, boxList, rows, columns);
                players[0] = localPlayer;
                players[1] = cpuPlayer;
                playerModels[0] = new Player(Color.BLUE);
                playerModels[1] = cpuPlayer;
                break;
            case 3:
                loadGamePlayer = new LoadGamePlayer();
                players[0] = loadGamePlayer;
                players[1] = loadGamePlayer;
                playerModels[0] = new Player(Color.BLUE);
                playerModels[0].setVictories(loadGamePlayer.getVictoriesP1());
                playerModels[1] = new Player(Color.RED);
                playerModels[1].setVictories(loadGamePlayer.getVictoriesP2());
                break;
            case 4: //computer random game
                randomCpuPlayer = new RandomAI(Color.RED, boxList, rows, columns);
                players[0] = localPlayer;
                players[1] = randomCpuPlayer;
                playerModels[0] = new Player(Color.BLUE);
                playerModels[1] = randomCpuPlayer;
                break;
            
        }
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
            if(line == -2) {
                // game load successful, change Playermodels to continue playing
                int victoryPlayer0 = playerModels[0].getVictories();
                int victoryPlayer1 = playerModels[1].getVictories();
                ArrayList<Integer> ownBoxesPlayer0 = playerModels[0].getBoxes();
                ArrayList<Integer> ownBoxesPlayer1 = playerModels[1].getBoxes();
                
                this.setGameType(loadGamePlayer.getGameType());
                
                playerModels[0].setVictories(victoryPlayer0);
                playerModels[0].setVictories(victoryPlayer1);
                for(Integer box : ownBoxesPlayer0) {
                    playerModels[0].addBox(box);
                }
                for(Integer box : ownBoxesPlayer1) {
                    playerModels[1].addBox(box);
                }
                
            }
            else if(line != -1){
                gameSaver.saveTurn(playerIndex, line, playerModels[playerIndex].getColor());
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
            observer.winnerIs("Player " + (playerIndex+1) + " wins!!");
            resetGame();
        }else if ((((boxList.size() % 2) == 0)) && (playerModels[0].getBoxes().size() == playerModels[1].getBoxes().size()) 
                && ((playerModels[0].getBoxes().size() == (boxList.size()/2)))){
            System.out.println("No winner! DRAW! "+ (boxList.size() % 2));
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
    
    public void setDifficulty(int diff){
        this.difficulty = diff;
    }
    
    void addWinnerObserver(GPanel aThis) {
        observer = aThis;
    }
    
}
