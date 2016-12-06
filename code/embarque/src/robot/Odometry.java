package robot;

import java.util.Timer;
import java.util.TimerTask;

public class Odometry{

	final static float TWO_PI = 3.1415926535f * 2;
	final static float RAD_TO_DEG = 57.2958f;

	float WHEEL_BASE = 12.5f; // Distance entre les roues	
	
	
	
	float theta;                    /* bot heading */
	float X_pos;                    /* bot X position in inches */
	float Y_pos;                    /* bot Y position in inches */

	/* ----------------------------------------------------------------------- */
	/* using these local variables */

	float left_cm;
	float right_cm;
	float cm;

	int L_ticks, R_ticks;
	int last_left, last_right;

	
	Robot bot;
	public Odometry(Robot robot) {
		bot = robot;
		
		Timer tm = new Timer(true);
		tm.schedule(sendPos, 0, 500);
	}

	TimerTask sendPos = new TimerTask() {
		
		@Override
		public void run() {
			/* determine how many ticks since our last sampling? */
			L_ticks = bot.motorL.getTachos(); 	
			R_ticks = bot.motorR.getTachos();

			/* and update last sampling for next time */
			bot.motorL.resetTachos();
			bot.motorR.resetTachos();

			/* convert the longs to floats */
			left_cm = L_ticks;
			right_cm = R_ticks;

			/* and convert ticks to inches */
			left_cm /= bot.motorL.getTachoPerCm();
			right_cm /= bot.motorR.getTachoPerCm();

			/* calculate distance we have traveled since last sampling */
			cm = (left_cm + right_cm) / 2f;

			/* accumulate total rotation around our center */
			/*theta += (left_cm - right_cm) / WHEEL_BASE;
			if (theta > TWO_PI)
				theta -= TWO_PI;
			if (theta < 0)
				theta += TWO_PI;
			*/
			
			theta = (float) Math.toRadians(bot.gyroscope.getValue());

			/* and clip the rotation to plus or minus 360 degrees */
			//theta -= (theta / 2*Math.PI) * 2*Math.PI;

			/* now calculate and accumulate our position in inches */
			Y_pos += (cm * (Math.cos(theta))); 
			X_pos += (cm * (Math.sin(theta))); 

			bot.btServer.sendPosition(X_pos, Y_pos, theta*RAD_TO_DEG);
		}
	};

}
