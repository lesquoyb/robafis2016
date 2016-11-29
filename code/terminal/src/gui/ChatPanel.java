package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Terminal;

@SuppressWarnings("serial")
public class ChatPanel extends JPanel {
	
	public ChatPanel(final Terminal terminal) {
		setPreferredSize(new Dimension(0, 50));
		
		setLayout(new FlowLayout());
		
		final JTextField depannage = new JTextField();
		JButton send = new JButton("Envoyer");
		
		depannage.setPreferredSize(new Dimension(500, 40));
		send.setPreferredSize(new Dimension(100, 40));
		
		
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				terminal.message = depannage.getText();
				depannage.setText("");
			}
		});
		
		
		add(depannage);
		add(send);
		
		this.setBackground(Color.GRAY);
	}
}
