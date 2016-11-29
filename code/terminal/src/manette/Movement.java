package manette;

public class Movement {

	private static final float ROBOT_STEERING_RATIO_LIMIT = 0.7f;
	private static final float ROBOT_SPEED_LIMIT = 720; //valeur max d'une roue
	private static final float ROBOT_ROTATE_SPEED = 500;
	
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
		String data = "";
		
		 // Exemple de gestion pour le bouton A (Faite comme vous voulez, c'est juste un exemple ici ^^)
		if (boutonA) {
			data += "Bouton A \n";
		}
		
		data += "Acceleration : " + speed + "\n";
		data += "Reverse : " + reverse + "\n";
		data += "Direction : " + direction + "\n";
		data += "Magnitude : " + magnitude + "\n";
		
		
		return "" + (int)(leftWheel) + ";" + (int)(rightWheel) + "\n"
				+ ((boutonA) ? "d" : "")
				+ ((boutonStart) ? "s" : "");
		
	}
	
	
	


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movement other = (Movement) obj;
		if (boutonA != other.boutonA)
			return false;
		if (Double.doubleToLongBits(direction) != Double.doubleToLongBits(other.direction))
			return false;
		if (Double.doubleToLongBits(leftWheel) != Double.doubleToLongBits(other.leftWheel))
			return false;
		if (Double.doubleToLongBits(magnitude) != Double.doubleToLongBits(other.magnitude))
			return false;
		if (Double.doubleToLongBits(reverse) != Double.doubleToLongBits(other.reverse))
			return false;
		if (Double.doubleToLongBits(rightWheel) != Double.doubleToLongBits(other.rightWheel))
			return false;
		if (rotateLeft != other.rotateLeft)
			return false;
		if (rotateRight != other.rotateRight)
			return false;
		if (Double.doubleToLongBits(speed) != Double.doubleToLongBits(other.speed))
			return false;
		return boutonStart == other.boutonStart;
	}

	
}
