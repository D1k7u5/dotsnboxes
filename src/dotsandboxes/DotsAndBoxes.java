/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Remo
 */
public class DotsAndBoxes extends JFrame implements Runnable{

    /**
     * @param args the command line arguments
     */
    /*private final JButton start;
    private final JLabel gameSize;
    private final JTextField sizeField;
    private final JRadioButton net,loc,com;*/
    private final GPanel gameView;
    
    private ArrayList<Line> lineList;
    
    public DotsAndBoxes(){
        super("DotsAndBoxes");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        gameView = new GPanel();
        gameView.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        gameView.setPreferredSize(new Dimension(800,600));
        gameView.setBackground(Color.white);
        gameView.addMouseListener(new MouseListener(){
            
            @Override
            public void mousePressed(MouseEvent e) {
                
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public void mouseReleased(MouseEvent e) {
             //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        this.setLayout(new BorderLayout());
        this.add(gameView,BorderLayout.CENTER);
        this.setSize(496, 496);
        this.setVisible(true);
        
        Thread t = new Thread(this);
        t.start();
    }
    
    public void createGameView(){
        
    }
    public static void main(String[] args) {
        // TODO code application logic here
        DotsAndBoxes dab = new DotsAndBoxes();
    }
    
    @Override
    public void run() {
        while(true){
            this.getGameView().repaint();
            try{
                Thread.sleep(20);
            }
            catch(InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public JPanel getGameView() {
        return gameView;
    }
}
