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
 * @author tom
 */
public class Player {
    private Color color;
    private final ArrayList<Integer> ownBoxes = new ArrayList<>();
    private int victories = 0;
    
    public Player(Color color) {
        this.color = color;
    }
    
    public Player(){
        this.color = Color.GREEN;
    }

    public int getVictories() {
        return victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public Color getColor() {
        return color;
    }
    
    public void addBox(int id){
        ownBoxes.add(id);
    }

    public ArrayList<Integer> getBoxes() {
        return ownBoxes;
    }

    void reset() {
        ownBoxes.removeAll(ownBoxes);
    }

    void addVictory() {
        this.victories++;
    }
    
}
