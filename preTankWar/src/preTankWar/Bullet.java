package preTankWar;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	//把图片加入imgs数组中
		private static Toolkit tk = Toolkit.getDefaultToolkit();
		public static Image[] bulletImages = null;
		private static Map<String, Image> imgs = new HashMap<String, Image>();
		static {
			bulletImages = new Image[] {
				tk.getImage(Bullet.class.getClassLoader().getResource("images/bulletL.gif")),
				tk.getImage(Bullet.class.getClassLoader().getResource("images/missileLU.gif")),
				tk.getImage(Bullet.class.getClassLoader().getResource("images/bulletU.gif")),
				tk.getImage(Bullet.class.getClassLoader().getResource("images/missileRU.gif")),
				tk.getImage(Bullet.class.getClassLoader().getResource("images/bulletR.gif")),
				tk.getImage(Bullet.class.getClassLoader().getResource("images/missileRD.gif")),
				tk.getImage(Bullet.class.getClassLoader().getResource("images/bulletD.gif")),
				tk.getImage(Bullet.class.getClassLoader().getResource("images/missileLD.gif"))
			};
			
			imgs.put("L", bulletImages[0]);
			imgs.put("LU", bulletImages[1]);
			imgs.put("U", bulletImages[2]);
			imgs.put("RU", bulletImages[3]);
			imgs.put("R", bulletImages[4]);
			imgs.put("RD", bulletImages[5]);
			imgs.put("D", bulletImages[6]);
			imgs.put("LD", bulletImages[7]);
		}
				
		
		
	
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
		
		switch(dir) {
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		}
		
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
			//如果是好的每次打中掉20滴血，如果是坏蛋直接死
			if(t.isGood()) {
				t.setBlood(t.getBlood() - 20);
				if(t.getBlood() <= 0) {
					t.setLive(false);
				}
			}else {
				t.setLive(false);
			}
			
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
	
	//当子弹碰到墙的时候
	public boolean hitWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}
	
	//得到一个以x,y为横纵坐标,WIDTH, HEIGHT为宽高大小的矩形
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

}
