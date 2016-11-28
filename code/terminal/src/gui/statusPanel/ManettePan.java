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

		final JButton refresh = new JButton(StatusPanel.rafraichir);
		refresh.setBorder(BorderFactory.createEmptyBorder());
		refresh.setContentAreaFilled(false);

		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				refresh.setIcon(StatusPanel.rafraichir);
			}
		});

		refresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				super.mousePressed(e);
				refresh.setIcon(StatusPanel.rafraichir2);
				m.initPad();
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				super.mouseReleased(e);
				refresh.setIcon(StatusPanel.rafraichir);
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