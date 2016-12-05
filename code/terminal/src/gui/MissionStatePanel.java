package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Terminal;

@SuppressWarnings("serial")
public class MissionStatePanel extends JPanel implements Observer {
	Terminal terminal;
	JPanel choixm = new JPanel();
	JPanel phases = new JPanel();
	
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	
	JLabel title, lp1, lp2, lp3;
	int over;
	
	String press_start, phase1, phase2, phase3;

	public MissionStatePanel(Terminal terminal) {
		this.terminal = terminal;
		terminal.addObserver(this);
		setPreferredSize(new Dimension(300, 0));
		
		this.setLayout(new BorderLayout());

		title = new JLabel("<html><h2><center>CHOIX DE LA MISSION</center><h2></html>");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		add(title, BorderLayout.NORTH);
		
		press_start = "<html><center>"
				+ "<h3>Phase 1: Déplacement automatique</h3>"
				+ "APPUYEZ SUR"
				+ "<b><h1> START </h1></b>"
				+ "POUR DEMARRER"
				+ "</center></html>";
		p1.setBackground(new Color(255, 200, 200));
		
		choixm.setLayout(new GridLayout(3, 1));
		phases.setLayout(new GridLayout(3, 1));
		
		choixMission();
		add(choixm);		
	}

	public void choixMission(){
		JButton m1 = new JButton("<html><center>"+"Baliser 1 crevasse"+"</center></html>");
		m1.addActionListener(
			new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setMission(1);
				}
			}
		);
		choixm.add(m1);

		JButton m2 = new JButton("<html><center>"+"Baliser 2 zones"+"<br>"+"de faible luminosité"+"</center></html>");
		m2.addActionListener(
			new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setMission(2);
				}
			}
		);
		choixm.add(m2);
		
		JButton m3 = new JButton("<html><center>"+"Baliser 3 zones"+"<br>"+"de mauvaises conditions de liaison"+"</center></html>");
		m3.addActionListener(
			new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setMission(3);
				}
			}
		);
		choixm.add(m3);
	}

	public void setMission(int i){
		title.setText("<html><h2>ETAT DE LA MISSION</center><h2></html>");


		remove(choixm);


		missions();
		if (i == 1)
			mission1();
		if (i == 2)
			mission2();
		if (i == 3)
			mission3();

		lp1 = new JLabel(press_start);
		lp2 = new JLabel(phase2);
		lp3 = new JLabel(phase3);
		
		p1.add(lp1);
		p2.add(lp2);
		p3.add(lp3);
		
		phases.add(p1);
		phases.add(p2);
		phases.add(p3);
		
		add(phases);
	}

	public void missions(){
		phase1 = "<html><center>"
				+ "<h3>Phase 1: Déplacement automatique</h3>"
				+ "Cette phase est automatique." + "<br>"
				+ "Veuillez attendre le retour sonore"
				+ "</center></html>";
		
		phase3 = "<html><center>"
				+ "<h3>Phase 3: retour</h3>"
				+ "Déplacez le robot afin de le ramener" + "<br>"
				+ "au camp de base en suivant le" + "<br>"
				+ "chemin défini par les ligne noires"
				+ "</center></html>";
	}
	
	public void mission1(){
		over = 1;

		phase2 = "<html><center>"
				+ "<h3>Phase 2: balisage</h3>"
				+ "Déplacez le robot pour déposer" + "<br>"
				+ "une balise sur la crevasse."
				+ "<h1> 0/1 </h1>"
				+ "</center></html>";
	}

	public void mission2(){
		over = 2;

		phase2 = "<html><center>"
				+ "<h3>Phase 2: balisage</h3>"
				+ "Déplacez le robot pour déposer" + "<br>"
				+ "une balise sur chaque zones" + "<br>"
				+ "de faible luminosité."
				+ "<h1> 0/2 </h1>"
				+ "</center></html>";
	}

	public void mission3(){
		over = 3;

		phase2 = "<html><center>"
				+ "<h3>Phase 2: balisage</h3>"
				+ "Déplacez le robot pour déposer" + "<br>"
				+ "une balise sur chaque zones" + "<br>"
				+ "de mauvaise liaison."
				+ "<h1> 0/3 </h1>"
				+ "</center></html>";
	}

	@Override
	public void update(Observable o, Object arg) {
		if (terminal.phase == 2)
			lp2.setText(lp2.getText().replaceAll("[0-9]/[0-9]", terminal.depose + "/" + over));
		
		
		if (terminal.phase == 2 && terminal.depose == over){
			terminal.phase = 3;
		}
		
		switch (terminal.phase){
		case 1:
			p1.setBackground(new Color(255, 255, 120));
			lp1.setText(phase1);
			break;
		case 2:
			p1.setBackground(new Color(0, 255, 120));
			p2.setBackground(new Color(255, 255, 120));
			break;
		case 3:
			p1.setBackground(new Color(0, 255, 120));
			p2.setBackground(new Color(0, 255, 120));
			p3.setBackground(new Color(255, 255, 120));
			break;
		}
	}
}