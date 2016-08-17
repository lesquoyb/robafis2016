package robot.actuators;

import lejos.utility.Delay;

public class Sound {
	static int [] inst = {4, 25, 500, 7000, 5};
	
	public static void beep(int times, int length){
		for (int i = 0; i < times; i++){
			lejos.hardware.Sound.playNote(inst , 440, length);
			Delay.msDelay(100);
		}
	}
}
