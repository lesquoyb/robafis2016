package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;

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

import model.Camera;
import model.Terminal;

@SuppressWarnings("serial")
public class CameraPanel extends JPanel implements Observer, WebcamPanel.Painter{

	static {
		Webcam.setDriver(new IpCamDriver());
	}
	
	Terminal terminal;
	Camera camera;
	
	private WebcamPanel.Painter painter = null;
	private WebcamPanel panel = null;
	
	MiniMap minimap;
	
	public CameraPanel(Terminal terminal) {
		this.terminal = terminal;
		this.camera = terminal.getCamera();
		camera.addObserver(this);
		
		Dimension dim = new Dimension(camera.getWidth(), camera.getHeight());
		
		minimap = new MiniMap(this, terminal);
		
		try {
			IpCamDeviceRegistry.register("Robot" + camera.getNumCam() , "http://" + camera.getIp() + "/video", IpCamMode.PUSH);
			camera.setConnected(true);
		} catch (Exception e) {
			camera.setConnected(false);
			e.printStackTrace();
		}
		Webcam webcam = (Webcam) Webcam.getWebcams().get(0);
		webcam.setViewSize(dim);

		panel = new WebcamPanel(webcam);
		panel.setFillArea(true);
		
		panel.setPainter(this);
		panel.start();
		
		painter = panel.getDefaultPainter();
		
		add(panel);
		
		Timer minimUpd = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				minimap.update();
			}
		};
		minimUpd.schedule(task, 0, 500);
	}
	
	@Override
	public void update(Observable o, Object arg) {
	}

	@Override
	public void paintImage(WebcamPanel arg0, BufferedImage image, Graphics2D g2) {
		if (painter != null) {
			painter.paintImage(panel, image, g2);
		}
		
		g2.setColor(Color.RED);
		g2.drawRect(800/2 - 50, 480/2 - 25, 100, 50);
		g2.drawRect(800/2 - 49, 480/2 - 24, 98, 48);
		
		g2.drawImage(getBatteryImage(), 0, 0, 100, 50, null);
		
		g2.drawImage(minimap.getMiniMap(), 800-200, 480-100, 200, 100, null);
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
	public void paintPanel(WebcamPanel arg0, Graphics2D g2) {
		
	}

	public Image getImage() {
		return panel.createImage(800, 480);
	}
}