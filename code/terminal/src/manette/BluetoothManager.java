package manette;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import model.Terminal;


public class BluetoothManager {

	private Socket socket;
	private BufferedReader bufferReader;
	int PORT = 5000;
	boolean alive;
	
	Terminal terminal;



	public BluetoothManager(Terminal terminal) {
		socket = null;	
		alive = false;
		
		this.terminal = terminal;
	}

	public boolean connect(String ip) {
		try {
			socket = new Socket(ip, PORT);
			alive = true;
			listen();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean stillAlive(){
		return alive;
	}

	public void listen(){
		System.out.println("LISTEN");
		Thread t = new Thread(){
			@Override
			public void run() {
				System.out.println("Started");
				try {
					bufferReader = new BufferedReader(new InputStreamReader (socket.getInputStream()));
					String tmp;
					while (stillAlive()){
						String fromclient = bufferReader.readLine();
						String[] val;
						tmp = fromclient.split(":")[0];
						switch(tmp){
						case "balise":
							terminal.baliseDepose();
							break;
						case "batterie":
							val = fromclient.split(":");
							terminal.setBatterie(new Integer(val[1]));
							break;
						case "phase2":
							terminal.setPhase(2);
							break;
						default:
							int posX, posY, angle;

							val = fromclient.split(";");
							posX = (new Float(val[0])).intValue();
							posY = (new Float(val[1])).intValue();
							angle = (new Float(val[2])).intValue();
							
							terminal.setPosition(posX, posY, angle);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		t.run();
	}


	public void sendMovement(Movement m) {
		if (socket != null && socket.isConnected() && !socket.isClosed()) {
			try {
				socket.getOutputStream().write(m.toString().getBytes());
			} catch (Exception e) {
				System.out.println("erreur d'envoi bluetooth: " + e.getMessage());
				alive = false;
			}
		}
	}


}
