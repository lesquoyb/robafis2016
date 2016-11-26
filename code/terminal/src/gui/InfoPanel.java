package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Camera;
import model.Terminal;

@SuppressWarnings("serial")
public class InfoPanel extends JPanel{
	Terminal terminal;

	JPanel co_manette;
	JPanel co_video;
	JPanel co_robot;


	static ImageIcon rouge = new ImageIcon("assets/images/rouge.png");
	static ImageIcon vert = new ImageIcon("assets/images/vert.png");
	static ImageIcon rafraichir = new ImageIcon("assets/images/rafraichir.png");
	static ImageIcon rafraichir2 = new ImageIcon("assets/images/rafraichir2.png");

	public InfoPanel(Terminal terminal) {
		this.terminal = terminal;

		setPreferredSize(new Dimension(250, 0));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


		JLabel title = new JLabel("PLOP");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		co_robot = new RobotPan();
		co_video = new VideoPan(terminal.getCamera());
		co_manette = new ManettePan();

		add(title);
		add(Box.createVerticalStrut(10));
		add(co_robot);
		add(Box.createVerticalStrut(10));
		add(co_video);
		add(Box.createVerticalStrut(10));
		add(co_manette);
	}
}

@SuppressWarnings("serial")
class RobotPan extends JPanel{
	public RobotPan() {
		setLayout(new BorderLayout());

		JLabel etat_robot = new JLabel(InfoPanel.rouge);

		final JButton refresh = new JButton(InfoPanel.rafraichir);
		refresh.setBorder(BorderFactory.createEmptyBorder());
		refresh.setContentAreaFilled(false);

		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// AJOUTER ACTION
			}
		});

		refresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				super.mousePressed(e);
				refresh.setIcon(InfoPanel.rafraichir2);
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				super.mouseReleased(e);
				refresh.setIcon(InfoPanel.rafraichir);
			}
		});

		add(new JLabel("Robot : "), BorderLayout.WEST);
		add(etat_robot, BorderLayout.CENTER);
		add(refresh, BorderLayout.EAST);
	}
}

@SuppressWarnings("serial")
class VideoPan extends JPanel implements Observer {
	JLabel etat_video = new JLabel(InfoPanel.rouge);
	Camera camera;

	public VideoPan(final Camera camera) {
		this.camera = camera;
		camera.addObserver(this);

		setBackground(Color.LIGHT_GRAY);
		setLayout(new BorderLayout());

		if (camera.isCamConnected())
			etat_video.setIcon(InfoPanel.vert);
		else
			etat_video.setIcon(InfoPanel.rouge);

		final JTextField ip = new JTextField();

		final JButton refresh = new JButton(InfoPanel.rafraichir);
		refresh.setBorder(BorderFactory.createEmptyBorder());
		refresh.setContentAreaFilled(false);

		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				camera.setIp(ip.getText());
				camera.reconnect();
			}
		});

		refresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				super.mousePressed(e);
				refresh.setIcon(InfoPanel.rafraichir2);
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				super.mouseReleased(e);
				refresh.setIcon(InfoPanel.rafraichir);
			}
		});

		ip.setText(camera.getIp());

		add(new JLabel("Video : "), BorderLayout.WEST);
		add(etat_video, BorderLayout.CENTER);
		add(refresh, BorderLayout.EAST);
		add(ip, BorderLayout.SOUTH);
	}

	void refresh(){
		if (camera.isCamConnected())
			etat_video.setIcon(InfoPanel.vert);
		else
			etat_video.setIcon(InfoPanel.rouge);
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
				500
				);
	}
}

@SuppressWarnings("serial")
class ManettePan extends JPanel{
	public ManettePan() {
		setLayout(new BorderLayout());

		JLabel etat_robot = new JLabel(InfoPanel.rouge);

		final JButton refresh = new JButton(InfoPanel.rafraichir);
		refresh.setBorder(BorderFactory.createEmptyBorder());
		refresh.setContentAreaFilled(false);

		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// AJOUTER ACTION
			}
		});

		refresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				super.mousePressed(e);
				refresh.setIcon(InfoPanel.rafraichir2);
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				super.mouseReleased(e);
				refresh.setIcon(InfoPanel.rafraichir);
			}
		});

		add(new JLabel("Manette : "), BorderLayout.WEST);
		add(etat_robot, BorderLayout.CENTER);
		add(refresh, BorderLayout.EAST);
	}
}