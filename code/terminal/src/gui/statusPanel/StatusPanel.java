package gui.statusPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Camera;
import model.Manette;
import model.Robot;
import model.Terminal;




@SuppressWarnings("serial")
class RobotPan extends JPanel implements Observer{
	
	Robot robot;
	JLabel etat_robot = new JLabel(StatusPanel.rouge);

	public RobotPan(final Robot robot) {
		this.robot = robot;
		robot.addObserver(this);
		setLayout(new BorderLayout());

		
		
		
		Timer timer = new Timer();
		TimerTask myTask = new TimerTask() {
		    @Override
		    public void run() {
		    	refresh();
		    }
		};
		timer.schedule(myTask, 2000);
		
	

		final JTextField ip = new JTextField();
		ip.setText(robot.getIPBT());

		final JButton refresh = new JButton(StatusPanel.rafraichir);
		refresh.setBorder(BorderFactory.createEmptyBorder());
		refresh.setContentAreaFilled(false);

		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				robot.setIPBT(ip.getText());
				robot.reconnectBT();
			}
		});

		refresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				super.mousePressed(e);
				refresh.setIcon(StatusPanel.rafraichir2);
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				super.mouseReleased(e);
				refresh.setIcon(StatusPanel.rafraichir);
			}
		});

		add(new JLabel("Robot : "), BorderLayout.WEST);
		add(etat_robot, BorderLayout.CENTER);
		add(refresh, BorderLayout.EAST);
		add(ip, BorderLayout.SOUTH);
	}

	void refresh() {
		robot.refresh();
		if (robot.isConnectedToBT())
			etat_robot.setIcon(StatusPanel.vert);
		else
			etat_robot.setIcon(StatusPanel.rouge);
	}

	@Override
	public void update(Observable o, Object arg) {
		new java.util.Timer().schedule( 
				new java.util.TimerTask() {
					@Override
					public void run() {
						refresh();
					}
				}, 500);
		
	}
}



@SuppressWarnings("serial")
public class StatusPanel extends JPanel{
	Terminal terminal;

	JPanel co_manette;
	JPanel co_video;
	JPanel co_robot;


	static ImageIcon rouge = new ImageIcon("assets/images/rouge.png");
	static ImageIcon vert = new ImageIcon("assets/images/vert.png");
	static ImageIcon rafraichir = new ImageIcon("assets/images/rafraichir.png");
	static ImageIcon rafraichir2 = new ImageIcon("assets/images/rafraichir2.png");

	public StatusPanel(Terminal terminal) {
		this.terminal = terminal;

		setPreferredSize(new Dimension(250, 0));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


		JLabel title = new JLabel("ETAT DES PERIPHERIQUES");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		co_robot = new RobotPan(terminal.getRobot());
		co_video = new VideoPan(terminal.getCamera());
		co_manette = new ManettePan(terminal.manette);

		add(title);
		add(Box.createVerticalStrut(10));
		add(co_robot);
		add(Box.createVerticalStrut(10));
		add(co_video);
		add(Box.createVerticalStrut(10));
		add(co_manette);
	}
}
