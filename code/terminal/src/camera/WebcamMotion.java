package camera;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;

public class WebcamMotion implements WebcamMotionListener {

	public WebcamMotion(Webcam w) {
		WebcamMotionDetector detector = new WebcamMotionDetector(w);
		detector.setInterval(500); // one check per 500 ms
		detector.addMotionListener(this);
		detector.start();
	}
	
	@Override
	public void motionDetected(WebcamMotionEvent arg0) {
		System.out.println("Detected motion I, alarm turn on you have");
	}
	
}