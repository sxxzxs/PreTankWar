package preTankWar;

import java.awt.*;

public class Explode {
	private int x , y;
	private boolean live = true;
	int[] size = {5,15,25,35,45,55,45,35,25,15,5};
	int step = 0;
	private TankClient tc;
	
	public Explode(int x,int y,TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			tc.explode.remove(this);
			return;		
		}
		if(step == size.length) {
			live = false;
			step = 0;
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, size[step], size[step]);
		step++;
		g.setColor(c);		
	}

}
