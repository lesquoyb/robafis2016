import robot.Robot;
import robot.actuators.Sound;

public class Main {
	public static void main(String... args){
		
		Robot robot = new Robot();
		
		robot.setMotorLeft("A");
		robot.setMotorRight("B");
		
		robot.followLine(200, 0, 0, 0);

	}
}
