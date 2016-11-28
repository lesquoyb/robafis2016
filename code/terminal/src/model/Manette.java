package model;

import javax.swing.JOptionPane;

import ch.aplu.xboxcontroller.XboxController;
import manette.XBoxCtrlListener;

public class Manette implements Runnable{

	Robot robot;
	XboxController xc = new XboxController();
	
	public Manette(Robot bot) {

		this.robot = bot;
		//Thread t = new Thread(this);
		//t.start();
	}

	@Override
	public void run() {
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
			robot.doMovement( bbox.movement );

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
