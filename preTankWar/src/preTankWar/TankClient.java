package preTankWar;

import java.awt.*;
import java.awt.event.*;


public class TankClient extends Frame{
		
	public void launch() {
		setLocation(300, 100);	//窗口位置
		setSize(800, 600);	 //窗口大小
		setVisible(true);	//设置可见
		setResizable(false); 	//窗口大小可变否
		setTitle("TankWar");	//窗口标题栏
		//设置窗口监听，点拔插可关闭窗口
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}			
		});
		
	}
	
	//paint方法，窗口重画时候自动调用
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(30, 50, 50, 50);	//画出一个圆
		g.setColor(c);
	}
	
	
	public static void main(String[] args) {
		
		TankClient tc = new TankClient();
		tc.launch();
	}

}
