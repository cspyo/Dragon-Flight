package 드래곤길들이기;

import java.awt.Graphics;

public class Bullet extends PosImageIcon{
	double moveX,moveY;
	
	
	Bullet(String img, int x, int y,int width, int height, double moveX, double moveY){
		super(img,x,y,width,height);
		this.moveX = moveX;
		this.moveY = moveY;
	}
	
	public void move() {
		xx+= moveX;
		yy+= moveY;
		x = (int)xx;
		y = (int)yy;
	}
	public void draw(Graphics g) {
		g.drawImage(this.getImage(), (int)xx, (int)yy, width, height, null);
	}
}
