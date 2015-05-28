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
    
    private ArrayList<Box> box0Line = new ArrayList<>(); //Noch leere Boxen
    private ArrayList<Box> box1Line = new ArrayList<>(); //Boxen mit einer markierten Line
    private ArrayList<Box> box2Line = new ArrayList<>(); //Boxen mit zwei markierten Lines
    private ArrayList<Box> box3Line = new ArrayList<>(); //Boxen mit drei markierten Lines
    private ArrayList<ArrayList> tubeList = new ArrayList<>(); //Zusammenhängende Boxen mit zwei Linien, zu offenen Linien
    private ArrayList<Box> boxList; //Enthält die Liste der Boxen des aktuellen Spieles
    private int selectedLine = 0; //Linie welche markiert werden soll
    private final int row; //Reihenanzahl des Spiels
    private final int col;//Kolonenanzahl des Spiels
    private BoxNeighbours boxNeighbours; //Errechnet die Nachbarn der Boxen
    
    /**
     * 
     * @param color Farbe des AI-Players
     * @param boxlist Liste der Boxen des aktuellen Spiels
     * @param row anz. Reihen des Spiels
     * @param col anz. Kolonen des Spiels
     */
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
    private void sortBoxes() {
        if (this.boxList != null) {
            for (Box box : boxList) {
                int setedLines;
                setedLines = box.getNumberOfSetedLines();

                if (setedLines == 0) {
                    box0Line.add(box);
                }
                if (setedLines == 1) {
                    box1Line.add(box);
                }
                if (setedLines == 2) {
                    box2Line.add(box);
                }
                if (setedLines == 3) {
                    box3Line.add(box);
                }
            }
        }
    }
    
    /**
     * Schliesse eine Box welche schon drei markierte Linien hatte
     * @return konnte eine Box gefüllt werden
     */
    private boolean setFourthLine() {
        boolean foundLine = false;
        if (!box3Line.isEmpty()) {
            Box box = box3Line.get(0);
            if (box.getLineN().getColor() == Color.LIGHT_GRAY) {
                selectedLine = box.getLineN().getId();
                foundLine = true;
            } else if (box.getLineE().getColor() == Color.LIGHT_GRAY) {
                selectedLine = box.getLineE().getId();
                foundLine = true;
            } else if (box.getLineS().getColor() == Color.LIGHT_GRAY) {
                selectedLine = box.getLineS().getId();
                foundLine = true;
            } else if (box.getLineW().getColor() == Color.LIGHT_GRAY) {
                selectedLine = box.getLineW().getId();
                foundLine = true;
            }
        }
        return foundLine;
    }
    
    /**
     * Ziehe den ersten Strich
     * @return Konnte erster Strich gezogen werden
     */
    private boolean setFirstLine(){
        //BoxC = CenterBox  boxLoop = soll Nachbarsbox aus boxList finden
        boolean foundLine = false;
        Box boxC;
        int edgeLine = -1;
        int lineNeighbourWith0 = -1;
        int lineNeighbourWith1 = -1;
        int lineNeighbourWith2 = -1;
        
        //Durchlaufe Array von Boxen mit einer Linie
        for(int i =0; i< box0Line.size();i++){
            boxC = box0Line.get(i);
            //Prüfe Linie im Norden & Prüfe Nachbar wenn Linie frei
            
            NeighbourValues north = this.checkLineN(boxC);
            NeighbourValues east = this.checkLineE(boxC);
            NeighbourValues south = this.checkLineS(boxC);
            NeighbourValues west = this.checkLineW(boxC);
            
            switch(north.getNumbersOfLines()){
                case 0: lineNeighbourWith0 = north.getLineID();
                        foundLine = true;
                        break;
                
                case 1: lineNeighbourWith1 = north.getLineID();
                        foundLine= true;
                        break;
                    
                case 2: lineNeighbourWith2 = north.getLineID();
                        break;
                    
                case 5: edgeLine = north.getLineID();
                        selectedLine = edgeLine;
                        foundLine = true;
                        return foundLine;
                    
                default: break;     
            }
            switch(east.getNumbersOfLines()){
                case 0: lineNeighbourWith0 = east.getLineID();
                        foundLine = true;
                        break;
                
                case 1: lineNeighbourWith1 = east.getLineID();
                        foundLine= true;
                        break;
                    
                case 2: lineNeighbourWith2 = east.getLineID();
                        break;
                    
                case 5: edgeLine = north.getLineID();
                        selectedLine = edgeLine;
                        foundLine = true;
                        return foundLine;
                    
                default: break;     
            }
            switch(south.getNumbersOfLines()){
                case 0: lineNeighbourWith0 = north.getLineID();
                        foundLine = true;
                        break;
                
                case 1: lineNeighbourWith1 = south.getLineID();
                        foundLine= true;
                        break;
                    
                case 2: lineNeighbourWith2 = south.getLineID();
                        break;
                    
                case 5: edgeLine = south.getLineID();
                        selectedLine = edgeLine;
                        foundLine = true;
                        return foundLine;
                    
                default: break;     
            }
            switch(west.getNumbersOfLines()){
                case 0: lineNeighbourWith0 = west.getLineID();
                        foundLine = true;
                        break;
                
                case 1: lineNeighbourWith1 =west.getLineID();
                        foundLine= true;
                        break;
                    
                case 2: lineNeighbourWith2 = west.getLineID();
                        break;
                    
                case 5: edgeLine = west.getLineID();
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
    
    /**
     * Ziehe den zweiten Strich einer Box
     * @return konnte ein zweiter Strich gezogen werden
     */
    private boolean setSecondLine(){
        //BoxC = CenterBox  boxLoop = soll Nachbarsbox aus boxList finden
        boolean foundLine = false;
        Box boxC;
        int edgeLine = -1;
        int lineNeighbourWith0 = -1;
        int lineNeighbourWith1 = -1;
        int lineNeighbourWith2 = -1;
        
        //Durchlaufe Array von Boxen mit einer Linie
        for(int i =0; i< box1Line.size();i++){
            boxC = box1Line.get(i);
            //Prüfe Linie im Norden & Prüfe Nachbar wenn Linie frei
            
            NeighbourValues north = this.checkLineN(boxC);
            NeighbourValues east = this.checkLineE(boxC);
            NeighbourValues south = this.checkLineS(boxC);
            NeighbourValues west = this.checkLineW(boxC);
            
            switch(north.getNumbersOfLines()){
                case 0: lineNeighbourWith0 = north.getLineID();
                        foundLine = true;
                        break;
                
                case 1: lineNeighbourWith1 = north.getLineID();
                        foundLine= true;
                        break;
                    
                case 2: lineNeighbourWith2 = north.getLineID();
                        break;
                    
                case 5: edgeLine = north.getLineID();
                        selectedLine = edgeLine;
                        foundLine = true;
                        return foundLine;
                    
                default: break;     
            }
            switch(east.getNumbersOfLines()){
                case 0: lineNeighbourWith0 = east.getLineID();
                        foundLine = true;
                        break;
                
                case 1: lineNeighbourWith1 = east.getLineID();
                        foundLine= true;
                        break;
                    
                case 2: lineNeighbourWith2 = east.getLineID();
                        break;
                    
                case 5: edgeLine = east.getLineID();
                        selectedLine = edgeLine;
                        foundLine = true;
                        return foundLine;
                    
                default: break;     
            }
            
            switch(south.getNumbersOfLines()){
                case 0: lineNeighbourWith0 = south.getLineID();
                        foundLine = true;
                        break;
                
                case 1: lineNeighbourWith1 = south.getLineID();
                        foundLine= true;
                        break;
                    
                case 2: lineNeighbourWith2 = south.getLineID();
                        break;
                    
                case 5: edgeLine = south.getLineID();
                        selectedLine = edgeLine;
                        foundLine = true;
                        return foundLine;
                    
                default: break;     
            }
            
            switch(west.getNumbersOfLines()){
                case 0: lineNeighbourWith0 = west.getLineID();
                        foundLine = true;
                        break;
                
                case 1: lineNeighbourWith1 = west.getLineID();
                        foundLine= true;
                        break;
                    
                case 2: lineNeighbourWith2 = west.getLineID();
                        break;
                    
                case 5: edgeLine = west.getLineID();
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
    
    /**
     * 
     * @param boxC ist die aktuelle Box
     * @return Gibt die BoxCID und die Anzahl markierter Linien des nördlichen Nachbarn zurück
     */
    private NeighbourValues checkLineN(Box boxC){
        if(boxC.getLineN().getColor()== Color.LIGHT_GRAY){
                int boxNid = boxNeighbours.getBoxN(boxC.getId());
                
                if(boxNid == -1){
                   selectedLine = boxC.getLineN().getId();
                   return new NeighbourValues(boxC.getLineN().getId(), 5);
                }
                else{
                    //Suche boxN Könnte mit getBox vereinfacht werden.
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
    
    private NeighbourValues checkLineE(Box boxC){
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
    
    private NeighbourValues checkLineS(Box boxC){
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
    
    private NeighbourValues checkLineW(Box boxC){
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
    
    /**
     * 
     * @return konnte eine dritte Linie markiert werden.
     */
    private boolean setThirdLine() {
        boolean result = false;
        this.setTubeList();
        if (!tubeList.isEmpty()) {
            if(tubeList.size()%2 ==1){
                ArrayList<Box> smallestTube = tubeList.get(0);
                for (ArrayList list : tubeList) {
                    if (smallestTube.size() > list.size()) {
                        smallestTube = list;
                    }
                }
                if(smallestTube.get(0).getLineN().getColor() == Color.LIGHT_GRAY){
                    selectedLine = smallestTube.get(0).getLineN().getId();
                } else if(smallestTube.get(0).getLineE().getColor() == Color.LIGHT_GRAY){
                    selectedLine = smallestTube.get(0).getLineE().getId();
                } else if(smallestTube.get(0).getLineS().getColor() == Color.LIGHT_GRAY){
                    selectedLine = smallestTube.get(0).getLineS().getId();
                } else if(smallestTube.get(0).getLineW().getColor() == Color.LIGHT_GRAY){
                    selectedLine = smallestTube.get(0).getLineW().getId();
                }
                System.out.println("smallestTube: " + smallestTube.get(0).getId() +"\n"+
                        "tubeList size: "+ tubeList.size());
                result = true;
            } else{
                ArrayList<Box> smallestTube = tubeList.get(0);
                ArrayList<Box> secondSmallestTube = tubeList.get(0);
                for (ArrayList list : tubeList) {
                    if (smallestTube.size() > list.size()) {
                        secondSmallestTube = smallestTube;
                        smallestTube = list;  
                    }
                    else if(secondSmallestTube.size() > list.size()){
                        secondSmallestTube = list;
                    }
                }
                if(secondSmallestTube.get(0).getLineN().getColor() == Color.LIGHT_GRAY){
                    selectedLine = secondSmallestTube.get(0).getLineN().getId();
                } else if(secondSmallestTube.get(0).getLineE().getColor() == Color.LIGHT_GRAY){
                    selectedLine = secondSmallestTube.get(0).getLineE().getId();
                } else if(secondSmallestTube.get(0).getLineS().getColor() == Color.LIGHT_GRAY){
                    selectedLine = secondSmallestTube.get(0).getLineS().getId();
                } else if(secondSmallestTube.get(0).getLineW().getColor() == Color.LIGHT_GRAY){
                    selectedLine = secondSmallestTube.get(0).getLineW().getId();
                }
                System.out.println("secondSmallestTube Box0: " + secondSmallestTube.get(0).getId() + "\n"
                        + "smallestTube Box0: " +smallestTube.get(0).getId()+"\n"
                        + "tubeList size: " + tubeList.size());
                result = true;
            }    
        }
        return result;
    }
    
    /**
     * Erstellt eine Liste mit aneinanderhängenden Boxen 
     * welche zwei markierte Linien haben.
     */
    private void setTubeList(){
        ArrayList<Box> checkedBoxes = new ArrayList<>();
        Box currentBox;
        for(Box box : box2Line){
            if(!checkedBoxes.contains(box)){
                checkedBoxes.add(box);
                ArrayList<Box> oneTube = new ArrayList<>();
                oneTube.add(box);
                currentBox = box;
                boolean aNeighbourHave2Lines = true;
                while(aNeighbourHave2Lines){
                    if((currentBox.getLineN().getColor() == Color.LIGHT_GRAY) 
                            && box2Line.contains(boxNeighbours.getBoxN(currentBox.getId()))
                            && !checkedBoxes.contains(boxNeighbours.getBoxN(currentBox.getId()))){
                        currentBox = getBox(boxNeighbours.getBoxN(currentBox.getId()));
                        checkedBoxes.add(currentBox);
                        oneTube.add(currentBox);
                    }
                    else if((currentBox.getLineE().getColor() == Color.LIGHT_GRAY) 
                            && box2Line.contains(boxNeighbours.getBoxE(currentBox.getId()))
                            && !checkedBoxes.contains(boxNeighbours.getBoxE(currentBox.getId()))){
                        currentBox = getBox(boxNeighbours.getBoxE(currentBox.getId()));
                        checkedBoxes.add(currentBox);
                        oneTube.add(currentBox);
                    }
                    else if((currentBox.getLineS().getColor() == Color.LIGHT_GRAY) 
                            && box2Line.contains(boxNeighbours.getBoxS(currentBox.getId()))
                            && !checkedBoxes.contains(boxNeighbours.getBoxS(currentBox.getId()))){
                        currentBox = getBox(boxNeighbours.getBoxS(currentBox.getId()));
                        checkedBoxes.add(currentBox);
                        oneTube.add(currentBox);
                    }
                    else if((currentBox.getLineW().getColor() == Color.LIGHT_GRAY) 
                            && box2Line.contains(boxNeighbours.getBoxW(currentBox.getId()))
                            && !checkedBoxes.contains(boxNeighbours.getBoxW(currentBox.getId()))){
                        currentBox = getBox(boxNeighbours.getBoxW(currentBox.getId()));
                        checkedBoxes.add(currentBox);
                        oneTube.add(currentBox);
                    }
                    else{
                        tubeList.add(oneTube);
                        aNeighbourHave2Lines = false;
                    }
                }
            }
        }
    }
    
    /**
     * 
     * @param id BoxID
     * @return Gibt die Box zurück mit der ausgewählten ID
     */
    private Box getBox(int id){
        for(Box box : boxList){
            if(box.getId() == id){
                return box;
            }
        }
        System.out.println("Keine gültige Box gefunden daher wurde eine neue generiert");
        return new Box(1000);
    }

    @Override
    public void act() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * @return gibt die Linie zurück welche ausgewählt werden soll.
     */
    @Override
    public int getTurn() {
        this.sortBoxes();
        this.setAITurn();
        int result = selectedLine;
        selectedLine = 0;
        return result;
    }
    
    /**
     * Versuche zuerst eine Box zu schliessen, dann zweite, dann erste und wenn
     * nichts mehr geht eine dritte Linie einer Box zu ziehen.
     * Falls davon nichts klappe, setze eine zufällige Linie.
     * Leere danach alle Arrays.
     */
    private void setAITurn(){
        if(setFourthLine()){}
        else if(setSecondLine()){}
        else if(setFirstLine()){}
        else if(setThirdLine()){}
        else{
            setRandomTurn();
        }
        box0Line.clear();
        box1Line.clear();
        box2Line.clear();
        box3Line.clear();
        tubeList.clear();
    }
    
    /**
     * Besetze eine zufällige leere Linie
     */
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
