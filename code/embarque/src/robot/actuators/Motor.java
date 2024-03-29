package robot.actuators;

import lejos.hardware.motor.NXTRegulatedMotor;

public class Motor {
	NXTRegulatedMotor motor;
	double wheelPerimeter = 1;
	double tachosPerCM = 0;
	
	public Motor(String port){
		switch(port){
		case "a": case "A":
			motor = lejos.hardware.motor.Motor.A;
			break;
		case "b": case "B":
			motor = lejos.hardware.motor.Motor.B;
			break;
		case "c": case "C":
			motor = lejos.hardware.motor.Motor.C;
			break;
		case "d": case "D":
			motor = lejos.hardware.motor.Motor.D;
			break;
		}
	}
	
	public int getTachos(){
		return motor.getTachoCount();
	}
	
	public void reset(){
		motor.resetTachoCount();
		motor.stop();
	}
	
	public void stop(){
		motor.stop();
	}

	public void setSpeed(int speed) {		
		if (speed < 0){
			motor.backward();
			speed = -speed;
		} else {
			motor.forward();
		}
		motor.setSpeed(speed);
	}
	
	public void moveDegree(int position, int speed){
		motor.setSpeed(speed);
		motor.rotate(position);
		
		/*setSpeed(720);
		while (Math.abs(motor.getPosition()) < 360);
		
		setSpeed(-20);
		while (Math.abs(motor.getPosition()) > 360);
		
		motor.stop();*/
		motor.flt();
	}
	
	public void setWheelDiameter(double wheelP){
		wheelPerimeter = wheelP * Math.PI;
		tachosPerCM = (int)(360 / wheelPerimeter);
	}
	
	public double getTachoPerCm(){
		return tachosPerCM;
	}

	public int distanceFrom(int start) {
		int curr = getTachos();
		return (int) ((curr-start) / tachosPerCM);
	}

	public void resetTachos() {
		motor.resetTachoCount();
	}
}
