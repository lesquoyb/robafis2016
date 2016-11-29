package gui.statusPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Camera;

public 
class VideoPan extends JPanel implements Observer {
	JLabel etat_video = new JLabel(StatusPanel.rouge);
	Camera camera;

	public VideoPan(final Camera camera) {
		this.camera = camera;
		camera.addObserver(this);

		setBackground(Color.LIGHT_GRAY);
		setLayout(new BorderLayout());

		if (camera.isConnected())
			etat_video.setIcon(StatusPanel.vert);
		else
			etat_video.setIcon(StatusPanel.rouge);

		final JTextField ip = new JTextField();
		ip.setText(camera.getIp());

		final JButton refresh = new JButton(StatusPanel.rafraichir);
		refresh.setBorder(BorderFactory.createEmptyBorder());
		refresh.setContentAreaFilled(false);

		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				camera.setIp(ip.getText());
				camera.reconnect();
			}
		});

		

		add(new JLabel("Video : "), BorderLayout.WEST);
		add(etat_video, BorderLayout.CENTER);
		add(refresh, BorderLayout.EAST);
		add(ip, BorderLayout.SOUTH);
	}

	void refresh(){
		if (camera.isConnected())
			etat_video.setIcon(StatusPanel.vert);
		else
			etat_video.setIcon(StatusPanel.rouge);
	}

	@Override
	public void update(java.util.Observable o, Object arg) {
		new java.util.Timer().schedule( 
				new java.util.TimerTask() {
					@Override
					public void run() {
						refresh();
					}
				}, 
				200
				);
	}
}

