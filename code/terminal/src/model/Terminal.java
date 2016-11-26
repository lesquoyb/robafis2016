package model;

public class Terminal {
	Camera camera;
	Robot robot;
	Manette manette;
	
	public Terminal() {
		camera = new Camera();
		//robot = new Robot();
		//manette = new Manette(robot);
	}
	
	public Camera getCamera(){
		return camera;
	}
}
