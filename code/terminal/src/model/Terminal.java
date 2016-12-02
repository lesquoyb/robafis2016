package model;

public class Terminal {
	Camera camera;
	Robot robot;
	public Manette manette;
	public String message;
	public int depose;
	
	
	public Terminal() {
		camera = new Camera();
		robot = new Robot();
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
}
