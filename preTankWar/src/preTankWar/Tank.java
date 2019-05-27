package preTankWar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Collection;
import java.util.List;
public class Tank {
	
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	private int x ,y;
	
	private int oldX, oldY;
	
	//用枚举定义各个方向	
	//tank方向
	private Direction dir = Direction.STOP;	//初始化方向为向下
	private boolean live = true;
	
	//炮筒方向
	private Direction ptDir = Direction.D;
	
	private boolean bL=false, bU=false, bR=false, bD = false;
	
	private boolean good;	//定义坦克的好坏
	
	private TankClient tc= null;
	
	Random r = new Random(); //定义随机数
	
	private int step = r.nextInt(12) + 3;	//记录坦克移动步数
	
	private List<Tank>tanks;
	
	public Tank(int x, int y, boolean good) {		
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}
	
	public Tank(int x, int y, boolean good,Direction dir,TankClient tc) {		
		this(x , y, good);
		this.dir = dir;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			if(!good) {
				tc.counterTanks.remove(this);
			}
			return;
		}
		Color c = g.getColor();
		if(good) g.setColor(Color.RED);
		else g.setColor(Color.BLUE);
		g.fillOval(x, y, WIDTH, HEIGHT);	//画出一个圆		
		g.setColor(Color.YELLOW);
		//根据炮筒的方向，画出炮筒，是一根线
		switch(ptDir) {
		case L:
			g.drawLine(x + Tank.WIDTH/2 , y + Tank.HEIGHT/2, x , y + Tank.HEIGHT/2);
			break;
		case LU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y);
			break;
		case U:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y);
			break;
		case RU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y);
			break;
		case R:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT/2);
			break;
		case RD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT);
			break;
		case D:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y + Tank.HEIGHT);
			break;
		case LD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT);
			break;
		}
		
		g.setColor(c);
		move();
	}
	
	//根据方向作出相应的移动
	private void move() {
		//记录上一步坦克的位置
		this.oldX = x;
		this.oldY = y;
		
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
		
		//若tank在动，炮筒指向动的方向
		if(Direction.STOP != this.dir) {
			this.ptDir = this.dir;
		}
		
		//解决坦克出界问题
		if(x < 0) x = 0;
		if(y < 30) y = 30;
		if(x + Tank.WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if(y + Tank.HEIGHT  > TankClient.GAME_HEIGHT)  y = TankClient.GAME_HEIGHT - Tank.HEIGHT;
		
		//如果是坏坦克，让它们随机动起来
		if(!good) {
			Direction[] dirs = Direction.values();	//把Direction里的内容转化为数组
			//每移动一定步数才改变坦克的方向
			if(step == 0) {
				step = r.nextInt(12) + 3;
				int rn  = r.nextInt(dirs.length);	//产生随机数
				dir = dirs[rn];
			}					
			step--;
			if(r.nextInt(40) > 36) fire();	//用随机数降低开火频率
		}
	}

	//确定哪个键被按下
	public void whichKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();	//获得所按键的虚拟键码
		switch(key) {
		//按下空格时候，新建子弹
		case KeyEvent.VK_SPACE:
			fire();
			break;
		//按下A键后，超级开火
		case KeyEvent.VK_A:
			superFire();
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
	
	//发射子弹,每按一下把一颗新建子弹填到线性表中
	private void fire() {
		if(!live) return;
		int x = this.x + WIDTH/2 - Bullet.WIDTH/2;
		int y = this.y + HEIGHT/2 - Bullet.HEIGHT/2;
		Bullet bullet = new Bullet(x , y, good, ptDir, tc);
		tc.bullets.add(bullet);
		
	}
	
	//根据指定方向建子弹，超级开火时候用
	private void fire(Direction dir) {
		if(!live) return;
		int x = this.x + WIDTH/2 - Bullet.WIDTH/2;
		int y = this.y + HEIGHT/2 - Bullet.HEIGHT/2;
		Bullet bullet = new Bullet(x , y, good, dir, tc);
		tc.bullets.add(bullet);		
	}
	
	//向八个方向开火
	public void superFire() {
		Direction[] dirs  = Direction.values();
		for(int i = 0; i < 8; i++) {
			fire(dirs[i]);
		}
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
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	//当坦克碰到墙的时候
	public boolean collidesWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}
	
	//敌方坦克之间碰到的时候
	public boolean collidesTanks(List<Tank>tanks) {
		for(int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if(this != t) {
				if(this.live && t.isLive() && this.getRect().intersects(t.getRect())) {
					this.stay();
					t.stay();
					return true;
				}
			}			
		}
		return false;
	}
	
	//回到上一步位置
	public void stay() {
		x = oldX;
		y = oldY;
	}
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	public boolean isGood() {
		return good;
	}
		
}
