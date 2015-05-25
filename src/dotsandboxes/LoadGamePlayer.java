/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author kevinbieri
 */
public class LoadGamePlayer implements IPlayer {
    
    private static File gameToLoad;
    private ArrayList<Integer>[] listOfTurns;
    private int numberOfTurns = 0;
    
    private int gameType;
    private int rows;
    private int columns;
    private int gamesP1Won;
    private int gamesP2Won;
    
    public LoadGamePlayer() {
        
        //LoadGamePlayer.gameToLoad = gameToLoad;
        listOfTurns = new ArrayList[2];
        listOfTurns[0] = new ArrayList();        
        listOfTurns[1] = new ArrayList();
        try {
        FileReader logfile = new FileReader(gameToLoad.getPath());
        BufferedReader bufferedReader = new BufferedReader(logfile);
        gameType = Integer.parseInt(bufferedReader.readLine().substring(10));
        rows = Integer.parseInt(bufferedReader.readLine().substring(6));
        columns = Integer.parseInt(bufferedReader.readLine().substring(9));
        gamesP1Won = Integer.parseInt(bufferedReader.readLine().substring(12));
        
        gamesP2Won = Integer.parseInt(bufferedReader.readLine().substring(12));
        
        String line;

        while (( line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(" ");
                
                listOfTurns[0].add(Integer.parseInt(tokens[1]));
                listOfTurns[1].add(Integer.parseInt(tokens[3]));
            //    numberOfTurns++;
                }
                
        }
    
        catch(IOException e) {
            
        }
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getColumns() {
        return columns;
    }
    
    public int getGameType() {
        return gameType;
    }
    
    public int getGamesP1Won () {
        return gamesP1Won;
    }
    
    public int getGamesP2Won () {
        return gamesP2Won;
    }

    public static void setFileToLoad(File gameToLoad) {
        LoadGamePlayer.gameToLoad = gameToLoad;
    }
    
    @Override
    public void act() { } // do Nothing

    @Override
    public int getTurn() {
        int turn;
        if(listOfTurns[1].isEmpty()) {
            turn = -2;
        }
        else{
            
            turn = listOfTurns[1].remove(0);
            //numberOfTurns++;
            
        }
        
        
        return turn;
        

    }
    
    
    
    
}
