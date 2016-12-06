import robot.Odometry;
import robot.Robot;

public class Main {
	public static void main(String... args){
		/*
		Motor distr = new Motor("C");

		distr.reset();
		for (int i = 0; i <= 5; i++){
			distr.moveDegree(360, 720);
			Delay.msDelay(3000);
		}
		*/

		Robot robot = new Robot();


		robot.setMotorLeft("B");
		robot.setMotorRight("A");
		robot.setWheelSize(6.88);

		new Odometry(robot);
		robot.listenMode();
		
		//robot.followLine(700, 0, 0);
		

		/*LCD.clear();
		String[] menu = new String[]{"Gyro", "drift", "noir et blanc", "couleur", "distance", "pression"};

		TextMenu m = new TextMenu(menu);
		int index = m.select();
		Sensor sensor = null;

		switch(menu[index]){
		case "Gyro": 
			sensor = new Gyroscope("S1");
			break;
		case "drift":
			sensor = new Gyroscope("S1");			
			break;
		case "noir et blanc":
			sensor = new ColorReflectSensor("S1");
			break;
		case "couleur":
			sensor = new RGBSensor("S1");
			break;
		case "distance":
			sensor = new UltrasonicSensor("S1");
			break;
		case "pression":
			sensor = new TouchSensor("S1");
			break;
		}

		Delay.msDelay(1000);

		LCD.clear();

		LogHandler l = new LogHandler(menu[index]+".txt", sensor);


		System.out.println("starting");

		while(true){
			long time = 0;
			if(menu[index].equals("Gyro")){
				Button.ENTER.waitForPress();
				sensor.reset();
				time = System.currentTimeMillis();
			}

			if(menu[index].equals("drift")){

				sensor.reset();
				while(sensor.getValue() == 0 ){
					Delay.msDelay(1000);
				}
				l.write();
				l.write("The end");
				Sound.beep(2, 10);

			} else {
				Button.ENTER.waitForPress();

				if(menu[index].equals("Gyro")){


			}
				else{
					l.write();		
				}
			}
			l.write("Battery Voltage: " + Battery.getVoltage());
		}
		 */
	}
}
