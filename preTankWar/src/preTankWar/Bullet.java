package preTankWar;

import java.awt.*;

public class Bullet {
	

	private int x , y;
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	private Direction dir;
	private boolean live = true;
	private TankClient tc;
	
	Bullet(int x,int y, Direction dir){
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	Bullet(int x,int y,Direction dir, TankClient tc){
		this(x ,y , dir);
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		move();
	}	
	
	public boolean isLive() {
		return live;
	}

	private void move() {
		switch(dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;		
		}
		
		if(x < 0 || y < 0 || x > tc.GAME_WIDTH || y > tc.GAME_HEIGHT) {
			live = false;
			tc.bullets.remove(this);
		}
		
	}

}
