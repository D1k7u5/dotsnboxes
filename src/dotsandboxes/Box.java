/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

/**
 *
 * @author Remo
 */
public class Box {
    
    private Line lineN;
    private Line lineE;
    private Line lineS;
    private Line lineW;
    
    private int sideLength;
    private int id;
    
    public Box(int ID){
        id = ID;
        sideLength = 30;
    }
    public Box(Line n,Line e,Line s,Line w,int ID){
        lineN = n;
        lineE = e;
        lineS = s;
        lineW = w;
        sideLength = 30;
        id = ID;
    }
    
    public Line getLineN() {
        return lineN;
    }

    public void setLineN(Line lineN) {
        this.lineN = lineN;
    }

    public Line getLineE() {
        return lineE;
    }

    public void setLineE(Line lineE) {
        this.lineE = lineE;
    }

    public Line getLineS() {
        return lineS;
    }

    public void setLineS(Line lineS) {
        this.lineS = lineS;
    }

    public Line getLineW() {
        return lineW;
    }

    public void setLineW(Line lineW) {
        this.lineW = lineW;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    
    /**
     * Returns the Number of seted lines
    */
    public int getNumberOfSetedLines(){
        int counter = 0;
        
        if(lineN != null){counter++;}
        if(lineE != null){counter++;}
        if(lineS != null){counter++;}
        if(lineW != null){counter++;}
        
        return counter;
    }
    
}
