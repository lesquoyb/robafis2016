package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Terminal;

@SuppressWarnings("serial")
public class ChatPanel extends JPanel {
	public ChatPanel(Terminal terminal) {
		setPreferredSize(new Dimension(0, 50));
		
		setLayout(new FlowLayout());
		
		JTextField depannage = new JTextField();
		JButton send = new JButton("Envoyer");
		
		depannage.setPreferredSize(new Dimension(500, 40));
		send.setPreferredSize(new Dimension(100, 40));
		
		add(depannage);
		add(send);
		
		this.setBackground(Color.GRAY);
	}
}
