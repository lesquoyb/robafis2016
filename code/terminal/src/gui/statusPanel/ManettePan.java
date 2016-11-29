package gui.statusPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Manette;

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
	}
	
	public void refreshView(){
		if(m.xc.isConnected())
			etat_robot.setIcon(StatusPanel.vert);
		else
			etat_robot.setIcon(StatusPanel.rouge);
	}
	
}