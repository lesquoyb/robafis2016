package model;

import java.util.Observable;

import manette.BluetoothManager;
import manette.Movement;

public class Robot extends Observable {
	String ip = "10.0.1.1";
	boolean btConnected;
	boolean refreshing;
	
	BluetoothManager b = new BluetoothManager();
	
	public Robot() {
		btConnected = false;
		refreshing = false;
	}

	public void doMovement(Movement movement) {
		if (btConnected)
			b.sendMovement(movement);
	}
	
	public void reconnectBT(){
		if (refreshing) return;
		
		Thread reco = new Thread(){
			@Override
			public void run() {
				refreshing = true;
				setChanged();
				notifyObservers();
				
				btConnected = b.connect(ip);
				
				refreshing = false;
				setChanged();
				notifyObservers();
			}
		};
		reco.start();
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
	
	public boolean isRefreshing(){
		return refreshing;
	}
}
