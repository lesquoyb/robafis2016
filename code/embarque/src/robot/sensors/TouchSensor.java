package robot.sensors;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;

public class TouchSensor implements Sensor{
	private EV3TouchSensor sensor;
	private SampleProvider sampler;
	private static float[] sample;

	public TouchSensor(String s){
		setPort(s);
	}
	
	@Override
	public void setPort(String port){
		sensor = new EV3TouchSensor(LocalEV3.get().getPort(port));
		sampler = sensor.getTouchMode();
		sample = new float[1];
	}
	
	@Override
	public float getValue(){
		sampler.fetchSample(sample, 0);
		return sample[0];
	}
	
	@Override
	public void close(){
		sensor.close();
	}

	@Override
	public void reset() {
		
	}
}
