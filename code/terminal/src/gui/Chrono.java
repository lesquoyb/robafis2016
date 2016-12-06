package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Chrono  extends JPanel{

    public long start;
    public JLabel label;
    Timer t;

    Color good, bad;
    double incR, incG, incB;
    public static final int MISSION_TIME = 5 * 60;
    
    public Chrono(){
            
        t = new Timer("uptime",true);          
        
        label = new JLabel("00:00");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        good = new Color(0, 255, 100);
        bad = new Color(255, 100, 100);
        incR = good.getRed() - bad.getRed();
        incG = good.getGreen() - bad.getGreen();
        incB = good.getBlue() - bad.getBlue();
        incR /= MISSION_TIME;
        incG /= MISSION_TIME;
        incB /= MISSION_TIME;
        
        
        this.setPreferredSize(new Dimension(250, 50));
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 30));
        
        setLayout(new BorderLayout());
        label.setVisible(true);
        add(label, BorderLayout.CENTER);
        setBackground(new Color(100, 150, 200));
        setVisible(true);    
    }
    
    public void start(){

    	start = System.currentTimeMillis();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                long d = (System.currentTimeMillis() - start)/1000;
                int tmp1 = Math.min(Math.max((int)(good.getRed() - d * incR),  0), 255), 
                    tmp2 = Math.min(Math.max((int) (good.getGreen() - d * incG),  0), 255),
                    tmp3 = Math.min(Math.max((int) (good.getBlue() - d * incB),  0), 255);
                setBackground( new Color(tmp1, tmp2, tmp3) );
                label.setText( twoDigits(d / 60) + ":" + twoDigits(d %60));
                
            }
        }, 0, 1000);
    }
    
    public void end(){
        t.cancel();
    }
    

    public static String twoDigits(long i){
          String ret ="";
          if( i < 10){
              ret =  "0" ;
          }
          return ret + Long.toString(i);
      }    
}
