package robot.communication;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.lcd.LCD;
import robot.Robot;

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
		establishConnection();
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
	
	public boolean stillAlive(){
		return !(socket.isInputShutdown() && socket.isOutputShutdown());
	}

	
	public void establishConnection(){
		
		boolean notConnected = true;
		while(notConnected){
			try{
				server = new ServerSocket (PORT);
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
	
	
	public void sendColor(int color){
		if(connected != null){
			try {
				bos = new BufferedOutputStream(connected.getOutputStream());
				bos.write(("c:"+Integer.toString(color) + "\n").getBytes());
				bos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	static final double trigger_dead_zone = 0.4;
	int MAX_TRIGGER_SPEED = 400;
	
	public void listen() {
		
		String fromclient;
		try {
			bos = new BufferedOutputStream(connected.getOutputStream());
			bos.write( ("ready\n").getBytes());
			bos.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while( ! connected.isClosed()) {
			try {
				fromclient = bufferReader.readLine();
				switch(fromclient.split(":")[0]){
				case "s":
					robot.followLine(700);
					break;
				case "e":
					robot.error(fromclient.split(":")[1]);
					break;
				case "d":
					robot.distribute();
					break;
				default:
					int speedR, speedL;
					
					String[] val = fromclient.split(";");
					speedL = new Integer(val[0]);
					speedR = new Integer(val[1]);
					System.out.println("" + speedL + " " + speedR);
					
					robot.motorL.setSpeed(speedL);
					robot.motorR.setSpeed(speedR);
					
					break;
				}
				
				bos.flush();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	
	private double apply_trigger_dead_zone(double x){
		if(x > -trigger_dead_zone && x < trigger_dead_zone)
			return 0;
		return x - trigger_dead_zone;
	}

}
