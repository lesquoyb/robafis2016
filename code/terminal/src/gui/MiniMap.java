package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Terminal;

@SuppressWarnings("serial")
public class MiniMap extends JPanel{
	ConcurrentHashMap<Point, Integer> minimap = new ConcurrentHashMap<>();
	Terminal terminal;
	CameraPanel camera;

	BufferedImage buffer;

	// distance du centre de la vue a l'axe des roues
	int distWheel = 30;

	int minX, minY, maxX, maxY;

	// NB cm par rapport au centre de vue de la camera / position pixel equivalent a l'ecran
	HashMap<Point, Point> corresp = new HashMap<>();



	public MiniMap(CameraPanel camera, Terminal terminal) {
		this.terminal = terminal;
		this.camera = camera;

		int cx = camera.camera.getWidth() / 2;
		int cy = camera.camera.getHeight() / 2;
		
		// ligne du centre
		for (int x = -5; x < 10; x++ )
			for (int y = -8; y < 8; y++ )
				corresp.put(new Point(y, x), new Point(cx - 25*x, cy - 25*y));
		
		

		addPixel(0, 0, 0);
	}

	public void update() {
		BufferedImage image = (BufferedImage)(camera.getImage());

		if (image == null) return;

		//temp
		/*
		try {
			image = ImageIO.read(new File("assets/images/capture.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//

		double theta = Math.toRadians(terminal.theta) + 3.1415926535;
		int robx = terminal.posx;
		int roby = terminal.posy;


		for( Point co : corresp.keySet()){
			int mapx = 0;
			int mapy = 0;

			// on prend en compte la distance entre le centre vu par la cam et le robot
			int c_x = co.x + distWheel;
			int c_y = co.y;
			double c = Math.sqrt(c_x * c_x + c_y * c_y);

			double alpha = (c_y<0 ? -1 : 1) * Math.acos(c_x / c);

			mapx = (int) (robx + Math.sin(theta + alpha)*c);
			mapy = (int) (roby + Math.cos(theta + alpha)*c);

			int imgx = corresp.get(co).x;
			int imgy = corresp.get(co).y;

			int color = image.getRGB(imgx, imgy);

			addPixel(mapx, mapy, color);
		}

		// modif taille map pour afficher pos robot
		minX = Math.min(robx - 10, minX);
		minY = Math.min(roby - 10, minY);

		maxX = Math.max(robx + 10, maxX);
		maxY = Math.max(roby + 10, maxY);


		recreateImage();

	}

	private void recreateImage() {
		int w = maxX - minX;
		int h = maxY - minY;

		w = Math.max(w, h);
		h = w;


		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR_PRE);

		for (Point c : minimap.keySet()) {
			img.setRGB(c.x - minX, c.y - minY, minimap.get(c));
		}

		try {
			for (int i = -w/20; i < w/20; i++)
				for (int j = -w/20; j < w/20; j++)
					img.setRGB(terminal.posx + i - minX, terminal.posy + j - minY, (new Color(200, 0, 0)).getRGB() );
		} catch (Exception e){}	

		/*
		try {
			for (int i = 0; i < w/10; i++)
				for (int j = 0; j < i; j++) {
					img.setRGB(-minX + terminal.posx + j, -minY + terminal.posy + i, (new Color(200, 0, 0)).getRGB() );
					img.setRGB(-minX + terminal.posx - j, -minY + terminal.posy + i, (new Color(200, 0, 0)).getRGB() );
				}

		} catch (Exception e){}	
		 */


		buffer = img;
	}

	public static BufferedImage tilt(BufferedImage image, double angle, GraphicsConfiguration gc) {
		double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
		int w = image.getWidth(), h = image.getHeight();
		int neww = (int)Math.floor(w*cos+h*sin), newh = (int)Math.floor(h*cos+w*sin);
		int transparency = image.getColorModel().getTransparency();
		BufferedImage result = gc.createCompatibleImage(neww, newh, transparency);
		Graphics2D g = result.createGraphics();
		g.translate((neww-w)/2, (newh-h)/2);
		g.rotate(angle, w/2, h/2);
		g.drawRenderedImage(image, null);

		return result;
	}

	BufferedImage getMiniMap(){
		return buffer;
	}

	void addPixel(int x, int y, int color){
		minX = Math.min(minX, x);
		minY = Math.min(minY, y);

		maxX = Math.max(maxX, x+1);
		maxY = Math.max(maxY, y+1);

		Point coords = new Point(x, y);

		if (minimap.containsKey(coords)){
			Color oldC = new Color(minimap.get(coords));
			Color newC = new Color(color);
			int r = (newC.getRed() + oldC.getRed() * 9)/10;
			int g = (newC.getGreen() + oldC.getGreen() * 9)/10;
			int b = (newC.getBlue() + oldC.getBlue() * 9)/10;
			
			color = (new Color(r, g, b)).getRGB();
		}

		minimap.put(new Point(x, y), color);
	}



	public static BufferedImage correction2(BufferedImage pixels) {
		try {
			pixels = ImageIO.read(new File("assets/images/capture.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int width = pixels.getWidth();
		int height = pixels.getHeight();
		BufferedImage pixelsCopy = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

		// parameters for correction
		double paramA = -0.001; // affects only the outermost pixels of the image
		double paramB = 0.1; // most cases only require b optimization
		double paramC = 0.0; // most uniform correction
		double paramD = 1.0 - paramA - paramB - paramC; // describes the linear scaling of the image

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int d = Math.min(width, height) / 2;    // radius of the circle

				// center of dst image
				double centerX = (width - 1) / 2.0;
				double centerY = (height - 1) / 2.0;

				// cartesian coordinates of the destination point (relative to the centre of the image)
				double deltaX = (x - centerX) / d;
				double deltaY = (y - centerY) / d;

				// distance or radius of dst image
				double dstR = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

				// distance or radius of src image (with formula)
				double srcR = (paramA * dstR * dstR * dstR + paramB * dstR * dstR + paramC * dstR + paramD) * dstR;

				// comparing old and new distance to get factor
				double factor = Math.abs(dstR / srcR);

				// coordinates in source image
				double srcXd = centerX + (deltaX * factor * d);
				double srcYd = centerY + (deltaY * factor * d);

				// no interpolation yet (just nearest point)
				int srcX = (int) srcXd;
				int srcY = (int) srcYd;

				if (srcX >= 0 && srcY >= 0 && srcX < width && srcY < height) {
					pixelsCopy.setRGB(x, y, pixels.getRGB(srcX, srcY));
				}
			}
		}

		return pixelsCopy;
	}
}

class Point{
	int x, y;
	
	public Point(int x_,int y_) {
		x = x_;
		y = y_;
	}
	
	 public int getX() 
	    {
	        return x;
	    }

	    public int getY()
	    {
	        return y;
	    }

	    @Override
	    public boolean equals(Object other) 
	    {
	        if (this == other)
	          return true;

	        if (!(other instanceof Point))
	          return false;

	        Point otherPoint = (Point) other;
	        return otherPoint.x == x && otherPoint.y == y;
	    }
	
	@Override
    public int hashCode()
    {
		int result = x;
	    result = 31 * result + y;
	    return result;
    }
}