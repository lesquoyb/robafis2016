package model;

import java.util.Observable;

import manette.BluetoothManager;
import manette.Movement;

public class Robot extends Observable {
	String ip = "10.0.1.1";
	boolean btConnected;
	volatile boolean refreshing;
	BluetoothManager b = new BluetoothManager();

	public Robot() {
		btConnected = false;
		refreshing = false;
	}

	public void doMovement(Movement movement, Terminal terminal) {
		if (btConnected)
			b.sendMovement(movement, terminal);
	}

	public boolean isRefreshing(){
		return refreshing;
	}
	
	synchronized public void reconnectBT(){	
		
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
		btConnected = b.stillAlive();
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

	public double getAngle() {
		return 0;
	}

	public int getPosX() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getPosY() {
		// TODO Auto-generated method stub
		return 0;
	}
}
