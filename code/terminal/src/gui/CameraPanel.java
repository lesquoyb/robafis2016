package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;

import model.Camera;
import model.Terminal;

@SuppressWarnings("serial")
public class CameraPanel extends JPanel implements Observer{

	Camera camera;

	static {
		Webcam.setDriver(new IpCamDriver());
	}

	Webcam webcam;
	WebcamPanel video;

	MiniMap minimap;
	
	Dimension camDim;

	public CameraPanel(Terminal terminal) {
		this.camera = terminal.getCamera();
		camera.addObserver(this);

		minimap = new MiniMap(this, terminal);
		
		camDim = new Dimension(camera.getWidth(), camera.getHeight());

		setPreferredSize(camDim);

		setBackground(Color.BLACK);

		/*
		 try {
			IpCamDeviceRegistry.register("Robot" + camera.getNumCam() , "http://" + camera.getIp() + "/video", IpCamMode.PUSH);

			webcam = (Webcam) Webcam.getWebcams().get(0);
			webcam.setViewSize(camDim);
			webcam.open();

			video = new WebcamPanel(webcam);
			video.setBounds(0, 0, getWidth(), getHeight());

			video.setFillArea(true);
			add(video);

			camera.setConnected(true);
		} catch (Exception e) {}
		 */
	}



	public void refresh(){


		camera.setConnected(false);		

		if (video != null)
			remove(video);

		try {
			webcam.close();
			IpCamDeviceRegistry.unregister("Robot" + camera.getNumCam());
		} catch (Exception e) {}

		try {

			IpCamDeviceRegistry.register("Robot" + camera.getNumCam(), "http://" + camera.getIp() + "/video", IpCamMode.PUSH);

			webcam = (Webcam) Webcam.getWebcams().get(0);
			webcam.setViewSize(camDim);
			webcam.open();

			if (video != null)
				video.stop();

			video = new WebcamPanel(webcam);




			add(video);

			camera.setConnected(true);	
		} catch (Exception e) {}
	}


	private void reconnect(){

	}



	@Override
	public void update(Observable o, Object arg) {
		reconnect();
		refresh();
	}



	@Override
	public void repaint() {
		if(video != null){

			video.setFillArea(true);
			video.setBounds(0, 0, getWidth(), getHeight());
		}
		super.repaint();
		
		if (minimap != null)
			minimap.update();
	}


	public BufferedImage getImage(){
		if (webcam == null)
			return null;
		return webcam.getImage();
	}


}
