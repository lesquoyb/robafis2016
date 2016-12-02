package gui.statusPanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Terminal;



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
		title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 18));
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
