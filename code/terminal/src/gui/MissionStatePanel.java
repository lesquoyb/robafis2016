package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Terminal;

@SuppressWarnings("serial")
public class MissionStatePanel extends JPanel {
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	JLabel nb_balise;
	
	public MissionStatePanel(Terminal terminal) {
		setPreferredSize(new Dimension(250, 0));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


		JLabel title = new JLabel("ETAT DE LA MISSION");
		title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 18));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(title);
		
		nb_balise = new JLabel("___/___");
		nb_balise.setFont(new Font(nb_balise.getFont().getName(), Font.PLAIN, 60));
		nb_balise.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		p1.setBackground(new Color(0, 255, 120));
		p2.setBackground(new Color(255, 255, 120));
		
		mission1();
		
		
		add(Box.createVerticalStrut(10));
		this.add(p1);
		add(Box.createVerticalStrut(10));
		this.add(p2);
		add(Box.createVerticalStrut(10));
		this.add(p3);
	}
	
	public void mission1(){
		nb_balise.setText("0/1");
		
		p1.add(new JLabel("Phase 1: Déplacement au camp de base"));
		p1.add(new JLabel("Cette phase est automatique."));
		p1.add(new JLabel("Veuillez attendre le retour sonore"));
		
		
		p2.add(new JLabel("Phase 2: balisage"));
		p2.add(new JLabel("Déplacez le robot pour déposer"));
		p2.add(new JLabel("une balise sur la crevasse."));
		p2.add(new JLabel("                "));
		p2.add(nb_balise);
		
		p3.add(new JLabel("Phase 3: retour"));
		p3.add(new JLabel("Déplacez le robot afin de le"));
		p3.add(new JLabel("ramener au camp de base"));
		p3.add(new JLabel("en suivant le chemin défini"));
		p3.add(new JLabel("par les ligne noires."));
		
		
	}
	
	public void mission2(){
		nb_balise.setText("0/2");
		
		p1.add(new JLabel("Phase 1: Déplacement au camp de base"));
		p1.add(new JLabel("Cette phase est automatique."));
		p1.add(new JLabel("Veuillez attendre le retour sonore"));
		
		
		p2.add(new JLabel("Phase 2: balisage"));
		p2.add(new JLabel("Déplacez le robot pour déposer"));
		p2.add(new JLabel("une balise sur chaque zones"));
		p2.add(new JLabel("de faible luminosité."));
		p2.add(new JLabel("                      "));
		p2.add(nb_balise);
		
		p3.add(new JLabel("Phase 3: retour"));
		p3.add(new JLabel("Déplacez le robot afin de le"));
		p3.add(new JLabel("ramener au camp de base"));
		p3.add(new JLabel("en suivant le chemin défini"));
		p3.add(new JLabel("par les ligne noires."));
	}
	
	public void mission3(){
		nb_balise.setText("0/3");
		
		p1.add(new JLabel("Phase 1: Déplacement au camp de base"));
		p1.add(new JLabel("Cette phase est automatique."));
		p1.add(new JLabel("Veuillez attendre le retour sonore"));
		
		
		p2.add(new JLabel("Phase 2: balisage"));
		p2.add(new JLabel("Déplacez le robot pour déposer"));
		p2.add(new JLabel("une balise sur chaque zones"));
		p2.add(new JLabel("de mauvaise condition de liaison."));
		p2.add(nb_balise);
		
		p3.add(new JLabel("Phase 3: retour"));
		p3.add(new JLabel("Déplacez le robot afin de le"));
		p3.add(new JLabel("ramener au camp de base"));
		p3.add(new JLabel("en suivant le chemin défini"));
		p3.add(new JLabel("par les ligne noires."));
	}
}