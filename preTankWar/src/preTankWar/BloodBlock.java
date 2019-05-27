package preTankWar;

import java.awt.*;

public class BloodBlock {
	private int x, y, w, h;
	private TankClient tc;
	private boolean live = true;	//看血块是否被吃掉
	private int[][] loc = {
	          {350, 300}, {360, 300}, {375, 275}, {385, 250}, {360, 270}, {365, 290}, {340, 280}
			  };
	private int step = 0;
	
	BloodBlock(int x, int y) {		
		this.x = x;
		this.y = y;
		w = h =15;		
	}
	
	public void draw(Graphics g) {
		if(!live) return;
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		move();
	}
	
	//不断的让血块在屏幕里运动
	private void move() {
		step ++;
		if(step == loc.length) {
			step = 0;
		}
		x = loc[step][0];
		y = loc[step][1];
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, w, h);
	}
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

}
