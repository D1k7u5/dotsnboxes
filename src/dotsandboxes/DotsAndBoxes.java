/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Remo
 */
public class DotsAndBoxes extends JFrame implements Runnable,ActionListener{

    /**
     * @param args the command line arguments
     */
    private final JPanel menuPanel,dummyPanel1,dummyPanel2,dummyPanel3,dummyPanel4;
    private final JPanel statsPanel;
    private final JButton start,btnNewGame,btnLoadGame;
    private final JLabel labelRow,labelCol,labelLoc,labelNet,labelCom;
    private final JLabel labelRound,labelP1r,labelP2r,labelGamesWon,labelP1g,labelP2g;
    private final JTextField fieldRow,fieldCol;
    private final JRadioButton net,loc,com;
    private JLabel lblP1BoxesCnt,lblP2BoxesCnt,lblP1GameCnt,lblP2GameCnt;
    private GPanel gameView;
    private int gameType = 0;
    
    private ArrayList<Line> lineList;
    
    public DotsAndBoxes(){
        super("DotsAndBoxes");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Menu
        menuPanel = new JPanel();
        start = new JButton("Start");
        btnLoadGame=new JButton("Load Game");
        labelCol = new JLabel("Columns");
        labelRow = new JLabel("Rows");
        fieldRow = new JTextField("3");
        fieldCol = new JTextField("3");
        labelLoc = new JLabel("Local Game");
        labelNet = new JLabel("Network Game");
        labelCom = new JLabel("Computer Game");
        loc = new JRadioButton();
        loc.setSelected(true);
        net = new JRadioButton();
        net.setEnabled(false);
        com = new JRadioButton();
        com.setEnabled(false);
        //Game view panel
        gameView = new GPanel();
        //dummy panel for layout
        dummyPanel1 = new JPanel();
        dummyPanel2 = new JPanel();
        dummyPanel3 = new JPanel();
        dummyPanel4 = new JPanel();
        //stats Panel
        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel,BoxLayout.PAGE_AXIS));
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(3,2));
        //panel1.setPreferredSize(new Dimension(100,50));
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        panel1.setBorder(loweredetched);
        labelRound = new JLabel("Round : ");
        labelP1r = new JLabel("P1 :");
        lblP1BoxesCnt = new JLabel("0");
        labelP2r = new JLabel("P2 :");
        lblP2BoxesCnt = new JLabel("0");
        panel1.add(labelRound);
        panel1.add(new JLabel());
        panel1.add(labelP1r);
        panel1.add(lblP1BoxesCnt);
        panel1.add(labelP2r);
        panel1.add(lblP2BoxesCnt);
        
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(3,2));
        //panel2.setPreferredSize(new Dimension(100,50));
        panel2.setBorder(loweredetched);
        labelGamesWon = new JLabel("Games Won :");
        labelP1g = new JLabel("P1 :");
        lblP1GameCnt = new JLabel("0");
        labelP2g = new JLabel("P2 :");
        lblP2GameCnt = new JLabel("0");
        panel2.add(labelGamesWon);
        panel2.add(new JPanel());
        panel2.add(labelP1g);
        panel2.add(lblP1GameCnt);
        panel2.add(labelP2g);
        panel2.add(lblP2GameCnt);
        btnNewGame = new JButton("New Game");
        //btnNewGame.setPreferredSize(new Dimension(100,50));
        statsPanel.add(panel1);
        statsPanel.add(panel2);
        statsPanel.add(btnNewGame);
        
        start.addActionListener(this);
        btnLoadGame.addActionListener(this);
        btnNewGame.addActionListener(this);
        //start menu
        menuPanel.setLayout(new GridLayout(6,2));
        menuPanel.add(labelRow);
        menuPanel.add(fieldRow);
        menuPanel.add(labelCol);
        menuPanel.add(fieldCol);
        menuPanel.add(loc);
        menuPanel.add(labelLoc);
        menuPanel.add(net);
        menuPanel.add(labelNet);
        menuPanel.add(com);
        menuPanel.add(labelCom);
        menuPanel.add(btnLoadGame);
        menuPanel.add(start);
        menuPanel.setSize(500, 300);
        
        this.setMenuView();
        this.setResizable(false);
        this.setVisible(true);
        
        Thread t = new Thread(this);
        t.start();
    }
    
    public void setGameView(int rows, int columns){
        //Game view panel
        gameView = new GPanel(rows,columns,this.gameType);   
        this.remove(menuPanel);
        this.remove(dummyPanel1);
        this.remove(dummyPanel2);
        this.remove(dummyPanel3);
        this.remove(dummyPanel4);
        this.setLayout(new BorderLayout());
        this.add(dummyPanel1,BorderLayout.NORTH);
        this.add(statsPanel,BorderLayout.WEST);
        this.add(dummyPanel3,BorderLayout.SOUTH);
        this.add(dummyPanel4,BorderLayout.EAST);
        this.add(gameView,BorderLayout.CENTER);
        repaint();
        this.pack();
    }
    private void setMenuView() {
        this.setLayout(new BorderLayout());
        this.add(dummyPanel1,BorderLayout.NORTH);
        this.add(dummyPanel2,BorderLayout.EAST);
        this.add(dummyPanel3,BorderLayout.WEST);
        this.add(dummyPanel4,BorderLayout.SOUTH);
        this.add(menuPanel,BorderLayout.CENTER);
        this.pack();    
    }
    public JPanel getGameView() {
        return gameView;
    }
    
    @Override
    public void run() {
        while(true){
            this.repaint();
            if(Integer.parseInt(this.lblP1BoxesCnt.getText()) != this.gameView.getNrOfBoxes(0)){
                this.lblP1BoxesCnt.setText(Integer.toString(this.gameView.getNrOfBoxes(0)));
            }else if(Integer.parseInt(this.lblP2BoxesCnt.getText()) != this.gameView.getNrOfBoxes(1)){
                this.lblP2BoxesCnt.setText(Integer.toString(this.gameView.getNrOfBoxes(1)));
            }else if(Integer.parseInt(this.lblP1GameCnt.getText()) != this.gameView.getNrOfVictories(0)){
                this.lblP1GameCnt.setText(Integer.toString(this.gameView.getNrOfVictories(0)));
            }else if(Integer.parseInt(this.lblP2GameCnt.getText()) != this.gameView.getNrOfVictories(1)){
                this.lblP2GameCnt.setText(Integer.toString(this.gameView.getNrOfVictories(1)));
            }
            try{
                Thread.sleep(20);
            }
            catch(InterruptedException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.start){
            try{
                if(Integer.parseInt(this.fieldRow.getText())>6 || Integer.parseInt(this.fieldCol.getText())>8){
                    throw new NumberFormatException("enter columns below 9 and rows below 7");
                }                
                this.setGameView(Integer.parseInt(this.fieldRow.getText()),Integer.parseInt(this.fieldCol.getText()));
            }
            catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(null, nfe.getMessage());
            }
        }else if (e.getSource() == this.loc){
            gameType = 0;
        }else if(e.getSource() == this.net){
            gameType = 1;
        }else if(e.getSource() == this.com){
            gameType = 2;
        }else if(e.getSource() == this.btnNewGame){
            //go back to menu view
        }else if(e.getSource() == this.btnLoadGame){
            JFileChooser fc = new JFileChooser();
            int result = fc.showOpenDialog(this);
            File file = fc.getSelectedFile();
            
            //simulate gameplay with file
        }
    }
    public static void main(String[] args) {
        DotsAndBoxes dab = new DotsAndBoxes();
    }
}
