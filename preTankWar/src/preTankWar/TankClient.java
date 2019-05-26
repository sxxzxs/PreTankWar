package preTankWar;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame{
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	Tank myTank = new Tank(30,50,true,Direction.STOP,this) ;
	Image offScreenImage = null;
	List <Bullet> bullets  = new ArrayList<Bullet>();	//建立顺序表装子弹	
	List<Explode> explode  = new ArrayList<Explode>(); //建立顺序表装爆炸
	List<Tank>counterTanks = new ArrayList<Tank>();
		
	public void launch() {
		for(int i = 0; i < 10; i++) {
			counterTanks.add(new Tank(50 + (i + 1) * 40, 50, false, Direction.D,this));
		}
				
		setLocation(300, 100);	//窗口位置
		setSize(GAME_WIDTH, GAME_HEIGHT);	 //窗口大小		
		setResizable(false); 	//窗口大小可变否
		setTitle("TankWar");	//窗口标题栏
		setBackground(Color.BLACK);
		
		//添加事件监听
		addKeyListener(new KeyMonitor());
		
		setVisible(true);	//设置可见
		
		//设置窗口监听，点拔插可关闭窗口
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}			
		});
		
		//启动线程
		new Thread(new PaintThread()).start();
			
	}
	
	//paint方法，窗口重画时候自动调用
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.yellow);
		
		g.drawString("current bullets: " + bullets.size(), 10, 50);
		g.drawString("current explodes: " + explode.size(), 10, 70);
		g.drawString("current counterTanks: " + counterTanks.size(), 10, 90);
		
		g.setColor(c);
		myTank.draw(g);				
		
		//画出子弹
		for(int i = 0; i < bullets.size(); i++) {
			Bullet m = bullets.get(i);
			m.hitTanks(counterTanks);
			m.draw(g);
		}
		
		//画出爆炸
		for(int i = 0; i < explode.size(); i++) {
			Explode e = explode.get(i);
			e.draw(g);
		}
		
		for(int i = 0;i < counterTanks.size(); i++) {
			Tank t = counterTanks.get(i);
			t.draw(g);
		}
	}
	
	/*解决双缓冲,没必要深究，截获update,首先把画出来的东西（先画在内存的图片中，
	图片大小和游戏画面一致，然后把内存中图片一次性画到屏幕上（把内存的内容复制到显存）*/
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();	//拿到图片的画笔
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);	
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	public static void main(String[] args) {
		
		TankClient tc = new TankClient();
		tc.launch();
	}
	
	//定义线程继承Runnable接口
	private class PaintThread implements Runnable {
		public void run() {
			while(true) {
				//调用repaint方法后先调用update在调用paint
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {				
					e.printStackTrace();
				}
			}
		}
	}
	
	//定义监听器类实现键盘控制
	private class KeyMonitor extends KeyAdapter{	

		//键按下去的时候
		@Override
		public void keyPressed(KeyEvent e) {
			myTank.whichKeyPressed(e);
		}
		
		//键抬起来的时候
		@Override
		public void keyReleased(KeyEvent e) {
			myTank.whichKeyReleased(e);
		}
		
	}

}
