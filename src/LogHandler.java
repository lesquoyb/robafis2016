import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import lejos.utility.Delay;
import robot.sensors.Sensor;

public class LogHandler {

	Sensor sensor;
	File file;

	public LogHandler(String filename, Sensor s){
		try {
		file = new File(filename);
		sensor = s;
		
		out = new FileOutputStream(file);
		
		dataOut = new DataOutputStream(out);
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	final int DELAY = 10;
	FileOutputStream out;



	public void write(){
		try {
			String data = sensor.getValue() + "\n";
			dataOut.write(data.getBytes());
			System.out.println(data);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}


	public void write(String s){
		try {
			String data = sensor.getValue() + " " + s + "\n";
			dataOut.write(data.getBytes());
			System.out.println(data);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}



	

	DataOutputStream dataOut;


	public void writeContinuous(){
		try {

			while(true){
				dataOut.write(("" +sensor.getValue() + "\n").getBytes());


				Delay.msDelay(DELAY);
			}


		} catch(IOException e) {
			System.err.println("Failed to create output stream");
			System.exit(1);
		}



	}
	Thread t ;
	public void startLogging(){

		t = new Thread(new Runnable() {

			@Override
			public void run() {
				writeContinuous();
			}
		});
		t.start();


	}

	public void endLogging(){
		t.interrupt();
		try {
			out.close();


		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
