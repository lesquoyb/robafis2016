package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import model.Terminal;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	CameraPanel camPan;
	ChatPanel chatPan;
	InfoPanel infoPan;
	MissionStatePanel missionPan;
	
	public Frame(Terminal terminal) {
		// Gestion des composants de la fenetre
		setLayout(new BorderLayout());
	
		camPan = new CameraPanel(terminal.getCamera());
		chatPan = new ChatPanel(terminal);
		infoPan = new InfoPanel(terminal);
		missionPan = new MissionStatePanel(terminal);
	
		add(camPan, BorderLayout.CENTER);
		add(infoPan, BorderLayout.WEST);
		add(missionPan, BorderLayout.EAST);
		add(chatPan, BorderLayout.SOUTH);
		
		// Le reste
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	@Override
	public void repaint() {
		super.repaint();

		camPan.repaint();
		infoPan.repaint();
	}
}
