/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

import java.awt.Color;

/**
 *
 * @author Remo
 */
public class Line {
    
    private Color color;
    private int height,width;
    private int id;
    

    public Line(int ID){
        color = Color.gray;
        width = 80;
        height = 24;
        this.id = ID;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }

    public void setId(int ID){
        this.id = ID;
    }
    
    public int getId(){
        return id;
    }
}
