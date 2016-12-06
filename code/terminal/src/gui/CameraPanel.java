package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;

import main.Main;
import model.Camera;
import model.Terminal;

@SuppressWarnings("serial")
public class CameraPanel extends JPanel implements Observer, WebcamPanel.Painter{

	static {
		Webcam.setDriver(new IpCamDriver());
	}

	Terminal terminal;
	Camera camera;

	Webcam webcam;
	private WebcamPanel.Painter painter = null;
	private WebcamPanel panel = null;
	
	MiniMap minimap;

	public CameraPanel(Terminal terminal) {
		this.terminal = terminal;
		this.camera = terminal.getCamera();
		camera.addObserver(this);
		
		minimap = new MiniMap(this, terminal);

		/*Dimension dim = new Dimension(camera.getWidth(), camera.getHeight());
		this.setPreferredSize(dim);

		try {
			camera.getNewNumCam();
			IpCamDeviceRegistry.register("Robot" + camera.getNumCam() , "http://" + camera.getIp() + "/video", IpCamMode.PUSH);
			camera.setConnected(true);

			webcam = (Webcam) Webcam.getWebcams().get(0);
			webcam.setViewSize(dim);

			panel = new WebcamPanel(webcam);
			panel.setFillArea(true);
			panel.setPainter(this);
			panel.start();
			
			painter = panel.getDefaultPainter();
			add(panel);
		} catch (Exception e) {
			camera.setConnected(false);
		}*/
		
		reconnect();
	}

	void reconnect(){
		Dimension dim = new Dimension(camera.getWidth(), camera.getHeight());
		this.setPreferredSize(dim);

		try {
			camera.getNewNumCam();
			IpCamDeviceRegistry.register("Robot" + camera.getNumCam() , "http://" + camera.getIp() + "/video", IpCamMode.PUSH);

			webcam = (Webcam) Webcam.getWebcams().get(0);
			webcam.setViewSize(dim);

			panel = new WebcamPanel(webcam);
			panel.setFillArea(true);
			panel.setPainter(this);
			panel.start();
			
			painter = panel.getDefaultPainter();
			add(panel);
			
			Main.frame.resize(Main.frame.getPreferredSize());
			Main.frame.repaint();
			Main.frame.pack();
			
			camera.setConnected(true);			
		} catch (Exception e) {
			camera.setConnected(false);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if ( panel == null)
			reconnect();
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		
		if (!camera.isConnected()) {
			try {
				BufferedImage plop = ImageIO.read(new File("assets/images/noFlux.png"));
				//System.out.println(g);
				g.drawImage(plop, 0,0, null);
				
			} catch (IOException ioe) {
				// TODO Auto-generated catch block
				ioe.printStackTrace();
			}
		}
	}

	int mwidth = 300;
	int mheight = mwidth/2;

	@Override
	public void paintImage(WebcamPanel arg0, BufferedImage image, Graphics2D g2) {
		if (painter != null) {
			painter.paintImage(panel, image, g2);
		}

		g2.setColor(Color.RED);
		g2.drawRect(450, 175, 200, 100);
		g2.drawRect(451, 176, 198, 98);
		g2.drawRect(452, 177, 196, 96);



		g2.drawImage(getBatteryImage(), 0, 0, 100, 50, null);

		BufferedImage im = minimap.getMiniMap();

		if (im != null){
			int msx = 800-mwidth;
			int msy = 480-mheight;

			float rx = (float)mwidth / minimap.realMap.getWidth() ;
			float ry = (float)mheight / minimap.realMap.getHeight();

			g2.drawImage(im, msx, msy, mwidth, mheight, null);

			//System.out.println(rx + " - " + ry);

			int x = msx + (int)((minimap.startx + terminal.posx*10)*rx);
			int y = msy + (int)((minimap.starty + terminal.posy*10)*ry);

			g2.fillOval( msx + (int)((minimap.startx + terminal.posx*10)*rx) -10 , msy + (int)((minimap.starty + terminal.posy*10)*ry) -10, 20, 20);

			g2.setColor(new Color (0, 200, 0));

			for (int i = -25; i < 25; i++) {
				g2.drawLine(x, y,
						x + (int)(30 * Math.sin( Math.toRadians(180 + terminal.theta + i))),
						y + (int)(30 * Math.cos( Math.toRadians(180 + terminal.theta + i)))
						);
			}
			
			g2.setColor(new Color(50, 50, 250));
			for (java.awt.Point pt : terminal.balises) {
				int xp = msx + (int)((minimap.startx + pt.getX()*10)*rx);
				int yp = msy + (int)((minimap.starty + pt.getY()*10)*ry);
				for (int i = 0; i < 5; i++)
					g2.drawOval(xp -20 + i, yp-20 +i, 40-i*2, 40-i*2);
			}
		}
	}



	private Image getBatteryImage() {
		int lvl = terminal.batterie;

		Image bi = null;
		try {
			if (lvl < 25)
				bi = ImageIO.read(new File("assets/images/batterie/low.png"));
			else if (lvl < 50)
				bi = ImageIO.read(new File("assets/images/batterie/medium.png"));
			else if (lvl < 75)
				bi = ImageIO.read(new File("assets/images/batterie/good.png"));
			else
				bi = ImageIO.read(new File("assets/images/batterie/full.png"));

		} catch (Exception e){}

		return bi;
	}

	@Override
	public void paintPanel(WebcamPanel arg0, Graphics2D g2) {}

	public BufferedImage getImage() {
		return webcam.getImage();
	}
}