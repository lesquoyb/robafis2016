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
	
	final int MIN_SPEED = 25;
	final int VSIZE = 10;
	final int BLACK = 10;
	final int WHITE = 255;
	final double SMOOTH = 1.07;
	
	final double KP = 0.08;
	final double KI = 0.03;
	final double KD = 8;
	
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
		int speed = MIN_SPEED;
		
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
			
			double prop = KP * error + KI * integral + KD * derivative;
			
			prior_error = error;			
			
			int yolo = (int)( basespeed - Math.abs(integral * basespeed / ERROR_MAX) );
			
			yolo = Math.max(yolo, Math.max((int)(speed/SMOOTH), MIN_SPEED));
			
			speed = Math.min( yolo, (int)(SMOOTH*speed));
			
			
			motorL.setSpeed((int) (speed - prop));
			motorR.setSpeed((int) (speed + prop));
			
			
			
			//System.out.println("speed: " + (int)speed);
			//System.out.println("corre: " + (int)todo);
			//System.out.println(error);
			
			Delay.msDelay((int)iteration_time);
			
		}
	}
}
