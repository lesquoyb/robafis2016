

import ch.aplu.xboxcontroller.XboxControllerListener;


public class XBoxCtrlListener implements XboxControllerListener{

	public Movement movement;
	public boolean connected;
	
	public XBoxCtrlListener() {
		movement = new Movement();
		connected = false;
	}
	
	@Override
	public void isConnected(boolean connected) {
		this.connected = connected;	//ne pas toucher à celle là
	}

	@Override
	public void buttonA(boolean pressed) {
		movement.boutonA = pressed;
	}

	@Override
	public void buttonB(boolean pressed) {
	}

	@Override
	public void buttonX(boolean pressed) {
		
	}

	@Override
	public void buttonY(boolean pressed) {
		
	}

	@Override
	public void back(boolean pressed) {
		
	}

	@Override
	public void start(boolean pressed) {
	
	}

	@Override
	public void leftShoulder(boolean pressed) {
		movement.rotateLeft = pressed;
	}

	@Override
	public void rightShoulder(boolean pressed) {
		movement.rotateRight = pressed;
	}

	@Override
	public void leftThumb(boolean pressed) {

	}

	@Override
	public void rightThumb(boolean pressed) {
	}

	@Override
	public void dpad(int direction, boolean pressed) {
		
	}

	@Override
	public void leftTrigger(double value) {
		//On se servira de ca pour faire marche arrière (ou freiner?)
		movement.reverse = value;
	}

	@Override
	public void rightTrigger(double value) {
		//gachette droite pour l'accélération
		movement.speed = value;
	}

	@Override
	public void leftThumbMagnitude(double magnitude) {
		//cordonnées polaire : longueur
		movement.magnitude = magnitude;
	}

	@Override
	public void leftThumbDirection(double direction) {
		//cordonnées polaire : angle
		movement.direction = direction;
	}

	@Override
	public void rightThumbMagnitude(double magnitude) {
		
	}

	@Override
	public void rightThumbDirection(double direction) {
		
	}

	
	


	
}
