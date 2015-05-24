/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

/**
 *
 * @author davidheer
 */
public class NeighbourValues {
    private int lineID;
    private int numbersOfLines;
    
    public NeighbourValues(int lineID, int numbersOfLines){
        this.lineID = lineID;
        this.numbersOfLines = numbersOfLines;
    }
    
    public int getLineID(){
        return lineID;
    }
    
    public int getNumbersOfLines(){
        return numbersOfLines;
    }
    
    public void setLineID(int id){
        lineID = id;
    }
    
    public void setNumbersOfLines(int n){
        numbersOfLines = n;
    }
    
}
