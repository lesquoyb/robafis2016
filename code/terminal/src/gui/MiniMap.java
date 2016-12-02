package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Terminal;

public class MiniMap extends JPanel{
	HashMap<Coords, Integer> minimap = new HashMap<>();
	Terminal terminal;
	CameraPanel camera;
	
	// distance du centre de la vue a l'axe des roues
	int distWheelX = 4;
	int distWheelY = 40;
	int angle = 0;
	
	// NB cm par rapport au centre de vue de la camera / position pixel equivalent a l'ecran
	HashMap<Coords, Coords> corresp = new HashMap<>();
	
	
	
	public MiniMap(CameraPanel camera, Terminal terminal) {
		this.terminal = terminal;
		this.camera = camera;
		
		corresp.put(new Coords(0, 0), new Coords(0, 0));
	}

	public void update() {
		BufferedImage image = camera.getImage();
		
		//temp
		try {
			image = ImageIO.read(new File("assets/images/rouge.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		
		//double angle = terminal.getRobot().getAngle();
		double angle = Math.toRadians(this.angle);
		
		
		for( Coords co : corresp.keySet()){
			double b = distWheelY;
			double a = Math.sqrt(distWheelX*distWheelX + distWheelY+distWheelY);
			double c = Math.sqrt((distWheelX + co.x)*(distWheelX + co.x) + (distWheelY + co.y)*(distWheelY + co.y));
			
			double alpha = Math.acos((b*b + c*c - a*a)/(2*b*c));
			int mapx = (int)(terminal.getRobot().getPosX() + Math.sin(angle + alpha) * c) ;
			int mapy = (int)(terminal.getRobot().getPosY() + Math.cos(angle + alpha) * c) ;
			
			int imgx = corresp.get(co).x;
			int imgy = corresp.get(co).y;
			int color = image.getRGB(imgx, imgy);
			minimap.put(new Coords(mapx, mapy), color);
			
			System.out.println(mapx + " -- " + mapy);
		}
	}
}

class Coords {
	int x, y;
	
	public Coords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int hashCode() {
		return x + y*20011;
	}
}