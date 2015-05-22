/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;

/**
 *
 * @author kevinbieri
 */
public class StorageGame {
    
    private PrintWriter printWriter;
    private String pathLogFile = "./ressources/logFile";
//    private final FileWriter textFile;
//    private boolean fileOpen = true; 
    private Object FileUtils;
    private int rows;
    private int columns;
    private int gameType;
    
    public StorageGame(int gameType, int rows, int columns) {
        this.gameType = gameType;
        this.rows = rows;
        this.columns = columns;
        reInitFile(0, 0);
        
   
    }
    
    public void reInitFile(int gamesWonP1, int gamesWonP2) {
        
        try (FileWriter logfile = new FileWriter(pathLogFile))
        {   
            
            printWriter = new PrintWriter(logfile);
            printWriter.println("GameType: " + gameType);
            printWriter.println("Rows: " + rows);
            printWriter.println("Columns: " + columns);
            printWriter.println("GamesWonP1: " + gamesWonP1);
            printWriter.println("GamesWonP2: " + gamesWonP2);
            printWriter.close();
            
        } catch (IOException e) {
            System.out.printf(e.getMessage());
        }
        finally {
            if(printWriter != null) {
                printWriter.close();
            }
        }
    }
    
    public void SaveTurn(int PlayerID, int LineID, Color color) {
        
        try(FileWriter logfile = new FileWriter(pathLogFile, true)) {
            
        
        printWriter = new PrintWriter(logfile);
        printWriter.println("PID: " + PlayerID + " LID: " + LineID + " RGB: " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
        printWriter.close();
        }
         catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        finally {
            if(printWriter != null) {
                printWriter.close();
            }
        }
    
    }
    
    public void SaveGame(String destinationPath) {
        File logfile = new File(pathLogFile);
        File dest = new File(destinationPath);
        
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(logfile).getChannel();
            outputChannel = new FileInputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            inputChannel.close();
            outputChannel.close();
        }
        catch( IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        
        
    }

                
                
        
        
    
    
}
