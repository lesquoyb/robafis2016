package robot.sensors;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class UltrasonicSensor implements Sensor{
	private EV3UltrasonicSensor sensor;
	private SampleProvider sampler;
	private static float[] sample;

	public UltrasonicSensor(String string) {
		setPort(string);
	}

	@Override
	public void setPort(String port){
		sensor = new EV3UltrasonicSensor(LocalEV3.get().getPort(port));
		sampler = sensor.getDistanceMode();
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
