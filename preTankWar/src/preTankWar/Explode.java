package preTankWar;

import java.awt.*;

public class Explode {
	private int x , y;
	private boolean live = true;			
	int step = 0;	//爆炸到第几步了
	private TankClient tc;
	//把图片加入imgs数组中
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	public static Image[] imgs = {
			tk.getImage(Explode.class.getClassLoader().getResource("images/e1.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/e2.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/e3.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/e4.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/e5.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/e6.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/e7.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/e8.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/e9.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/e10.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/e11.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/e12.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/e13.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/e14.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/e15.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/e16.gif"))
	};
	
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
		
		if(step == imgs.length) {
			live = false;
			step = 0;
			return;
		}	
		//画出爆炸图片
		g.drawImage(imgs[step], x, y, null);
		step++;
				
	}

}
