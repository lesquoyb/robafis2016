package robot;

import robot.actuators.Motor;
import robot.sensors.ColorSensor;
import robot.sensors.Gyroscope;
import robot.sensors.UltrasonicSensor;

public class Robot {
	
	ColorSensor colorSensor;
	Gyroscope gyroscope;
	UltrasonicSensor distanceSensor;
	
	Motor motorL;
	Motor motorR;
	
	public Robot(){
		colorSensor = new ColorSensor("S1");
	}
	
	public void setMotorLeft(String port) {
		motorL = new Motor(port);
	}
	
	public void setMotorRight(String port) {
		motorR = new Motor(port);
	}
	
	public void followLine(int basespeed, int objective, int end_color, int duration){
		double kp = 0.1, ki = 0.001, kd = 0.002;
		
		int error = 0;
		int sumerror = 0;
		
		int virage = 0;
		
		while ( true ) {
			error = objective - (int)colorSensor.getValue();
			sumerror *= 0.8;
			sumerror += error;
			
			virage = (int) (kp * error + ki * sumerror);
			
			motorR.setSpeed(basespeed + virage);
			motorL.setSpeed(basespeed - virage);
		}
	}
}
