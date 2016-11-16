package robot;

import java.util.Vector;

import lejos.utility.Delay;
import robot.actuators.Motor;
import robot.sensors.ColorReflectSensor;
import robot.sensors.Gyroscope;
import robot.sensors.UltrasonicSensor;

public class Robot {
	
	ColorReflectSensor colorSensor;
	Gyroscope gyroscope;
	UltrasonicSensor distanceSensor;
	
	Motor motorL;
	Motor motorR;
	
	public Robot(){
		colorSensor = new ColorReflectSensor("S1");
		gyroscope = new Gyroscope("S2");
	}
	
	public void setMotorLeft(String port) {
		motorL = new Motor(port);
	}
	
	public void setMotorRight(String port) {
		motorR = new Motor(port);
	}
	
	final int MIN_SPEED = 0;
	final int VSIZE = 4;
	final int BLACK = 10;
	final int WHITE = 140;
	final int SMOOTH = 20;
	
	final double KP = 0.002;
	final double KI = 0.; // Laisser 0, ça pue la mort sinon
	final double KD = 1.;
	
	public void followLine(int basespeed, int end_color, int duration){
		
		
		Vector<Double> vInteg = new Vector<Double>(VSIZE);
		final double objective = (BLACK + WHITE )/2;
		final int ERROR_MAX = (int)((objective- BLACK)*VSIZE);
		
		double iteration_time = 5;
		
		double error = 0;
		double prior_error = 0;
		double integral = 0;
		double derivative = 0;
		
		int virage = 0;
		int speed = 0;
		
		gyroscope.reset();
		
		for (int i = 0; i < VSIZE; i++){
			double cheat = objective-BLACK;
			vInteg.addElement(cheat);
			integral += cheat;
		}
		
		while ( true ) {
			//System.out.println((int)colorSensor.getValue());
			
			/*double gyro = gyroscope.getValue() % 90;
			if (gyro > 45) gyro = 90 - gyro;
			gyro /= 45;
			*/
			
			error = (objective - colorSensor.getValue());
			
			integral += error - vInteg.firstElement();

			vInteg.addElement(error);
			vInteg.remove(0);
			
			derivative = (error - prior_error);
			
			double prop = KP * Math.abs(integral) * error + KI * integral + KD * derivative;
			
			prior_error = error;			
			
			speed = ((SMOOTH-1) * speed ) / SMOOTH + Math.max((int) (basespeed - (Math.abs(integral)/ERROR_MAX * basespeed) ) / SMOOTH, MIN_SPEED);
			
			motorL.setSpeed((int) (speed - prop));
			motorR.setSpeed((int) (speed + prop));
			
			
			
			//System.out.println("speed: " + (int)speed);
			//System.out.println("corre: " + (int)todo);
			//System.out.println(error);
			
			Delay.msDelay((int)iteration_time);
			
		}
	}
}
