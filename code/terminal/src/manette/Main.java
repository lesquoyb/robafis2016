package manette;

import javax.swing.JOptionPane;
import ch.aplu.xboxcontroller.XboxController;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class Main {
	
	private static String DEFAULT_BT_IP = "10.0.1.1";

	public static void main(String[] args) throws InterruptedException {
		
		
		final BluetoothManager b = new BluetoothManager();
		b.connect(DEFAULT_BT_IP);
		
		XboxController xc = new XboxController(200,200);
		
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
		
		final XBoxCtrlListener bbox = new XBoxCtrlListener();		
		//add listener to XboxController
		xc.addXboxControllerListener(bbox);
		Thread t = new Thread(new Runnable() {
			

			@Override
			public void run() {
				while(true)
				{
					//calcul des valeurs pour chaque roue
					bbox.movement.calculateWheelsSpeed();
					
					//System.out.println("L : " + bbox.movement.leftWheel + " R: " + bbox.movement.rightWheel	);
					
					//envoi
					b.sendMovement( bbox.movement );
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					
			}
		});
		t.start();
		//xc.release();
	    //System.exit(0);
	}

}
