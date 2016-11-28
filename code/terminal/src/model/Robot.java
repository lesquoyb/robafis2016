package model;

import java.util.Observable;

import manette.BluetoothManager;
import manette.Movement;

public class Robot extends Observable {
	String ip = "10.0.1.1";
	boolean btConnected;
	
	BluetoothManager b = new BluetoothManager();
	
	public Robot() {
		btConnected = false;
	}

	public void doMovement(Movement movement) {
		if (btConnected)
			b.sendMovement(movement);
	}
	
	public void reconnectBT(){
		btConnected = b.connect(ip);
		setChanged();
		notifyObservers();
	}
	
	public boolean isConnectedToBT(){
		return btConnected;
	}
	
	public String getIPBT(){
		return ip;
	}
	
	public void setIPBT(String newIP){
		ip = newIP;
	}
	
	public void refresh(){
	
	}
}
