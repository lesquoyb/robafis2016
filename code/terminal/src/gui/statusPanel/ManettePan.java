package gui.statusPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Manette;

@SuppressWarnings("serial")
public class ManettePan extends JPanel{
	
	Manette m;
	JLabel etat_robot;
	JButton refresh;

	public ManettePan(Manette manette) {
		m = manette;
		m.setView(this);
		
		setLayout(new BorderLayout());

		etat_robot = new JLabel(StatusPanel.rouge);
		refresh = new JButton(StatusPanel.rafraichir);
		
		refresh.setBorder(BorderFactory.createEmptyBorder());
		refresh.setContentAreaFilled(false);

		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m.initPad();
			}
		});

		add(new JLabel("Manette : "), BorderLayout.WEST);
		add(etat_robot, BorderLayout.CENTER);
		add(refresh, BorderLayout.EAST);
	}
	
	public void refreshView(){
		if(m.xc.isConnected())
			etat_robot.setIcon(StatusPanel.vert);
		else
			etat_robot.setIcon(StatusPanel.rouge);
	}
	
}