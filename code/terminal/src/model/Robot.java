package model;

import manette.BluetoothManager;
import manette.Movement;

public class Robot {
	private static String DEFAULT_BT_IP = "10.0.1.1";
	boolean connected = false;
	
	BluetoothManager b = new BluetoothManager();
	
	public Robot() {
		connected = b.connect(DEFAULT_BT_IP);
	}

	public void sendMovement(Movement movement) {
		b.sendMovement(movement);
	}
	
	public boolean isConnected(){
		return connected;
	}
}
