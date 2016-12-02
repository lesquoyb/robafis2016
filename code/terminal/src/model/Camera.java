package model;

import java.util.Observable;

public class Camera extends Observable {
	
	int numCam = 0;
	String ipCam = "192.168.43.1:8080";
	int width = 800;
	int height = 480;
	
	boolean connected = false;
	

	public boolean isConnected() {
		return connected;
	}

	public void setIp(String ip) {
		ipCam = ip;
	}
	
	public int getNewNumCam() {
		return ++numCam;
	}

	public int getNumCam() {
		return numCam;
	}
	
	public String getIp(){
		return ipCam;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public void reconnect() {
		setChanged();
		notifyObservers();
	}

	public void setConnected(boolean co) {
		connected = co;
		setChanged();
	}

	public void getInstantImage() {
		
	}

}
