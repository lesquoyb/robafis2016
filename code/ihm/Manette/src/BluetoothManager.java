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

	public BluetoothManager() 
	{
		socket = null;	
	}

	public boolean connect(String ip) 
	{
		try {
			socket = new Socket(ip, PORT);

		} catch (Exception e) {
			return false;
		}
		return true;
	}


	public void sendMovement(Movement m) 
	{
		if (socket != null && socket.isConnected() && !socket.isClosed()) {
			try {

				String toSend = "" + (int)(m.leftWheel) + ";" + (int)(m.rightWheel) + "\n";
						
				System.out.println(toSend);			

				socket.getOutputStream().write(toSend.getBytes());

			} catch (Exception e) {
				System.out.println("erreur d'envoi bluetooth: " + e.getMessage());
			}
		}
	}


}
