/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author davidheer
 */
public class AI extends Player implements IPlayer {
    
    private ArrayList<Box> box0Line = new ArrayList<>();
    private ArrayList<Box> box1Line = new ArrayList<>();
    private ArrayList<Box> box2Line = new ArrayList<>();
    private ArrayList<Box> box3Line = new ArrayList<>();
    private ArrayList<ArrayList> twoLineTube = new ArrayList<>(); //Zusammenhängende Boxen mit zwei Linien, zu offenen Linien
    private ArrayList<Box> boxList;
    private int selectedLine = 0;
    private final int row;
    private final int col;
    

    public AI(Color color, ArrayList<Box> boxlist, int row, int col){
        super(color);
        this.boxList = boxlist;
        this.row = row;
        this.col = col;
    }

    /**
     * Sortiert Boxen nach anzahl Linien in ArrayLists
     */
    private void sortBoxes(){
        for(Box box : boxList){
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
    
    
    private void setTurn(){
        boolean impossibleBox = true;
        while(impossibleBox){
            int chooseBox = (int) Math.random()*(row*col+1);
        }
    }
    
}
