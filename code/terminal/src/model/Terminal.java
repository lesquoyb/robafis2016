package model;

import java.util.Observable;

public class Terminal extends Observable {
	Camera camera;
	Robot robot;
	public Manette manette;
	public String message;
	public int depose, phase;
	
	
	public Terminal() {
		camera = new Camera();
		robot = new Robot(this);
		manette = new Manette(robot);
	}
	
	public void listenPad(){
		manette.listen(this);
	}
	public Camera getCamera(){
		return camera;
	}

	public Robot getRobot() {
		return robot;
	}

	public void baliseDepose() {
		depose++;
		setChanged();
		notifyObservers();
	}

	public void setPhase(int i) {
		phase = i;
		setChanged();
		notifyObservers();
	}

	public void setPosition(int posX, int posY, int angle) {
		// TODO Auto-generated method stub
		
	}
}
