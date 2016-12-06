package main;
import gui.Frame;
import model.Terminal;

public class Main {
	public static Frame frame; 
	
	public static void main(String[] args) {
		Terminal terminal = new Terminal();
		
		frame = new Frame(terminal);
		
		terminal.listenPad();
	}	
}