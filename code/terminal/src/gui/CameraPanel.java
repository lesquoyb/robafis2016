package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;

import model.Camera;

@SuppressWarnings("serial")
public class CameraPanel extends JPanel implements Observer{

	Camera camera;
	
	static {
		Webcam.setDriver(new IpCamDriver());
	}

	Webcam webcam;
	WebcamPanel video;
	
	Dimension camDim;
	
	public CameraPanel(Camera camera) {
		this.camera = camera;
		camera.addObserver(this);

		camDim = new Dimension(getWidth(), getHeight());
		setPreferredSize(camDim);
		
		setBackground(Color.BLACK);

		try {
			IpCamDeviceRegistry.register("Robot" + camera.getNumCam() , "http://" + camera.getIp() + "/video", IpCamMode.PUSH);

			webcam = (Webcam) Webcam.getWebcams().get(0);
			webcam.setViewSize(camDim);
			webcam.open();
			
			video = new WebcamPanel(webcam);
			video.setBounds(0, 0, getWidth(), getHeight());

			add(video);

			camera.setConnected(true);
		} catch (Exception e) {}
	
	
	}


	public void refresh(){
		
		
		camera.setConnected(false);
		
		camDim = new Dimension(camera.getWidth(), camera.getHeight());
		setPreferredSize(camDim);
		
		
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
			video.setBounds(0, 0, getWidth(), getHeight());

			add(video);
			
			camera.setConnected(true);	
		} catch (Exception e) {}
	}


	@Override
	public void update(Observable o, Object arg) {
		refresh();
	}
}
