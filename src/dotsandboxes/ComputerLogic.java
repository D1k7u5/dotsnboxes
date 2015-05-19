/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

import java.util.ArrayList;

/**
 *
 * @author davidheer
 */
public class ComputerLogic implements IPlayer {
    
    ArrayList<Box> box0Line = new ArrayList<>();
    ArrayList<Box> box1Line = new ArrayList<>();
    ArrayList<Box> box2Line = new ArrayList<>();
    ArrayList<Box> box3Line = new ArrayList<>();
    ArrayList<ArrayList> twoLineTube = new ArrayList<>(); //Zusammenhängende Boxen mit zwei Linien, zu offenen Linien
    private int selectedLine = 0;

    public ComputerLogic(){
        
    }

    /**
     * Sortiert Boxen nach anzahl Linien in ArrayLists
     */
    private void sortBoxes(){
        for(Box box : box0Line){
            int setedLines;
            setedLines = box.getNumberOfSetedLines();
            
            if(setedLines == 0){box0Line.add(box);}
            if(setedLines == 1){box1Line.add(box);}
            if(setedLines == 2){box2Line.add(box);}
            if(setedLines == 3){box3Line.add(box);}
        }
    }
    
    private void check3LineBoxes(){
        
    }
    
    private void check0Lines(){
        
    }
    
    private void check1Line(){
        
    }
    
    /**
     * Prüfe Boxen mit zwei Linien und erstelle
     */
    private void check2Line(){
        /*
        
        for(Box box : box2Line){
            if(box.getLineN() == null){
                //Prüfe Box im Norden
            }
            if(box.getLineE() == null){
                twoLineTube.
                //Prüfe Box im Osten
            }
        }
        */
    }

    @Override
    public void act() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getTurn() {
        int result = selectedLine;
        selectedLine = -1;
        return result;
    }
    
}
