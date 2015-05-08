/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Remo
 */
public class GPanel extends JPanel {
    
    ArrayList<Line> lineList;
        
    public GPanel() {
            setPreferredSize(new Dimension(496, 496));
            lineList = new ArrayList<>();
            for(int i = 1 ; i <= 24 ; i++){
                lineList.add(new Line(i));
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            g.drawRect(0, 0, this.getWidth(), this.getHeight());
            this.drawLines(g);
            this.drawDotGrid(g);
        }
        private void drawDotGrid(Graphics g) {
            int x = 40;
            int y = 40;
            g.setColor(Color.black);
            for(int i = 0; i < 4 ; i++){
                for(int k=0 ; k<4 ; k++){
                    g.fillOval(x, y, 32, 32);
                    x = x+104;
                }
                y = y + 104;
                x = 40;
            }
        }
        private void drawLines(Graphics g){
            int x = 68;
            int y = 44;
            int i;
            for(i=0; i<3 ; i++){
                while(i<3){
                    g.setColor(lineList.get(i).getColor());
                    g.fillRect(x, y, lineList.get(i).getWidth(), lineList.get(i).getHeight());
                    x = x + 104;
                }
            }
            
        }
}
