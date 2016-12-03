package robot.brick;

public class Battery {
	private static int MIN_BATTERY = 7000;
	
	public static float getVoltage(){
		return lejos.hardware.Battery.getVoltage();
	}
	
	public static int getPercentage(){
		int milli = lejos.hardware.Battery.getVoltageMilliVolt();
		
		int perc = Math.min(100, (milli - MIN_BATTERY) / 10);
		
		return perc;
	}
}
