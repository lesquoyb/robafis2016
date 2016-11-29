package manette;

public class Movement {

	private static final float ROBOT_STEERING_RATIO_LIMIT = 0.7f;
	private static final float ROBOT_SPEED_LIMIT = 600; //valeur max d'une roue
	private static final float ROBOT_ROTATE_SPEED = 100;
	
	//Inputs Manette
	public boolean boutonA;
	public boolean boutonStart;
	public double speed;
	public double reverse;
	public double direction;
	public double magnitude;
	
	public boolean rotateLeft;
	public boolean rotateRight;
	
	//Movement calcul�
	public double leftWheel;
	public double rightWheel;
	
	public String error;
	
	
	public void calculateWheelsSpeed(){
		
		double angleRadian = Math.toRadians(direction);
		double limitedSpeed = (speed - reverse) * ROBOT_SPEED_LIMIT;
		
		//R�cup�ration de la cordonn�es X � partir de la cordonn�e polaire
		double x = magnitude * Math.sin(angleRadian);
		//limite l'angle de braquage
		x = x * ROBOT_STEERING_RATIO_LIMIT;
				
		//left side
		if(x < 0){
			leftWheel = limitedSpeed * (1+x);
			rightWheel = limitedSpeed;
		}
		else{
			leftWheel = limitedSpeed;
			rightWheel = limitedSpeed * (1-x);
		}	
		
		//L'action de rotation est prioritaire sur le reste		
		if(rotateLeft){
			leftWheel = -ROBOT_ROTATE_SPEED;
			rightWheel = ROBOT_ROTATE_SPEED;
		}
		else if(rotateRight){
			leftWheel = ROBOT_ROTATE_SPEED;
			rightWheel = -ROBOT_ROTATE_SPEED;
		}
		leftWheel = - leftWheel;
		rightWheel = - rightWheel;
	}	
	
	@Override
	public String toString() {
		String tmp = error;
		error = "";
		return "" + (int)(leftWheel) + ";" + (int)(rightWheel) + "\n"
				+ ((boutonA) ? "d\n" : "")
				+ ((boutonStart) ? "s\n" : "")
				+ ((! tmp.equals("")) ? "e:"+tmp+"\n" :"" );
		
	}
	
	
	
	
}
