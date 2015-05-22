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
    private final JButton start,btnNewGame,btnLoadGame,btnSaveGame,btnStartNet;
    private final JLabel labelRow,labelCol,labelLoc,labelNet,labelCom;
    private final JLabel labelRound,labelP1r,labelP2r,labelGamesWon,labelP1g,labelP2g;
    private final JTextField fieldRow,fieldCol;
    private final JRadioButton net,loc,com,rbtnServer,rbtnClient;
    private JLabel lblP1BoxesCnt,lblP2BoxesCnt,lblP1GameCnt,lblP2GameCnt;
    private GPanel gameView;
    private int gameType = 0;
    private JPanel netConnectionView;
    
    private ArrayList<Line> lineList;
    
    public DotsAndBoxes(){
        super("DotsAndBoxes");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Menu
        menuPanel = new JPanel();
        start = new JButton("Start");
        btnLoadGame=new JButton("Load Game");
        btnSaveGame = new JButton("Save Game");
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
        com = new JRadioButton();
        //Game view panel
        gameView = new GPanel();
        //dummy panel for layout
        dummyPanel1 = new JPanel();
        dummyPanel2 = new JPanel();
        dummyPanel3 = new JPanel();
        dummyPanel4 = new JPanel();
//View for network connection
        netConnectionView = new JPanel();
        rbtnClient = new JRadioButton();
        rbtnServer = new JRadioButton();
        btnStartNet = new JButton("Start");
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
        statsPanel.add(btnSaveGame);
//listener Registration
        start.addActionListener(this);
        btnLoadGame.addActionListener(this);
        btnSaveGame.addActionListener(this);
        btnNewGame.addActionListener(this);
        loc.addActionListener(this);
        com.addActionListener(this);
        net.addActionListener(this);
        rbtnServer.addActionListener(this);
        rbtnClient.addActionListener(this);
        btnStartNet.addActionListener(this);
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
        this.removeMenuView();
        //this.setLayout(new BorderLayout());
        //this.add(dummyPanel1,BorderLayout.NORTH);
        this.add(statsPanel,BorderLayout.WEST);
        //this.add(dummyPanel3,BorderLayout.SOUTH);
        //this.add(dummyPanel4,BorderLayout.EAST);
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
    public void removeMenuView(){
        this.remove(menuPanel);
        //this.remove(dummyPanel1);
        //this.remove(dummyPanel2);
        this.remove(dummyPanel3);
        //this.remove(dummyPanel4);
    }
    private void setNetworkConnectionView(){
        this.removeMenuView();
        this.netConnectionView.setLayout(new GridLayout(4,2));
        this.netConnectionView.add(new JLabel("Start Network Connection"));
        this.netConnectionView.add(new JPanel());
        this.netConnectionView.add(rbtnServer);
        this.netConnectionView.add(new JLabel("as Server"));
        this.netConnectionView.add(rbtnClient);
        this.netConnectionView.add(new JLabel("as Client"));
        this.netConnectionView.add(new JPanel());
        this.netConnectionView.add(btnStartNet);
        this.add(this.dummyPanel3,BorderLayout.WEST);
        this.add(netConnectionView,BorderLayout.CENTER);
        this.pack();
    }
    private void removeNetworkConnectionView(){
        this.remove(netConnectionView);
    }
    private void setNetworkServerView(){
        removeNetworkConnectionView();
        this.netConnectionView.removeAll();
        this.netConnectionView.setLayout(new GridLayout(2,1));
        this.netConnectionView.add(new JLabel("Waiting for Connection"));
        this.netConnectionView.add(new JLabel("........"));
        this.add(netConnectionView,BorderLayout.CENTER);
        this.pack();
    }
    private void setNetworkClientView(){
        removeNetworkConnectionView();
        this.netConnectionView.removeAll();
        this.netConnectionView.setLayout(new GridLayout(2,1));
        this.netConnectionView.add(new JLabel("Looking for Dots&Boxes Servers"));
        this.netConnectionView.add(new JLabel("........"));
        this.add(netConnectionView,BorderLayout.CENTER);
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
                switch(this.gameType){
                    case 0:
                        if(Integer.parseInt(this.fieldRow.getText())>6 || Integer.parseInt(this.fieldCol.getText())>8){
                            throw new NumberFormatException("enter columns below 9 and rows below 7");
                        }
                        this.setGameView(Integer.parseInt(this.fieldRow.getText()),Integer.parseInt(this.fieldCol.getText()));
                        break;
                    case 1:
                        this.setNetworkConnectionView();
                        break;
                }
                                
            }
            catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(null, nfe.getMessage());
            }
        }else if (e.getSource() == this.loc){
            gameType = 0;
            com.setSelected(false);
            net.setSelected(false);
        }else if(e.getSource() == this.net){
            gameType = 1;
            loc.setSelected(false);
            com.setSelected(false);
        }else if(e.getSource() == this.com){
            gameType = 2;
            loc.setSelected(false);
            net.setSelected(false);
        }else if(e.getSource() == this.rbtnClient){
            rbtnServer.setSelected(false);
        }else if(e.getSource() == this.rbtnServer){
            rbtnClient.setSelected(false);
        }else if(e.getSource() == this.btnStartNet){
            if(rbtnClient.isSelected()){
                //start net game as client
                this.setNetworkClientView();
            }else if(rbtnServer.isSelected()){
                //start net game as server
                this.setNetworkServerView();
            }
        }else if(e.getSource() == this.btnNewGame){
            //go back to menu view
        }else if(e.getSource() == this.btnLoadGame){
            JFileChooser fc = new JFileChooser();
            int result = fc.showOpenDialog(this);
            File file = fc.getSelectedFile();
            LoadGamePlayer.setFileToLoad(file);
            LoadGamePlayer loadGameSettings = new LoadGamePlayer();
            this.gameType = 3;
            this.setGameView(loadGameSettings.getRows(),loadGameSettings.getColumns());
            
            
            //simulate gameplay with file
        }else if(e.getSource() == this.btnSaveGame){
            JFileChooser j = new JFileChooser();
            j.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
            if (j.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
                System.out.println("getCurrentDirectory(): " +  j.getCurrentDirectory());
                System.out.println("getSelectedFile() : " +  j.getSelectedFile());
                StorageGame.saveGame(j.getSelectedFile());
            }else {
                System.out.println("No Selection ");
            }
            //simulate gameplay with file
        }
    }
    public static void main(String[] args) {
        DotsAndBoxes dab = new DotsAndBoxes();
    }
}
