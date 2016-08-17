package robot.sensors;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;

public class Gyroscope implements Sensor{
	
	private EV3GyroSensor gyro;
	private SampleProvider gyroSampler;
	private static float[] gyroSample;
	
	public Gyroscope() {}
	
	public Gyroscope(String port){
		setPort(port);
	}
	
	@Override
	public float getValue(){
		gyroSampler.fetchSample(gyroSample, 0);
		return gyroSample[0];
	}

	@Override
	public void setPort(String port) {
		gyro = new EV3GyroSensor(LocalEV3.get().getPort(port));
		gyroSampler =  gyro.getAngleMode();
		gyroSample = new float[1];
	}

	@Override
	public void reset() {
		gyro.reset();
	}

	@Override
	public void close() {
		gyro.close();
	}

}
