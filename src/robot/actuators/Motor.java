package robot.actuators;

import lejos.hardware.motor.NXTRegulatedMotor;

public class Motor {
	NXTRegulatedMotor motor;
	
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
}
