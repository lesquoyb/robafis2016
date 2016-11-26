package camera;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;

public class CameraDetection implements WebcamDiscoveryListener {

	public CameraDetection() {
		for(int i = 0; i < Webcam.getWebcams().size(); i++) {
			System.out.println("Webcam detected: " + ((Webcam) Webcam.getWebcams().get(i)).getName());
		}
		Webcam.addDiscoveryListener(this);
		System.out.println("Please connect additional webcams, or disconnect already connected ones. Listening for events...");
	}

	public void webcamFound(WebcamDiscoveryEvent event) {
		System.out.println("Webcam connected: %s \n" + event.getWebcam().getName());
	}

	public void webcamGone(WebcamDiscoveryEvent event) {
		System.out.println("Webcam disconnected: %s \n" + event.getWebcam().getName());
	}
}
