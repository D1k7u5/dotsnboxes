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
    
    public Box(Line n,Line e,Line s,Line w,int ID){
        lineN = n;
        lineE = e;
        lineS = s;
        lineW = w;
        sideLength = 18;
        id = ID;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    
}
