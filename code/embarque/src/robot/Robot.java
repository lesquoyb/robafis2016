package robot;

import java.util.Vector;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import lejos.utility.Timer;
import lejos.utility.TimerListener;
import robot.actuators.Motor;
import robot.communication.BluetoothServer;
import robot.sensors.ColorReflectSensor;
import robot.sensors.Gyroscope;
import robot.sensors.UltrasonicSensor;

public class Robot {

	ColorReflectSensor colorSensor;
	Gyroscope gyroscope;
	UltrasonicSensor distanceSensor;

	public Motor motorL;
	public Motor motorR;
	public Motor distr;

	BluetoothServer btServer;

	public Robot(){
		colorSensor = new ColorReflectSensor("S1");
		gyroscope = new Gyroscope("S2");
		distr = new Motor("C");
		distr.reset();
		btServer = new BluetoothServer(this);
		
		Timer batterie = new Timer(10000, new TimerListener() {
			@Override
			public void timedOut() {
				btServer.sendBatterie();
			}
		});
		batterie.start();
	}

	public void error(String message){
		LCD.clear();
		LCD.drawString(message, 0, 0);
		Sound.buzz();
		Sound.buzz();
	}

	public void setMotorLeft(String port) {
		motorL = new Motor(port);
	}

	public void setMotorRight(String port) {
		motorR = new Motor(port);
	}

	public void listenMode(){
		new Odometry(this);
		btServer.listen();
	}

	Timer timer;
	boolean dispo = true;
	public void distribute(){
		if(dispo){
			if (timer == null) {
				timer = new Timer(2000, new TimerListener() {
					@Override
					public void timedOut() {
						rstTimer();
					}
				});
			}
			
			timer.start();
			distr.moveDegree(360, 720);
			btServer.ackDist();
			dispo = false;
		}
	}
	
	void rstTimer(){
		timer.stop();
		dispo = true;
	}

	final int MIN_SPEED = 25;
	final int VSIZE = 10;
	final int BLACK = 10;
	final int WHITE = 255;
	final double SMOOTH = 1.07;

	final double KP = 0.08;
	final double KI = 0.03;
	final double KD = 8;

	int basespeed = 500;
	
	public void followLine(){
		int angle_end = 150;
		int angle_after_end = 175;
		double dist_after_end = 35;

		Vector<Double> vInteg = new Vector<Double>(VSIZE);
		final double objective = (BLACK + WHITE )/2;
		final int ERROR_MAX = (int)((objective- BLACK)*VSIZE);

		double iteration_time = 5;

		double error = 0;
		double prior_error = 0;
		double integral = 0;
		double derivative = 0;

		int speed = MIN_SPEED;

		gyroscope.reset();

		for (int i = 0; i < VSIZE; i++){
			double cheat = objective-BLACK;
			vInteg.addElement(cheat);
			integral += cheat;
		}

		while ( gyroscope.getValue() < angle_end ) {
			//System.out.println((int)colorSensor.getValue());

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

			Delay.msDelay((int)iteration_time);

		}
		// On se met bien pour rouler
		motorL.setSpeed(0);
		motorR.setSpeed(0);
		
		while ( gyroscope.getValue() < angle_after_end ) {
			motorL.setSpeed(100);
			motorR.setSpeed(-100);
		}
		motorL.setSpeed(0);

		int st_l = motorL.getTachos();
		while ( Math.abs(motorL.distanceFrom(st_l)) < dist_after_end ){
			motorL.setSpeed(200);
			motorR.setSpeed(200);

		}

		motorL.setSpeed(0);
		motorR.setSpeed(0);
		
		Sound.beep();
		btServer.ackPhase1();
	}

	public void setWheelSize(double d) {
		motorL.setWheelDiameter(d);
		motorR.setWheelDiameter(d);
	}
}
