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
public class RandomAI extends Player implements IPlayer{
    private ArrayList<Box> box0Line = new ArrayList<>();
    private ArrayList<Box> box1Line = new ArrayList<>();
    private ArrayList<Box> box2Line = new ArrayList<>();
    private ArrayList<Box> box3Line = new ArrayList<>();
    private ArrayList<ArrayList> twoLineTube = new ArrayList<>(); //Zusammenhängende Boxen mit zwei Linien, zu offenen Linien
    private ArrayList<Box> boxList;
    private int selectedLine = 0;
    private final int row;
    private final int col;
    private final BoxNeighbours boxNeighbours;
    

    public RandomAI(Color color, ArrayList<Box> boxlist, int row, int col){
        super(color);
        this.boxList = boxlist;
        this.row = row;
        this.col = col;
        boxNeighbours = new BoxNeighbours(row,col);
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
    
    
    @Override
    public void act() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getTurn() {
        this.sortBoxes();
        this.setRandomTurn();
        int result = selectedLine;
        return result;
    }

    
    private void setRandomTurn(){
        boolean impossibleBox = true;
        while(impossibleBox){
            int chooseBox = (int) (Math.random()*(row*col)+1);
            for(Box box : boxList){
                if(box.getId() == chooseBox){
                    if(box.getLineN().getColor() == Color.LIGHT_GRAY){
                        selectedLine = box.getLineN().getId();
                        impossibleBox = false;
                    }
                    if(box.getLineE().getColor() == Color.LIGHT_GRAY){
                        selectedLine = box.getLineE().getId();
                        impossibleBox = false;
                    }
                    if(box.getLineS().getColor() == Color.LIGHT_GRAY){
                        selectedLine = box.getLineS().getId();
                        impossibleBox = false;
                    }
                    if(box.getLineW().getColor() == Color.LIGHT_GRAY){
                        selectedLine = box.getLineW().getId();
                        impossibleBox = false;
                    }
                }
            }
        }
        System.out.println("Zufällig ausgewählte Linie: " + selectedLine);
    }
}    
    
