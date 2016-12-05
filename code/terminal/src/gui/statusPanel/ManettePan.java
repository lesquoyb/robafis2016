package gui.statusPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Manette;

@SuppressWarnings("serial")
public class ManettePan extends JPanel{
	
	Manette m;
	JLabel etat_robot;
	

	public ManettePan(Manette manette) {
		m = manette;
		m.setView(this);
		
		setLayout(new BorderLayout());

		etat_robot = new JLabel(StatusPanel.rouge);
		add(new JLabel("Manette : "), BorderLayout.WEST);
		add(etat_robot, BorderLayout.CENTER);
		
		// HACK pour aligner les voyants
		JPanel plop = new JPanel();
		plop.setPreferredSize(new Dimension(StatusPanel.rafraichir.getIconWidth() + 10, 20));
		add( plop , BorderLayout.EAST);
	}
	
	public void refreshView(){
		if(m.xc.isConnected())
			etat_robot.setIcon(StatusPanel.vert);
		else
			etat_robot.setIcon(StatusPanel.rouge);
	}
	
}