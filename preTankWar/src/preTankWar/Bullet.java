package preTankWar;

import java.awt.*;

public class Bullet {
	private int x , y;
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	private Direction dir;
	
	Bullet(int x,int y, Direction dir){
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, 10, 10);
		g.setColor(c);
		
		move();
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
		
	}

}
