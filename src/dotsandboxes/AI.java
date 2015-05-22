/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

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
    private BoxNeighbours boxNeighbours;
    

    public AI(Color color, ArrayList<Box> boxlist, int row, int col){
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
    
    private boolean setFourthLine(){
        boolean foundLine = false;
        Box box = box3Line.get(0);
            if(box.getLineN().getColor()== Color.LIGHT_GRAY){
                selectedLine = box.getLineN().getId();
                foundLine = true;
            }
            else if(box.getLineE().getColor()== Color.LIGHT_GRAY){
                selectedLine = box.getLineE().getId();
                foundLine = true;
            }
            else if(box.getLineS().getColor()== Color.LIGHT_GRAY){
                selectedLine = box.getLineS().getId();
                foundLine = true;
            }
            else if(box.getLineW().getColor() == Color.LIGHT_GRAY){
                selectedLine = box.getLineW().getId();
                foundLine = true;
            }
            return foundLine;
    }
    
    private boolean setFirstLine(){
        //BoxC = CenterBox  boxLoop = soll Nachbarsbox aus boxList finden
        boolean foundLine = false;
        Box boxC;
        int edgeLine = -1;
        int lineNeighbourWith0 = -1;
        int lineNeighbourWith1 = -1;
        int lineNeighbourWith2 = -1;
        
        //Durchlaufe Array von Boxen mit einer Linie
        for(int i =0; i<= box0Line.size();i++){
            boxC = box0Line.get(i);
            //Prüfe Linie im Norden & Prüfe Nachbar wenn Linie frei
            
            NeighbourValues north = this.secondLineN(boxC);
            NeighbourValues east = this.secondLineE(boxC);
            NeighbourValues south = this.secondLineS(boxC);
            NeighbourValues west = this.secondLineW(boxC);
            
            switch(north.numbersOfLines){
                case 0: lineNeighbourWith0 = north.lineID;
                        foundLine = true;
                        break;
                
                case 1: lineNeighbourWith1 = north.lineID;
                        foundLine= true;
                        break;
                    
                case 2: lineNeighbourWith2 = north.lineID;
                        break;
                    
                case 5: edgeLine = north.lineID;
                        selectedLine = edgeLine;
                        foundLine = true;
                        return foundLine;
                    
                default: break;     
            }
            if(edgeLine!=-1){
                i=1000;
            }
        }
        if(lineNeighbourWith1 != -1){
            selectedLine = lineNeighbourWith1;
        }
        else if(lineNeighbourWith0 != -1){
            selectedLine = lineNeighbourWith0;
        }
        
        return foundLine;
    }
    
    private boolean setSecondLine(){
        //BoxC = CenterBox  boxLoop = soll Nachbarsbox aus boxList finden
        boolean foundLine = false;
        Box boxC;
        int edgeLine = -1;
        int lineNeighbourWith0 = -1;
        int lineNeighbourWith1 = -1;
        int lineNeighbourWith2 = -1;
        
        //Durchlaufe Array von Boxen mit einer Linie
        for(int i =0; i<= box1Line.size();i++){
            boxC = box1Line.get(i);
            //Prüfe Linie im Norden & Prüfe Nachbar wenn Linie frei
            
            NeighbourValues north = this.secondLineN(boxC);
            NeighbourValues east = this.secondLineE(boxC);
            NeighbourValues south = this.secondLineS(boxC);
            NeighbourValues west = this.secondLineW(boxC);
            
            switch(north.numbersOfLines){
                case 0: lineNeighbourWith0 = north.lineID;
                        foundLine = true;
                        break;
                
                case 1: lineNeighbourWith1 = north.lineID;
                        foundLine= true;
                        break;
                    
                case 2: lineNeighbourWith2 = north.lineID;
                        break;
                    
                case 5: edgeLine = north.lineID;
                        selectedLine = edgeLine;
                        foundLine = true;
                        return foundLine;
                    
                default: break;     
            }
            switch(east.numbersOfLines){
                case 0: lineNeighbourWith0 = east.lineID;
                        foundLine = true;
                        break;
                
                case 1: lineNeighbourWith1 = east.lineID;
                        foundLine= true;
                        break;
                    
                case 2: lineNeighbourWith2 = east.lineID;
                        break;
                    
                case 5: edgeLine = east.lineID;
                        selectedLine = edgeLine;
                        foundLine = true;
                        return foundLine;
                    
                default: break;     
            }
            
            switch(south.numbersOfLines){
                case 0: lineNeighbourWith0 = south.lineID;
                        foundLine = true;
                        break;
                
                case 1: lineNeighbourWith1 = south.lineID;
                        foundLine= true;
                        break;
                    
                case 2: lineNeighbourWith2 = south.lineID;
                        break;
                    
                case 5: edgeLine = south.lineID;
                        selectedLine = edgeLine;
                        foundLine = true;
                        return foundLine;
                    
                default: break;     
            }
            
            switch(west.numbersOfLines){
                case 0: lineNeighbourWith0 = west.lineID;
                        foundLine = true;
                        break;
                
                case 1: lineNeighbourWith1 = west.lineID;
                        foundLine= true;
                        break;
                    
                case 2: lineNeighbourWith2 = west.lineID;
                        break;
                    
                case 5: edgeLine = west.lineID;
                        selectedLine = edgeLine;
                        foundLine = true;
                        return foundLine;
                    
                default: break;     
            }
        }
        if(lineNeighbourWith1 != -1){
            selectedLine = lineNeighbourWith1;
        }
        else if(lineNeighbourWith0 != -1){
            selectedLine = lineNeighbourWith0;
        }
        
        return foundLine;
    }
    
    
    private NeighbourValues secondLineN(Box boxC){
        if(boxC.getLineN().getColor()== Color.LIGHT_GRAY){
                int boxNid = boxNeighbours.getBoxN(boxC.getId());
                
                if(boxNid == -1){
                   selectedLine = boxC.getLineN().getId();
                   return new NeighbourValues(boxC.getLineN().getId(), 5);
                }
                else{
                    //Suche boxN
                    for(Box boxLoop : boxList){
                        //Prüfe ID
                        if(boxLoop.getId()== boxNid){
                            //Prüfe anzahl Linien
                            if(boxLoop.getNumberOfSetedLines()==1){
                                return new NeighbourValues(boxC.getLineN().getId(),1);
                            }
                            else if(boxLoop.getNumberOfSetedLines()==0){
                                return new NeighbourValues(boxC.getLineN().getId(),0);
                            }
                        }
                    }
                }
            }
    return new NeighbourValues(-1,-1);        
    }
    
    private NeighbourValues secondLineE(Box boxC){
        if(boxC.getLineE().getColor()== Color.LIGHT_GRAY){
                int boxEid = boxNeighbours.getBoxE(boxC.getId());
                
                if(boxEid == -1){
                   selectedLine = boxC.getLineE().getId();
                   return new NeighbourValues(boxC.getLineE().getId(), 5);
                }
                else{
                    //Suche boxN
                    for(Box boxLoop : boxList){
                        //Prüfe ID
                        if(boxLoop.getId()== boxEid){
                            //Prüfe anzahl Linien
                            if(boxLoop.getNumberOfSetedLines()==1){
                                return new NeighbourValues(boxC.getLineE().getId(),1);
                            }
                            else if(boxLoop.getNumberOfSetedLines()==0){
                                return new NeighbourValues(boxC.getLineE().getId(),0);
                            }
                        }
                    }
                }
            }
    return new NeighbourValues(-1,-1);        
    }
    
    private NeighbourValues secondLineS(Box boxC){
        if(boxC.getLineS().getColor()== Color.LIGHT_GRAY){
                int boxSid = boxNeighbours.getBoxS(boxC.getId());
                
                if(boxSid == -1){
                   selectedLine = boxC.getLineS().getId();
                   return new NeighbourValues(boxC.getLineS().getId(), 5);
                }
                else{
                    //Suche boxN
                    for(Box boxLoop : boxList){
                        //Prüfe ID
                        if(boxLoop.getId()== boxSid){
                            //Prüfe anzahl Linien
                            if(boxLoop.getNumberOfSetedLines()==1){
                                return new NeighbourValues(boxC.getLineS().getId(),1);
                            }
                            else if(boxLoop.getNumberOfSetedLines()==0){
                                return new NeighbourValues(boxC.getLineS().getId(),0);
                            }
                        }
                    }
                }
            }
    return new NeighbourValues(-1,-1);        
    }
    
    private NeighbourValues secondLineW(Box boxC){
        if(boxC.getLineW().getColor()== Color.LIGHT_GRAY){
                int boxWid = boxNeighbours.getBoxW(boxC.getId());
                
                if(boxWid == -1){
                   selectedLine = boxC.getLineW().getId();
                   return new NeighbourValues(boxC.getLineW().getId(), 5);
                }
                else{
                    //Suche boxN
                    for(Box boxLoop : boxList){
                        //Prüfe ID
                        if(boxLoop.getId()== boxWid){
                            //Prüfe anzahl Linien
                            if(boxLoop.getNumberOfSetedLines()==1){
                                return new NeighbourValues(boxC.getLineW().getId(),1);
                            }
                            else if(boxLoop.getNumberOfSetedLines()==0){
                                return new NeighbourValues(boxC.getLineW().getId(),0);
                            }
                        }
                    }
                }
            }
    return new NeighbourValues(-1,-1);        
    }
    
    private void setThirdLine(){

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
    
    
    private void setAITurn(){
        if(setFourthLine()){}
        else if(setSecondLine()){}
        else if(setFirstLine()){}
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
