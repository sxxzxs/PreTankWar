package preTankWar;

import java.awt.*;


public class TankClient extends Frame{
		
	public void launch() {
		setLocation(300, 100);
		setSize(800, 600);
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		
		TankClient tc = new TankClient();
		tc.launch();
	}

}
