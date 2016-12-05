
package gui.statusPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Robot;

@SuppressWarnings("serial")
class RobotPan extends JPanel implements Observer{

	Robot robot;
	JLabel etat_robot = new JLabel(StatusPanel.rouge);
	JButton refresh = new JButton(StatusPanel.rafraichir);

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
		timer.schedule(myTask, 0, 2000);


		final JTextField ip = new JTextField();
		ip.setText(robot.getIPBT());

		refresh.setBorder(BorderFactory.createEmptyBorder());
		refresh.setContentAreaFilled(false);

		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				robot.setIPBT(ip.getText());
				robot.reconnectBT();
			}
		});

		add(new JLabel("Robot : "), BorderLayout.WEST);
		add(etat_robot, BorderLayout.CENTER);
		add(refresh, BorderLayout.EAST);
		add(ip, BorderLayout.SOUTH);
	}

	void refresh() {
		if (robot.isRefreshing())
			refresh.setIcon(StatusPanel.rafraichir2);
		else
			refresh.setIcon(StatusPanel.rafraichir);

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
				},
				200
				);

	}
}