package manette;

import javax.swing.JOptionPane;
import ch.aplu.xboxcontroller.XboxController;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class Main {
	
	private static String DEFAULT_BT_IP = "10.0.1.1";

	public static void main(String[] args) throws InterruptedException {
		
		
		BluetoothManager b = new BluetoothManager();
		b.connect(DEFAULT_BT_IP);
		
		XboxController xc = new XboxController();
		
		//Set DeadZone
		xc.setLeftThumbDeadZone(0.2);
		
		//If no controllers are detected
		if (!xc.isConnected())
	    {
	      JOptionPane.showMessageDialog(null, 
	        "Xbox controller not connected.",
	        "Error", 
	        JOptionPane.ERROR_MESSAGE);
	      
	      
	      xc.release();
	      return;
	    }
		
		XBoxCtrlListener bbox = new XBoxCtrlListener();		
		//add listener to XboxController
		xc.addXboxControllerListener(bbox);
		
		while(true)
		{
			//calcul des valeurs pour chaque roue
			bbox.movement.calculateWheelsSpeed();
			
			//System.out.println("L : " + bbox.movement.leftWheel + " R: " + bbox.movement.rightWheel	);
			
			//envoi
			b.sendMovement( bbox.movement );
			
			Thread.sleep(100);
		}
		
		//xc.release();
	    //System.exit(0);
	}

}
