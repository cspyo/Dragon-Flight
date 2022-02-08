package 드래곤길들이기;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Enemy extends PosImageIcon{
	int moveX=0;
	int moveY=0;
	int hp;
	int dmg;
	int isHit=0;
	String name;
	ImageIcon hitImage;
	int deadTime=0;
	
	
	Enemy(String name, int x, int y, int width, int height, int hp){
		super("src/res/"+name+"용.gif",x,y,width,height);
		this.x = x;
		this.y = y;
		this.xx = (double)x;
		this.yy = (double)y;
		this.name = name;
		this.hp = hp;
		
		hitImage = new ImageIcon("src/res/"+name+"용 피격.png");
	}
	//적 이동
	public void move() {
		x += moveX;
		y += moveY;
		
	}
	
	public void draw(Graphics g) {
		
		if(isHit>0 && isHit <=100)
			g.drawImage(hitImage.getImage(), x, y, 110, 110, null);
		else
			g.drawImage(this.getImage(),x-2,y,width,height,null);
		
		
	}
	
}
