package gui.statusPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Terminal;

@SuppressWarnings("serial")
public class StatusPanel extends JPanel{
	Terminal terminal;

	JPanel panes = new JPanel();
	JPanel co_manette;
	JPanel co_video;
	JPanel co_robot;


	static ImageIcon rouge = new ImageIcon("assets/images/rouge.png");
	static ImageIcon vert = new ImageIcon("assets/images/vert.png");
	static ImageIcon rafraichir = new ImageIcon("assets/images/rafraichir.png");
	static ImageIcon rafraichir2 = new ImageIcon("assets/images/rafraichir2.png");

	public StatusPanel(Terminal terminal) {
		this.terminal = terminal;

		setPreferredSize(new Dimension(300, 0));

		this.setLayout(new BorderLayout());

		JLabel title = new JLabel("<html><h2><center>ETATS DES PERIPHERIQUES</center><h2></html>");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		add(title, BorderLayout.NORTH);
		
		co_robot = new RobotPan(terminal.getRobot());
		co_video = new VideoPan(terminal.getCamera());
		co_manette = new ManettePan(terminal.manette);

		add(panes);
		
		panes.setLayout(new GridLayout(3,1));
		panes.add(co_robot);
		panes.add(co_video);
		panes.add(co_manette);
	}
}
