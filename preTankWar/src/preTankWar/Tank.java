package preTankWar;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	private int x ,y;
	//用枚举定义各个方向	
	private Direction dir = Direction.D;	//初始化方向为向下
	
	private boolean bL=false, bU=false, bR=false, bD = false;
	
	private TankClient tc= null;
	
	public Tank(int x, int y) {		
		this.x = x;
		this.y = y;
	}
	
	public Tank(int x, int y, TankClient tc) {		
		this(x , y);
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, WIDTH, HEIGHT);	//画出一个圆
		g.setColor(c);
		
		move();
	}
	
	//根据方向作出相应的移动
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
		case STOP:
			break;
		}
		
		
	}

	//确定哪个键被按下
	public void whichKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();	//获得所按键的虚拟键码
		switch(key) {
		//按下空格时候，新建子弹
		case KeyEvent.VK_SPACE:
			tc.bullet = fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		
		locateDirection();
	}
	
	//发射子弹
	private Bullet fire() {
		int x = this.x + WIDTH/2 - Bullet.WIDTH/2;
		int y = this.y + HEIGHT/2 - Bullet.HEIGHT/2;
		Bullet bullet = new Bullet(x , y, dir);
		return bullet;
	}

	//确定哪个键被抬起
	public void whichKeyReleased(KeyEvent e) {
		int key = e.getKeyCode();	//获得所按键的虚拟键码
		switch(key) {
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		
		locateDirection();		
	}

	//通过判断哪个键被按下，确定tank的方向
	private void locateDirection() {		
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;		
	}
		
}
