package main;

import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class Robot {

	
	public final NXTRegulatedMotor motorL = Motor.B;
	public final NXTRegulatedMotor motorR = Motor.A;
	public final NXTRegulatedMotor armsMotor = Motor.D;
	
	public BluetoothServer server;

	public Robot() throws Exception{

			initBluetooth();
	}
	

	private void initBluetooth(){
		server = new BluetoothServer(this);
		server.establishConnection();
	}
	
	public void close(){

	}
}
