package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;

public class Terminal extends Observable {
	Camera camera;
	Robot robot;
	public Manette manette;
	public String message;
	public int depose, phase;
	
	public ArrayList<Point> balises = new ArrayList<>();

	public int batterie;
	
	public int posx, posy, theta;


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
		
		balises.add(new Point(posx, posy));
	}

	public void setPhase(int i) {
		phase = i;
		setChanged();
		notifyObservers();
	}

	public void setPosition(int posX, int posY, int angle) {
		posx = posX;
		posy = posY;
		theta = angle;
		
		//System.out.println(posX + " " + posY + " " + angle);
	}

	public void setBatterie(int integer) {
		if ( batterie != integer){
			batterie = integer;
			setChanged();
			notifyObservers();
		}
	}
}
