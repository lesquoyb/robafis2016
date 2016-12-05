package robot.communication;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.lcd.LCD;
import robot.Robot;
import robot.brick.Battery;

public class BluetoothServer {


	private ServerSocket server;
	private Socket socket;
	private BufferedReader bufferReader;
	private BufferedOutputStream bos;
	private Socket connected ;
	private Robot robot;
	int PORT = 5000;

	public BluetoothServer(Robot r) {
		robot = r;
		socket = null;
	}

	public void ackDist(){
		try {
			bos = new BufferedOutputStream(connected.getOutputStream());
			bos.write("balise:\n".getBytes());
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ackPhase1() {
		try {
			bos = new BufferedOutputStream(connected.getOutputStream());
			bos.write("phase2:\n".getBytes());
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean stillAlive(){
		return !(socket.isInputShutdown() && socket.isOutputShutdown());
	}


	public void establishConnection(){

		boolean notConnected = true;

		while(notConnected){
			try{
				server = new ServerSocket (PORT);
				server.setReuseAddress(true);
				LCD.drawString("Waiting connexion ...", 0, 0);
				connected = server.accept();
				server.setSoTimeout(0);
				bufferReader = new BufferedReader(new InputStreamReader (connected.getInputStream()));
				LCD.clear();
				LCD.drawString("Command Center found!", 0, 0);

				notConnected = false;
			}
			catch(Exception e){
				System.out.println("error: " + e.getMessage());
			}
		}
	}

	public void listen() {

		while(true){

			establishConnection();

			String fromclient;

			boolean done = false;
			String tmp;
			while( ! connected.isClosed()) {
				try {
					fromclient = bufferReader.readLine();
					tmp = fromclient.split(":")[0];
					switch(tmp){
					case "s":
						if(!done)
							robot.followLine();
						done = true;
						break;
					case "e":
						robot.error(fromclient.split(":")[1]);
						break;
					case "d":
						robot.distribute();
						break;
					case "f":
						System.out.println("pas implémenté ducon");
						break;
					default:
						int speedR, speedL;

						String[] val = fromclient.split(";");
						speedL = new Integer(val[0]);
						speedR = new Integer(val[1]);

						robot.motorR.setSpeed(speedR);
						robot.motorL.setSpeed(speedL);
						
						break;
					}

				} catch (Exception e) {

					robot.motorR.setSpeed(0);
					robot.motorL.setSpeed(0);
					
					e.printStackTrace();
					System.out.println(e.getMessage() + "reconnection");
					
					try {
						server.close();
					} catch (IOException e1) {}
					
					break;

				}
			}
			System.out.println("connection fermée ");
			
		}

	}

	public void sendBatterie() {
		try {
			bos = new BufferedOutputStream(connected.getOutputStream());
			String data = "batterie:" + Battery.getPercentage() + "\n";
			bos.write(data.getBytes());
			bos.flush();
		} catch (Exception e) {}
	}

	public void sendPosition(float x_pos, float y_pos, float theta) {
		try {
			bos = new BufferedOutputStream(connected.getOutputStream());
			String data = x_pos + ";" + y_pos + ";" + theta + "\n";
			bos.write(data.getBytes());
			bos.flush();
		} catch (Exception e) {}
	}
}
