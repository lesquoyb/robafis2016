package robot.sensors;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

public class ColorSensor implements Sensor {
	private SampleProvider colorSampler;
	private SampleProvider redSampler;
	private EV3ColorSensor colorSensor;
	private float[] sample;
	
	public ColorSensor(String port) {
		setPort(port);
	}

	@Override
	public void setPort(String port){
		colorSensor = new EV3ColorSensor(LocalEV3.get().getPort(port));
		redSampler = colorSensor.getRedMode();
		colorSampler = colorSensor.getColorIDMode();
		sample = new float[3];
	}
	
	public int readColor(){
		colorSampler.fetchSample(sample, 0);
		return (int) sample[0];
	}
	
	@Override
	public float getValue(){
		redSampler.fetchSample(sample, 0);
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
