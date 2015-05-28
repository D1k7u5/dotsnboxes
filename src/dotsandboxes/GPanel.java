/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Remo
 */
public class GPanel extends JPanel implements MouseListener, IPlayer, IWinnerCallback {

    private ArrayList<Line> lineList;
    private ArrayList<Box> boxList;
    private LineDetection lineDetector;
    private int selectedLine;
    private int row;
    private int col;
    private GameController gameController;

    public GPanel() {
    }
    
    /**
     * 
     * @param rows      Anzahl Reihen des Spielfeldes
     * @param columns   Anzahl Kolonnen des Spielfeldes
     * @param type      Typ des Spiels (lokal/netwerk/computer)
     */
    
    public GPanel(int rows, int columns, int type) {
        row = rows;
        col = columns;

        //row = columns;

        setPreferredSize(new Dimension((104 * (col + 1)) + 10, (104 * (row + 1)) + 10));
        lineList = new ArrayList<>();
        boxList = new ArrayList<>();
        initLinesAndBoxes();

        addMouseListener(this);
        selectedLine = -1;
        lineDetector = new LineDetection(40 + 16, 24, 80, col, row);
        
        gameController = new GameController(this, this, boxList,type, row, col);
        gameController.addWinnerObserver(this);
        new Thread(gameController).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        this.drawBoxes(g2d);
        this.drawLines(g2d);
        this.drawDotGrid(g2d);
    }
    /**
     *      Zeichnet die Punkte auf das Spielfeld
     * @param g         Graphics Object zum draufzeichnen
     */
    private void drawDotGrid(Graphics2D g) {
        int x = 40;
        int y = 40;
        for (int i = 0; i < row + 1; i++) {
            for (int k = 0; k < col + 1; k++) {
                g.setColor(Color.orange);
                g.fillOval(x, y, 32, 32);
                g.setColor(Color.black);
                g.drawOval(x, y, 32, 32);
                x = x + 104;
            }
            y = y + 104;
            x = 40;
        }
    }
    /**
     *      Zeichnet die Linien auf die Position gemäss Line ID
     * @param g         Graphics Object zum draufzeichnen
     */
    private void drawLines(Graphics2D g) {
        int x = 63;
        int y = 44;
        int c;
        int r;
        int selLine = 0;
        //horizontal lines
        for (r = 0; r < row +1 ; r++) {
            for (c = 0; c < col; c++) {
                selLine = (r * ( (col *2) +1 ) + c);
                g.setColor(lineList.get(selLine).getColor());
                g.fillRect(x, y, lineList.get(selLine).getWidth(), lineList.get(selLine).getHeight());
                g.setColor(Color.black);
                g.drawRect(x, y, lineList.get(selLine).getWidth(), lineList.get(selLine).getHeight());
                x = x + 104;
            }
            y = y + 104;
            x = 63;
        }
        //vertical lines, switched width an height
        x = 45;
        y = 63;
        for (r = 0; r < row; r++) {
            for (c = 0; c < col + 1; c++) {
                selLine = c+ (r * (2* col + 1 )) + col;
                g.setColor(lineList.get(selLine).getColor());
                g.fillRect(x, y, lineList.get(selLine).getHeight(), lineList.get(selLine).getWidth());
                g.setColor(Color.black);
                g.drawRect(x, y, lineList.get(selLine).getHeight(), lineList.get(selLine).getWidth());
                x = x + 104;
            }
            y = y + 104;
            x = 45;
        }
    }
    /**
     *      Zeichnet die Boxen auf Ihre Position gemäss Besitzer
     * @param g         Graphics Object zum draufzeichnen
     */
    private void drawBoxes(Graphics2D g){
        Player p1 = this.gameController.getPlayer(0);
        Player p2 = this.gameController.getPlayer(1);
        int x = 74;
        int y = 72;
        int r = 0;
        int c = 0;
        int index = 1;
        int sl = 71;
        for(int i=1 ; i<=row ; i++){
                for(int n=1 ; n<=col ; n++){
                    if(p1.getBoxes().contains(index)){
                        g.setColor(p1.getColor());
                        g.fillRect(x+104*c, y+104*r+1, sl, sl);
                    }else if(p2.getBoxes().contains(index)){
                        g.setColor(p2.getColor());
                        g.fillRect(x+104*c, y+104*r+1, sl, sl);
                    }
                    index++;
                    c++;
                }
                c=0;
                r++; 
        }
    }

    /**
     *  Setzt die Anzahl Reihen und Kolonnen des Spielfeldes
     * @param rows          Anzahl Reihen des Spielfeldes
     * @param columns       Anzahl Kolonnen des Spielfeldes
     */
    public void setDimensions(int rows, int columns) {
        row = rows;
        col = columns;
        setPreferredSize(new Dimension((104 * (col + 1)) + 10, (104 * (row + 1)) + 10));
        initLinesAndBoxes();
    }

    /**
     *  Weist jeder Box ihre Linien (Nord,Süd,Ost,West) zu
     */
    private void initLinesAndBoxes() {
        for (int i = 1; i <= (((row + 1) * col) + ((col + 1) * row)); i++) {
            lineList.add(new Line(i));
        }
        int c = 1;  //counter for box IDs
        for (int i = 1; i <= row; i++) {
            for (int k = 1; k <= col; k++) {
                boxList.add(new Box(c));
                int test = (k + (i - 1) * (col + col + 1));
                boxList.get(c - 1).setLineN(lineList.get(k + (i - 1) * (col + col + 1) - 1));
                boxList.get(c - 1).setLineS(lineList.get(k + (i) * (col + col + 1) - 1));
                boxList.get(c - 1).setLineE(lineList.get(c + col + ((i - 1) * (col + 1))));
                boxList.get(c - 1).setLineW(lineList.get(c + col + ((i - 1) * (col + 1)) - 1));
                c++;
            }
        }
    }

    public int getRows() {
        return row;
    }

    public int getCols() {
        return col;
    }
    
    /**
     *  Gibt die Anzahl gewonnener Boxen eines Spielers zurück
     * @param playerIndex   Index des Spielers (0=P1 , 1=P2)
     * @return              Anzahl gewonnener Boxen, sonst 0
     */
    public int getNrOfBoxes(int playerIndex){
        try{
            return gameController.getPlayer(playerIndex).getBoxes().size();
        }catch(NullPointerException e){
            return 0;
        }
    }
    
    public int getNrOfVictories(int playerIndex){
        try{
        return gameController.getPlayer(playerIndex).getVictories();
        }catch(NullPointerException e){
            return 0;
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        selectedLine = lineDetector.getLine(e.getX(), e.getY());
        System.out.println("Line ID: " + selectedLine);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int getTurn() {
        int result = selectedLine;
        selectedLine = -1;
//        System.out.println("Line ID getTurn: " + result);
        return result;
    }

    @Override
    public void winnerIs(String string) {
        JOptionPane.showMessageDialog(this, string);
    }
}
