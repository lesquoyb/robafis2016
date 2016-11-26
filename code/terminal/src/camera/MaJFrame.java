package camera;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MaJFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MaJFrame(int a, int b) {
		setTitle("IHM - Robafis - 2016");

		setResizable(false);
		setPreferredSize(new Dimension(a,b+40));
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
