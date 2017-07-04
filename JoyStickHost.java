/*
 * Author : Zero
 * date   : 22/2/15
 * time   : 1:30AM
 * */

import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author 
 */
public class JoyStickHost {

   
    public static void main(String[] args) {

        new MyFrame(400, 300);
    }

}

class MyFrame extends JFrame {

    JTextArea ta = new JTextArea();
    JButton scan = new JButton("Start Scanning");
    JButton stop = new JButton("Stop Scanning");
    JScrollPane sp=new JScrollPane(ta,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    ServerSocket server;
    DoOperations operation = new DoOperations(ta);

    MyFrame(int w, int h) {
        try {
			this.setTitle("Ip Address : "+Inet4Address.getLocalHost());
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}
        this.setSize(w, h);
        this.setVisible(true);
        this.setDefaultCloseOperation(2);

        initGUI();
    }

    void initGUI() {
        this.add(sp);
        this.add(scan, BorderLayout.NORTH);
        this.add(stop, BorderLayout.SOUTH);

        scan.addActionListener(operation);
        stop.addActionListener(operation);
    }
}

///operations done there------------------------------------------------------------------------------
class DoOperations implements ActionListener, Runnable {
	Robot bot=null;
	byte leftP,rightP,accelP,reverseP,nitroP,brakeP,gearP,backP,selectP;
    ServerSocket s;
    JTextArea ta;
    boolean stopThread = false;

    DoOperations(JTextArea ta) {
    	leftP=rightP=accelP=reverseP=nitroP=brakeP=gearP=backP=selectP=0;
    	try {
			bot=new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
        this.ta = ta;
    }

    public void actionPerformed(ActionEvent av) {
        JButton b = (JButton) av.getSource();

        if (av.getActionCommand().equals("Start Scanning")) {
            ta.append("trying to conect.....\n");
            new Thread(this).start();

            b.setEnabled(false);
        } else {
            stopThread = true;
        }
    }

    public void run() {

        Socket sc = null;
        ObjectInputStream inp = null;
        try {
            s = new ServerSocket(2020);
            sc = s.accept();
            ta.append("connected at : " + sc.getInetAddress() + "\n");
        } catch (IOException ex) {
            Logger.getLogger(DoOperations.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            ta.append("Drawing a Channel......\n");
            inp = new ObjectInputStream(sc.getInputStream());
            ta.append("Channel Drawn ..\nNow Enjoy BC+\n");
        } catch (IOException ex) {
            Logger.getLogger(DoOperations.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while (stopThread==false) {

                String s = (String)inp.readObject();
                
                //ta.append("\n"+s);
                perfromOperation(s);

            }
            ta.append("Stopped COnnection");
            sc.close();
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(DoOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DoOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //--------------------------------------------------------------------------------------------------=-=-=-=-=-=-
    //all bla bla stuff to do here
    //byte leftP,rightP,accelP,reverseP,nitroP,brakeP,backP,selectP;
    
    //0=no state,1=pressed state,2=released state,3=haleted state;
    void perfromOperation(String c) {
    	System.out.println(c);
    	
    	switch(c){
    	
    	case "L-D":leftP=1;break;
    	case "L-U":leftP=2;break;
    	
    	case "R-D":if(rightP!=3)rightP=1;break;
    	case "R-U":rightP=2;break;
    	
    	case "AC-D":if(accelP!=3)accelP=1;break;
    	case "AC-U":accelP=2;break;
    	
    	case "RC-D":if(reverseP!=3)reverseP=1;break;
    	case "RC-U":reverseP=2;break;
    	
    	case "N-D":if(nitroP!=3)nitroP=1;break;
    	case "N-U":nitroP=2;break;
    	
    	case "B-D":if(brakeP!=3)brakeP=1;break;
    	case "B-U":brakeP=2;break;
    	
    	case "gear-D":if(gearP!=3)gearP=1;break;
    	case "gear-U":gearP=2;break;
    	
    	
    	case "select-D":if(selectP!=3)selectP=1;break;
    	case "select-U":selectP=2;break;
    	
    	case "back-D":if(backP!=3)backP=1;break;
    	case "back-U":backP=2;break;
    	}
    	
    	clickOperation(c.substring(0,c.indexOf("-")));
    }
    
    void clickOperation(String c){
    	byte val=0;
    	switch(c){
    	
    	case "L":
    		//if(leftP==0)return ;
    		if(leftP==1){
    			//System.out.println(c+"P");
    			bot.keyPress(KeyEvent.VK_A);
    			
    		}else if(leftP==2 ){
    			//System.out.println(c+"R");
    			bot.keyRelease(KeyEvent.VK_A);
    			leftP = 0;
    		}
    		break;
    	
    	case "R":
    		if(rightP==0)return ;
    		if(rightP==1 ){
    			//System.out.println(c+"P");
    			bot.keyPress(KeyEvent.VK_D);
    			
    		}else if(rightP==2 ){
    			//System.out.println(c+"R");
    			bot.keyRelease(KeyEvent.VK_D);
    			rightP = 0;
    		}
    		break;
    	
    	
    	case "AC":
    		if(accelP==0)return ;
    		if(accelP==1 ){
    			//System.out.println(c+"P");
    			bot.keyPress(KeyEvent.VK_W);
    			
    		}else if(accelP==2 ){
    			//System.out.println(c+"R");
    			bot.keyRelease(KeyEvent.VK_W);
    			accelP = 0;
    		}
    		break;
    	
    	case "RC":
    		if(reverseP==0)return ;
    		if(reverseP==1 ){
    			//System.out.println(c+"P");
    			bot.keyPress(KeyEvent.VK_S);
    			
    		}else if(reverseP==2){
    			//System.out.println(c+"R");
    			bot.keyRelease(KeyEvent.VK_S);
    			reverseP = 0;
    		}
    		break;
    		
    	case "N":
    		if(nitroP==0)return ;
    		if(nitroP==1 ){
    			//System.out.println(c+"P");
    			bot.keyPress(KeyEvent.VK_X);
    			bot.keyPress(KeyEvent.VK_W);
    			
    		}else if(nitroP==2){
    			//System.out.println(c+"R");
    			bot.keyRelease(KeyEvent.VK_X);
    			bot.keyRelease(KeyEvent.VK_W);
    			
    			nitroP = 0;
    		}
    		break;
    	
    	case "B":
    		if(brakeP==0)return ;
    		if(brakeP==1 ){
    			//System.out.println(c+"P");
    			bot.keyPress(KeyEvent.VK_SPACE);
    			
    		}else if(brakeP==2){
    			//System.out.println(c+"R");
    			bot.keyRelease(KeyEvent.VK_SPACE);
    			brakeP = 0;
    		}
    		break;
    	
    	case "gear":
    		if(gearP==0)return ;
    		if(gearP==1 ){
    			//System.out.println(c+"P");
    			bot.keyPress(KeyEvent.VK_SHIFT);
    			
    		}else if(gearP==2 ){
    			//System.out.println(c+"R");
    			bot.keyRelease(KeyEvent.VK_SHIFT);
    			gearP = 0;
    		}
    		break;
    	
    	
    	case "select":
    		if(selectP==0)return ;
    		if(selectP==1 ){
    			//System.out.println(c+"P");
    			bot.keyPress(KeyEvent.VK_ENTER);
    			
    		}else if(selectP==2){
    			//System.out.println(c+"R");
    			bot.keyRelease(KeyEvent.VK_ENTER);
    			selectP = 0;
    		}
    		break;
    	
    	
    	case "back":
    		if(backP==0)return ;
    		if(backP==1 ){
    			//System.out.println(c+"P");
    			bot.keyPress(KeyEvent.VK_ESCAPE);
    			
    		}else if(backP==2){
    			//System.out.println(c+"R");
    			bot.keyRelease(KeyEvent.VK_ESCAPE);
    			backP = 0;
    		}
    		break;
    	
    	}
    	
    	
    }
    
}
