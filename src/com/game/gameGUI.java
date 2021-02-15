package com.game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Adarsh
 */
public class gameGUI extends JFrame implements ActionListener {
    
    JLabel heading,clock;
    Font font = new Font("",Font.BOLD,26);
    JPanel mainPanel;
    JButton []btns = new JButton[9];
    
    //Game Instance Variable
    
    int gameChances[] = {2,2,2,2,2,2,2,2,2};
    int activePlayer = 0;
    
    int wp[][] = {
        {0,1,2},
        {3,4,5},
        {6,7,8},
        {0,3,6},
        {1,4,7},
        {2,5,8},
        {0,4,8},
        {2,4,6}
};
    
    int winner = 2;
    boolean gameOver = false;
        
    gameGUI(){
        System.out.println("Instance is called");
        setTitle("Tic-Tac-Toe");                                        //Set Title of frame
        setSize(700,700);                                               //Set size of frame
        ImageIcon imageicon = new ImageIcon("src/img/tic-tac-toe.png"); //creating object for icon image 
        setIconImage(imageicon.getImage());                             //getting image from imageicon object
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                 //It stops the program when Frame is closed
        createGUI();
        setVisible(true);
    }
    
    
    private void createGUI()
    {
        this.getContentPane().setBackground(Color.decode("#A569BD"));
        this.setLayout(new BorderLayout());
        
        //For Heading
        heading = new JLabel("Tic Tac Toe");
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.white);
        this.add(heading,BorderLayout.NORTH);   //set heading to north side of border layout
        
        //For Clock
        clock = new JLabel("Clock");
        clock.setFont(font);
        clock.setHorizontalAlignment(SwingConstants.CENTER);
        clock.setForeground(Color.white);
        this.add(clock,BorderLayout.SOUTH);
        
        //Clock Thread
        Thread th = new Thread() {
        public void digiclock() {
            
            try{
                while (true)
                {
                    String datetime = new Date().toLocaleString();
                    clock.setText(datetime);
                    
                    Thread.sleep(1000);
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            
            }    
        };
        th.start();
        
        //Panel Content
         mainPanel = new JPanel();
         mainPanel.setLayout(new GridLayout(3, 3));     //Here we creating 3x3 grid in panel
         
         for(int i =1; i<=9; i++)                       
         {  
             JButton btn = new JButton();           //Now we creating 9 buttons and add on mainPanel
             
             btn.setBackground(Color.decode("#EBDEF0"));
             btn.setFont(font);
             mainPanel.add(btn);
             
             btns[i-1]=btn;         //storing these button into our button array declare above
             btn.addActionListener(this);
             btn.setName(String.valueOf(i-1));   //Initalize unique name to every button 
         }
         
         this.add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton currentBtn = (JButton)e.getSource();
        
        String btnName = currentBtn.getName();
        int name = Integer.parseInt(btnName.trim());
        
        if(gameOver)
        {
            JOptionPane.showMessageDialog(this, "Game Already Over...");
            return;
        }
        
        if(gameChances[name] == 2)
        {
         if(activePlayer == 1)
         {
             currentBtn.setIcon(new ImageIcon("src/img/cross.png"));   
             gameChances[name] = activePlayer;           //to prevent double chances on a single button             
             activePlayer = 0;                      //use to change player
         }else
         {
            currentBtn.setIcon(new ImageIcon("src/img/zero.png"));     
            gameChances[name] = activePlayer;            
            activePlayer = 1;
         }
            
        /*Winning Logic (There are total 8 winning positions)
          Winning Positions are 0 1 2, 3 4 5, 6 7 8, 0 3 6, 1 4 7, 2 5 8, 0 4 8, 2 4 6             
        */
        
           for(int []temp:wp)
        {
            if((gameChances[temp[0]]==gameChances[temp[1]]) && (gameChances[temp[1]]==gameChances[temp[2]]) && (gameChances[temp[2]]!=2))
            {
               winner =  gameChances[temp[0]];
               gameOver = true;
               JOptionPane.showMessageDialog(null, "Player "+winner+" has won the game.");
               int i = JOptionPane.showConfirmDialog(this, "Do you want to play more?");
               
               if(i==0)
               {
                   this.setVisible(false);
                   new gameGUI();
               }else if(i==1) {
                   System.exit(0);
               }
               else {
                   
               }
               break;
            }
        }
        
        //Draw Logic
        int c = 0;
        
        for(int x:gameChances)
        {
            if(x==2)
            {
                c++;
                break;
            }            
        }
        
        if(c==0 && (gameOver==false))
        {
            JOptionPane.showMessageDialog(null, "Game Draw");
               int i = JOptionPane.showConfirmDialog(this, "Do you want to play more?");
               
               if(i==0)
               {
                   this.setVisible(false);
                   new gameGUI();
               }else if(i==1) {
                   System.exit(0);
               }
               else {
                   
               }
        }
        
        }else {
            JOptionPane.showMessageDialog(this, "Position already occupied");
        }

        
        
       
   
   
    }
     
    
    
}
