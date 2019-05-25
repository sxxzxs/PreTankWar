package preTankWar;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {
	int x ,y;
	
	public Tank(int x, int y) {		
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 50, 50);	//画出一个圆
		g.setColor(c);
	}
	
	public void KeyPressed(KeyEvent e) {
		int key = e.getKeyCode();	//获得所按键的虚拟键码
		switch(key) {
		case KeyEvent.VK_LEFT:
			x -= 10;
			break;
		case KeyEvent.VK_UP:
			y -= 10;
			break;
		case KeyEvent.VK_RIGHT:
			x += 10;
			break;
		case KeyEvent.VK_DOWN:
			y += 10;
			break;
		}
	}
	
}
