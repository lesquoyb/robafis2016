package model;

import ch.aplu.xboxcontroller.XboxController;
import gui.statusPanel.ManettePan;
import manette.XBoxCtrlListener;

public class Manette{

	Robot robot;
	public XboxController xc;
	XBoxCtrlListener bbox;
	ManettePan view;
	
	public Manette(Robot bot) {
		 xc = new XboxController();
		this.robot = bot;
	}

	public void initPad(){
		xc.setLeftThumbDeadZone(0.2);
		bbox = new XBoxCtrlListener();		
		xc.addXboxControllerListener(bbox);	
		view.refreshView();
	}
	
	public void setView(ManettePan v){
		view = v;
	}
	public void listen(Terminal terminal) {

		initPad();
		
		while(true){
			
			if(xc.isConnected()){
				bbox.movement.calculateWheelsSpeed();
				bbox.movement.error = terminal.message;
				terminal.message = "";
			//	System.out.println("L : " + bbox.movement.leftWheel + " R: " + bbox.movement.rightWheel	);
				
				if (bbox.movement.boutonStart)
					terminal.setPhase(1);
				
				robot.doMovement( bbox.movement);
				
			}			
			view.refreshView();
			

			try {
				Thread.sleep(100);
			} 
			catch (InterruptedException e) {e.printStackTrace();}
		}





	}
}
