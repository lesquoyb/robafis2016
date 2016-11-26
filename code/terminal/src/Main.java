import gui.Frame;
import model.Terminal;

public class Main {

	public static void main(String[] args) {
		Terminal terminal = new Terminal();
		
		new Frame(terminal);
	}	
}