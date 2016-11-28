package model;

import java.util.Observable;

import manette.BluetoothManager;
import manette.Movement;

public class Robot extends Observable {
	String ip = "10.0.1.1";
	boolean connected = false;
	
	BluetoothManager b = new BluetoothManager();
	
	public Robot() {
		reconnect();
	}

	public void doMovement(Movement movement) {
		if (connected)
			b.sendMovement(movement);
	}
	
	public void reconnect(){
		connected = b.connect(ip);
		setChanged();
		notifyObservers();
	}
	
	public boolean isConnected(){
		return connected;
	}
	
	public String getIP(){
		return ip;
	}
	
	public void setIP(String newIP){
		ip = newIP;
	}
	
	public void refresh(){
	
	}
}
