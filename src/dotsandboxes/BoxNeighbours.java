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
public class BoxNeighbours {
    
    private final int row;
    private final int col;
    private static ArrayList<Integer> corner;
    private static ArrayList<Integer> edge;
    private static ArrayList<Integer> infield;
    
    public BoxNeighbours(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    public int getBoxN(int BoxID){
        if(BoxID<=col){
            return -1;
        }
        else{
            return (BoxID - col);
        }
    }
    
    public int getBoxE(int BoxID){
        if(BoxID%row == 0){
            return -1;
        }
        else{
            return (BoxID + 1);
        }
    }
    /**
     * @return Ist die Box am Südrand, so gib -1 zurück. 
     * Falls nicht gib BoxID der südlichen Box zurück
    */
    public int getBoxS(int BoxID){
        if(BoxID >= (row*col-col)){
            return -1;
        }
        else{
            return (BoxID + col);
        }
    }
    
    public int getBoxW(int BoxID){
        if(BoxID%col== 1){
            return -1;
        }
        else{
            return (BoxID-1);
        }
    }
    
    public static ArrayList getCorner(){
        return corner;
    }
    
    public static ArrayList getEdge(){
        return edge;
    }
    
    public static ArrayList getInnfield(){
        return infield;
    }
    
    private void setCorner(){
        corner.add(1);
        corner.add(row);
        corner.add(col);
        corner.add(row*col);
    }
    
    
    private void setEdge(){
        for(int i=0; i<= row*col; i++){
            if(i>1&&i<col){
                edge.add(i);
            }
            else if(i%col==0 && i!=1 && i!= row && i!=col && i!=row*col){
                edge.add(i);
            }
            else if((i%col)+1==0 && i!=1 && i!= row && i!=col && i!=row*col){
                edge.add(i);
            }
            else if(i>(row-1)*col+1 && i<row*col){
                edge.add(i);
            }
        }
    }
    
    private void setInfield(){
        for(int i=0; i< row*col; i++){
            if(i>1&&i<col){}
            else if(i%col==0 && i!=1 && i!= row && i!=col && i!=row*col){}
            else if((i%col)+1==0 && i!=1 && i!= row && i!=col && i!=row*col){}
            else if(i>(row-1)*col+1 && i<row*col){}
            else if(i!=1){}
            else if(i!=row){}
            else if(i!=col){}
            else if(i!=col*row){}
            else{
                infield.add(i);
            }
        }
    }
}
