package gui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Terminal;

@SuppressWarnings("serial")
public class MissionStatePanel extends JPanel {
	public MissionStatePanel(Terminal terminal) {
		setPreferredSize(new Dimension(250, 0));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


		JLabel title = new JLabel("PLOP");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(title);
	}
}
