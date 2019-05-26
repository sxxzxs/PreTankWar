package preTankWar;

import java.awt.*;
import java.util.List;

public class Bullet {
	

	private int x , y;
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	private Direction dir;
	private boolean live = true;
	private TankClient tc;
	private boolean good;
	
	Bullet(int x,int y, Direction dir){
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	Bullet(int x,int y,boolean good , Direction dir, TankClient tc){
		this(x ,y , dir);
		this.good = good;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			tc.bullets.remove(this);
			return;
		}
		
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
		}
		
	}
	
	//打坦克
	public boolean hitTank(Tank t) {
		if(this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()) {
			t.setLive(false);
			this.live = false;
			Explode e = new Explode(x, y , tc);
			tc.explode.add(e);
			return true;
		}
		return false;
	}
	
	//打多辆坦克
	public boolean hitTanks(List<Tank> counterTanks) {
		for(int i = 0; i < counterTanks.size(); i++) {
			if(this.hitTank(counterTanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	//得到一个以x,y为横纵坐标,WIDTH, HEIGHT为宽高大小的矩形
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

}
