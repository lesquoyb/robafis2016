package robot.sensors;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class RGBSensor implements Sensor{

	private EV3ColorSensor colorSensor;
	private SensorMode colorSampler;
	private float[] sample;

	public RGBSensor(String port) {
		setPort(port);
	}

	@Override
	public void setPort(String port){
		colorSensor = new EV3ColorSensor(LocalEV3.get().getPort(port));
		colorSampler = colorSensor.getColorIDMode();
		sample = new float[1];
	}
	
	@Override
	public float getValue(){
		colorSampler.fetchSample(sample, 0);
		return (int) (sample[0]*255);
	}
	
	@Override
	public void close(){
		colorSensor.close();
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
