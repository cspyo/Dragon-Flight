package 드래곤길들이기;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

// ImageIcon에 좌표의 위치를 부여하고자 ImageIcon 클래스를 상속함
public class PosImageIcon extends ImageIcon {
	String img;
	int x;				// ImageIcon의 X좌표
	int y;				// ImageIcon의 y좌표
	double xx;
	double yy;
	int width;			// ImageIcon의 넓이
	int height;			// ImageIcon의 높이
	
	
	public PosImageIcon(String img,int x,int y, int width, int height) {
		super(img);
		this.img = img;
		this.x=x;
		this.xx = (double)x;
		this.y=y;
		this.yy=(double)y;
		this.width=width;
		this.height=height;
	
	}
	//충돌
	public boolean collide(Rectangle rc) {
		return rc.intersects((double)x,(double)y,(double)width,(double)height);
	}
	//가운데
	public Point center() {
		return new Point(x+width/2,y+height/2);

	}
	
	public void draw(Graphics g) {
		g.drawImage(this.getImage(), x, y, width, height, null);
	}
}
