package manette;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class BluetoothManager {	

	private Socket socket;
	private BufferedReader bufferedReader;
	int PORT = 5000;



	public BluetoothManager() {
		socket = null;	
	}

	public boolean connect(String ip) {
		try {
			socket = new Socket(ip, PORT);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean stillAlive(){
		return !(socket.isInputShutdown() && socket.isOutputShutdown());
	}


	public void sendMovement(Movement m) {
		if (socket != null && socket.isConnected() && !socket.isClosed()) {
			try {

				socket.getOutputStream().write(m.toString().getBytes());

			} catch (Exception e) {
				System.out.println("erreur d'envoi bluetooth: " + e.getMessage());

			}
		}
	}


}
